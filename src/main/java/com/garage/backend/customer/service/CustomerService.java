package com.garage.backend.customer.service;

import com.garage.backend.customer.dto.CreateCustomerRequest;
import com.garage.backend.customer.dto.UpdateCustomerRequest;
import com.garage.backend.customer.dto.CustomerResponse;
import com.garage.backend.customer.dto.VehicleSummaryResponse;
import com.garage.backend.customer.entity.Customers;
import com.garage.backend.customer.repository.CustomersRepository;
import com.garage.backend.vehicle.entity.Vehicles;
import com.garage.backend.vehicle.repository.VehiclesRepository;
import com.garage.backend.settings.entity.Garage;
import com.garage.backend.settings.repository.GarageRepository;
import com.garage.backend.authentication.entity.User;
import com.garage.backend.authentication.repository.UserRepository;
import com.garage.backend.address.entity.Addresses;
import com.garage.backend.address.repository.AddressesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for customer management operations
 * 
 * Handles all business logic related to customer operations including:
 * - Creating new customers
 * - Updating existing customers
 * - Retrieving customer information
 * - Searching customers
 * - Managing customer lifecycle
 */
@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressesRepository addressesRepository;

    /**
     * Create a new customer with optional vehicle information
     * 
     * Scenario: Create New Customer with Vehicle
     *   Given a garage owner wants to add a new customer
     *   When they provide valid customer details and optional vehicle info
     *   Then the system should create a customer record
     *   And create vehicle record if provided
     *   And set default city/state from garage if not provided
     *   And return the customer information with vehicles
     *   But if phone number already exists
     *   Then the system should throw an exception
     */
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        // Check if customer with phone number already exists
        if (customersRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Customer with phone number " + request.getPhone() + " already exists");
        }

        // Get current user and garage information for defaults
        String currentUserEmail = getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        
        // Get garage information for default city/state
        Optional<Garage> garageOpt = garageRepository.findByCreatedBy(currentUser.getId()).stream().findFirst();
        
        // Create new customer entity
        Customers customer = new Customers();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        
        // Set city and state with defaults from garage address if not provided
        if (request.getCity() != null && !request.getCity().trim().isEmpty()) {
            customer.setCity(request.getCity());
        } else if (garageOpt.isPresent()) {
            // Get garage address to fetch city
            List<Addresses> garageAddresses = addressesRepository.findByGarageId(garageOpt.get().getId());
            if (!garageAddresses.isEmpty()) {
                customer.setCity(garageAddresses.get(0).getCity());
            } else {
                customer.setCity(null); // No default city available
            }
        } else {
            customer.setCity(null); // No garage found
        }
        
        if (request.getState() != null && !request.getState().trim().isEmpty()) {
            customer.setState(request.getState());
        } else if (garageOpt.isPresent()) {
            // Get garage address to fetch state
            List<Addresses> garageAddresses = addressesRepository.findByGarageId(garageOpt.get().getId());
            if (!garageAddresses.isEmpty()) {
                customer.setState(garageAddresses.get(0).getState());
            } else {
                customer.setState(null); // No default state available
            }
        } else {
            customer.setState(null); // No garage found
        }
        
        customer.setIsRegularCustomer(false); // Default value

        // Save customer to database
        Customers savedCustomer = customersRepository.save(customer);

        // Create vehicle if vehicle info is provided
        if (request.getVehicleInfo() != null) {
            createVehicleForCustomer(savedCustomer, request.getVehicleInfo());
        }

        // Convert to response DTO and return
        return convertToResponse(savedCustomer);
    }

    /**
     * Update an existing customer with optional vehicle information
     * 
     * Scenario: Update Customer Information with Vehicle
     *   Given a garage owner wants to update customer details
     *   When they provide valid updated information and optional vehicle info
     *   Then the system should update the customer record
     *   And update or create vehicle record if provided
     *   And return the updated customer information with vehicles
     *   But if customer does not exist
     *   Then the system should throw an exception
     *   And if phone number conflicts with another customer
     *   Then the system should throw an exception
     */
    public CustomerResponse updateCustomer(UUID customerId, UpdateCustomerRequest request) {
        // Find existing customer
        Customers existingCustomer = customersRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));

        // Check if phone number is being changed and if it conflicts with another customer
        if (!existingCustomer.getPhone().equals(request.getPhone())) {
            Optional<Customers> customerWithPhone = customersRepository.findByPhone(request.getPhone());
            if (customerWithPhone.isPresent() && !customerWithPhone.get().getId().equals(customerId)) {
                throw new RuntimeException("Another customer with phone number " + request.getPhone() + " already exists");
            }
        }

        // Get current user and garage information for defaults (if needed)
        String currentUserEmail = getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        Optional<Garage> garageOpt = garageRepository.findByCreatedBy(currentUser.getId()).stream().findFirst();

        // Update customer fields
        existingCustomer.setName(request.getName());
        existingCustomer.setPhone(request.getPhone());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setAddressLine1(request.getAddressLine1());
        existingCustomer.setAddressLine2(request.getAddressLine2());
        
        // Set city with default from garage address if not provided
        if (request.getCity() != null && !request.getCity().trim().isEmpty()) {
            existingCustomer.setCity(request.getCity());
        } else if (garageOpt.isPresent()) {
            List<Addresses> garageAddresses = addressesRepository.findByGarageId(garageOpt.get().getId());
            if (!garageAddresses.isEmpty()) {
                existingCustomer.setCity(garageAddresses.get(0).getCity());
            }
            // If no garage address found, keep existing city
        }
        
        // Set state with default from garage address if not provided
        if (request.getState() != null && !request.getState().trim().isEmpty()) {
            existingCustomer.setState(request.getState());
        } else if (garageOpt.isPresent()) {
            List<Addresses> garageAddresses = addressesRepository.findByGarageId(garageOpt.get().getId());
            if (!garageAddresses.isEmpty()) {
                existingCustomer.setState(garageAddresses.get(0).getState());
            }
            // If no garage address found, keep existing state
        }

        // Save updated customer
        Customers updatedCustomer = customersRepository.save(existingCustomer);

        // Handle vehicle information if provided
        if (request.getVehicleInfo() != null) {
            // Check if customer already has a vehicle, update it or create new one
            List<Vehicles> existingVehicles = vehiclesRepository.findByCustomerId(customerId);
            if (!existingVehicles.isEmpty()) {
                // Update the first vehicle (assuming one vehicle per customer for now)
                updateVehicleForCustomer(existingVehicles.get(0), request.getVehicleInfo());
            } else {
                // Create new vehicle
                createVehicleForCustomer(updatedCustomer, request.getVehicleInfo());
            }
        }

        // Convert to response DTO and return
        return convertToResponse(updatedCustomer);
    }

    /**
     * Get customer by ID
     * 
     * Scenario: Retrieve Customer by ID
     *   Given a garage owner wants to view customer details
     *   When they provide a valid customer ID
     *   Then the system should return the customer information
     *   But if customer does not exist
     *   Then the system should throw an exception
     */
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(UUID customerId) {
        Customers customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId));
        
        return convertToResponse(customer);
    }

    /**
     * Get all customers with pagination and optional filtering
     * 
     * Scenario: List All Customers with Filters
     *   Given a garage owner wants to view customers with filters
     *   When they request customer list with optional filters and pagination
     *   Then the system should return filtered and paginated customer list
     *   And include total count and page information
     */
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAllCustomers(Pageable pageable, String customerName, 
                                                 String vehicleRegistrationNumber, String vehicleBrand, 
                                                 LocalDate date, LocalDate fromDate, LocalDate toDate) {
        
        try {
            // Apply filters if any are provided
            if (hasAnyFilter(customerName, vehicleRegistrationNumber, vehicleBrand, date, fromDate, toDate)) {
                // For filtered results, get all matching customers and handle pagination manually
                List<Customers> allFilteredCustomers = getFilteredCustomers(customerName, vehicleRegistrationNumber, vehicleBrand, date, fromDate, toDate);
                
                System.out.println("Filtered customers count: " + allFilteredCustomers.size());
                
                List<CustomerResponse> allCustomerResponses = allFilteredCustomers.stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());
                
                System.out.println("Customer responses count: " + allCustomerResponses.size());
                
                // Apply manual pagination for filtered results
                int start = (int) pageable.getOffset();
                int end = Math.min((start + pageable.getPageSize()), allCustomerResponses.size());
                List<CustomerResponse> pageContent = allCustomerResponses.subList(start, end);
                
                System.out.println("Page content count: " + pageContent.size());
                
                return new PageImpl<>(pageContent, pageable, allCustomerResponses.size());
            } else {
                // No filters, use repository pagination directly (preserves total count)
                Page<Customers> customersPage = customersRepository.findAll(pageable);
                
                List<CustomerResponse> customerResponses = customersPage.getContent().stream()
                        .map(this::convertToResponse)
                        .collect(Collectors.toList());
                
                return new PageImpl<>(customerResponses, pageable, customersPage.getTotalElements());
            }
        } catch (Exception e) {
            System.err.println("Error in getAllCustomers: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Get filtered customers based on provided criteria
     * 
     * Scenario: Filter Customers by Multiple Criteria
     *   Given a garage owner wants to filter customers
     *   When they provide filter criteria
     *   Then the system should return matching customers
     *   And combine filters with AND logic
     */
    @Transactional(readOnly = true)
    private List<Customers> getFilteredCustomers(String customerName, String vehicleRegistrationNumber, 
                                                String vehicleBrand, LocalDate date, LocalDate fromDate, LocalDate toDate) {
        // Handle single date vs date range
        LocalDate actualFromDate = date != null ? date : fromDate;
        LocalDate actualToDate = date != null ? date : toDate;
        
        // Start with all customers
        List<Customers> result = customersRepository.findAll();
        
        // Apply name filter
        if (customerName != null && !customerName.trim().isEmpty()) {
            List<Customers> nameFiltered = customersRepository.findByNameFilter(customerName);
            result = result.stream()
                    .filter(nameFiltered::contains)
                    .collect(Collectors.toList());
        }
        
        // Apply vehicle registration filter
        if (vehicleRegistrationNumber != null && !vehicleRegistrationNumber.trim().isEmpty()) {
            List<Customers> regFiltered = customersRepository.findByVehicleRegistrationFilter(vehicleRegistrationNumber);
            result = result.stream()
                    .filter(regFiltered::contains)
                    .collect(Collectors.toList());
        }
        
        // Apply vehicle make filter
        if (vehicleBrand != null && !vehicleBrand.trim().isEmpty()) {
            List<Customers> makeFiltered = customersRepository.findByVehicleMakeFilter(vehicleBrand);
            result = result.stream()
                    .filter(makeFiltered::contains)
                    .collect(Collectors.toList());
        }
        
        // Apply date range filter - this is the key fix
        if (actualFromDate != null || actualToDate != null) {
            System.out.println("Applying date filter. Date: " + date + ", FromDate: " + fromDate + ", ToDate: " + toDate);
            System.out.println("Actual from date: " + actualFromDate + ", Actual to date: " + actualToDate);
            System.out.println("Customers before date filter: " + result.size());
            
            // For a single date filter, we need to check if the customer was created on that specific date
            if (date != null) {
                // Single date filter - check if customer was created on this exact date
                result = result.stream()
                        .filter(customer -> {
                            LocalDateTime createdAt = customer.getCreatedAt();
                            boolean matches = createdAt != null && createdAt.toLocalDate().equals(date);
                            if (matches) {
                                System.out.println("Customer " + customer.getName() + " matches date " + date + " (created: " + createdAt.toLocalDate() + ")");
                            }
                            return matches;
                        })
                        .collect(Collectors.toList());
            } else {
                // Date range filter
                LocalDate filterFromDate = actualFromDate != null ? actualFromDate : LocalDate.of(1900, 1, 1);
                LocalDate filterToDate = actualToDate != null ? actualToDate : LocalDate.of(2100, 12, 31);
                
                result = result.stream()
                        .filter(customer -> {
                            LocalDateTime createdAt = customer.getCreatedAt();
                            if (createdAt == null) return false;
                            LocalDate customerDate = createdAt.toLocalDate();
                            return !customerDate.isBefore(filterFromDate) && !customerDate.isAfter(filterToDate);
                        })
                        .collect(Collectors.toList());
            }
            
            System.out.println("Customers after date filter: " + result.size());
        }
        
        return result;
    }

    /**
     * Check if any filter parameters are provided
     */
    private boolean hasAnyFilter(String customerName, String vehicleRegistrationNumber, String vehicleBrand, 
                                LocalDate date, LocalDate fromDate, LocalDate toDate) {
        return (customerName != null && !customerName.trim().isEmpty()) ||
               (vehicleRegistrationNumber != null && !vehicleRegistrationNumber.trim().isEmpty()) ||
               (vehicleBrand != null && !vehicleBrand.trim().isEmpty()) ||
               date != null || fromDate != null || toDate != null;
    }

    /**
     * Create vehicle for customer
     * 
     * Scenario: Create Vehicle for Customer
     *   Given a customer needs a vehicle record
     *   When vehicle information is provided
     *   Then the system should create and link vehicle to customer
     *   And validate vehicle data
     */
    private void createVehicleForCustomer(Customers customer, com.garage.backend.customer.dto.VehicleInfoRequest vehicleInfo) {
        // Check if registration number already exists
        if (vehiclesRepository.existsByRegistrationNumber(vehicleInfo.getRegistrationNumber())) {
            throw new RuntimeException("Vehicle with registration number " + vehicleInfo.getRegistrationNumber() + " already exists");
        }

        // Validate year is 4 digits
        if (vehicleInfo.getYear().toString().length() != 4) {
            throw new RuntimeException("Vehicle year must be exactly 4 digits");
        }

        Vehicles vehicle = new Vehicles();
        vehicle.setCustomer(customer);
        vehicle.setRegistrationNumber(vehicleInfo.getRegistrationNumber());
        vehicle.setMake(vehicleInfo.getMake());
        vehicle.setModel(vehicleInfo.getModel());
        vehicle.setYear(vehicleInfo.getYear());

        vehiclesRepository.save(vehicle);
    }

    /**
     * Update vehicle for customer
     * 
     * Scenario: Update Vehicle for Customer
     *   Given a customer's vehicle needs updating
     *   When updated vehicle information is provided
     *   Then the system should update the vehicle record
     *   And validate vehicle data
     */
    private void updateVehicleForCustomer(Vehicles existingVehicle, com.garage.backend.customer.dto.VehicleInfoRequest vehicleInfo) {
        // Check if registration number is being changed and if it conflicts
        if (!existingVehicle.getRegistrationNumber().equals(vehicleInfo.getRegistrationNumber())) {
            if (vehiclesRepository.existsByRegistrationNumber(vehicleInfo.getRegistrationNumber())) {
                throw new RuntimeException("Vehicle with registration number " + vehicleInfo.getRegistrationNumber() + " already exists");
            }
        }

        // Validate year is 4 digits
        if (vehicleInfo.getYear().toString().length() != 4) {
            throw new RuntimeException("Vehicle year must be exactly 4 digits");
        }

        existingVehicle.setRegistrationNumber(vehicleInfo.getRegistrationNumber());
        existingVehicle.setMake(vehicleInfo.getMake());
        existingVehicle.setModel(vehicleInfo.getModel());
        existingVehicle.setYear(vehicleInfo.getYear());

        vehiclesRepository.save(existingVehicle);
    }

    /**
     * Get current user email from security context
     */
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName();
        }
        throw new RuntimeException("No authenticated user found");
    }

    /**
     * Convert Customer entity to CustomerResponse DTO with vehicles
     * 
     * @param customer Customer entity
     * @return CustomerResponse DTO
     */
    private CustomerResponse convertToResponse(Customers customer) {
        // Get vehicles for this customer
        List<Vehicles> vehicles = vehiclesRepository.findByCustomerId(customer.getId());
        List<VehicleSummaryResponse> vehicleSummaries = vehicles.stream()
                .map(this::convertToVehicleSummary)
                .collect(Collectors.toList());

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getAddressLine1(),
                customer.getAddressLine2(),
                customer.getCity(),
                customer.getState(),
                customer.getIsRegularCustomer(),
                vehicleSummaries,
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    /**
     * Convert Vehicle entity to VehicleSummaryResponse DTO
     * 
     * @param vehicle Vehicle entity
     * @return VehicleSummaryResponse DTO
     */
    private VehicleSummaryResponse convertToVehicleSummary(Vehicles vehicle) {
        return new VehicleSummaryResponse(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getYear()
        );
    }
}