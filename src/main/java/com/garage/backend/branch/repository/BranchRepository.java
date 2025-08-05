package com.garage.backend.branch.repository;

import com.garage.backend.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    // Find by branch code
    Optional<Branch> findByBranchCode(String branchCode);

    // Find by email
    Optional<Branch> findByEmail(String email);

    // Find active branches
    List<Branch> findByIsActiveTrue();

    // Find by manager ID
    List<Branch> findByManagerId(UUID managerId);

    // Find by branch name containing text
    List<Branch> findByBranchNameContainingIgnoreCase(String branchName);

    // Find by phone
    Optional<Branch> findByPhone(String phone);

    // Check if branch code exists
    boolean existsByBranchCode(String branchCode);

    // Check if email exists
    boolean existsByEmail(String email);

    // Custom query to find branches by multiple criteria
    @Query("SELECT b FROM Branch b WHERE " +
           "(:branchName IS NULL OR b.branchName LIKE %:branchName%) AND " +
           "(:isActive IS NULL OR b.isActive = :isActive) AND " +
           "(:managerId IS NULL OR b.managerId = :managerId)")
    List<Branch> findBranchesByCriteria(@Param("branchName") String branchName,
                                       @Param("isActive") Boolean isActive,
                                       @Param("managerId") UUID managerId);
}
