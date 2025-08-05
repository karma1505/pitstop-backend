package com.garage.backend.maintenance.repository;

import com.garage.backend.maintenance.entity.MaintenanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, UUID> {

    // Find by vehicle ID
    List<MaintenanceSchedule> findByVehicleId(UUID vehicleId);

    // Find by customer ID
    List<MaintenanceSchedule> findByCustomerId(UUID customerId);

    // Find by service type
    List<MaintenanceSchedule> findByServiceType(String serviceType);

    // Find by status
    List<MaintenanceSchedule> findByStatus(String status);

    // Find by scheduled date range
    List<MaintenanceSchedule> findByScheduledDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by due date range
    List<MaintenanceSchedule> findByDueDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by next service date range
    List<MaintenanceSchedule> findByNextServiceDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by mileage range
    List<MaintenanceSchedule> findByMileageBetween(Integer minMileage, Integer maxMileage);

    // Find by vehicle ID and status
    List<MaintenanceSchedule> findByVehicleIdAndStatus(UUID vehicleId, String status);

    // Find by customer ID and status
    List<MaintenanceSchedule> findByCustomerIdAndStatus(UUID customerId, String status);

    // Find by vehicle ID and service type
    List<MaintenanceSchedule> findByVehicleIdAndServiceType(UUID vehicleId, String serviceType);

    // Find by customer ID and service type
    List<MaintenanceSchedule> findByCustomerIdAndServiceType(UUID customerId, String serviceType);

    // Find overdue maintenance schedules
    List<MaintenanceSchedule> findByDueDateBeforeAndStatus(LocalDate date, String status);

    // Find upcoming maintenance schedules
    List<MaintenanceSchedule> findByScheduledDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, String status);

    // Custom query to find maintenance schedules by multiple criteria
    @Query("SELECT ms FROM MaintenanceSchedule ms WHERE " +
           "(:vehicleId IS NULL OR ms.vehicleId = :vehicleId) AND " +
           "(:customerId IS NULL OR ms.customerId = :customerId) AND " +
           "(:serviceType IS NULL OR ms.serviceType = :serviceType) AND " +
           "(:status IS NULL OR ms.status = :status) AND " +
           "(:startDate IS NULL OR ms.scheduledDate >= :startDate) AND " +
           "(:endDate IS NULL OR ms.scheduledDate <= :endDate)")
    List<MaintenanceSchedule> findSchedulesByCriteria(@Param("vehicleId") UUID vehicleId,
                                                   @Param("customerId") UUID customerId,
                                                   @Param("serviceType") String serviceType,
                                                   @Param("status") String status,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);

    // Find recent maintenance schedules
    List<MaintenanceSchedule> findTop10ByOrderByScheduledDateDesc();

    // Find maintenance schedules by mileage
    List<MaintenanceSchedule> findByMileage(Integer mileage);

    // Find maintenance schedules by next service mileage
    List<MaintenanceSchedule> findByNextServiceMileage(Integer nextServiceMileage);

    // Find maintenance schedules with mileage greater than specified
    List<MaintenanceSchedule> findByMileageGreaterThan(Integer mileage);

    // Find maintenance schedules with mileage less than specified
    List<MaintenanceSchedule> findByMileageLessThan(Integer mileage);
}
