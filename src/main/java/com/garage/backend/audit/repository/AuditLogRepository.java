package com.garage.backend.audit.repository;

import com.garage.backend.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    // Find by user ID
    List<AuditLog> findByUserId(UUID userId);

    // Find by action
    List<AuditLog> findByAction(String action);

    // Find by resource
    List<AuditLog> findByResource(String resource);

    // Find by resource ID
    List<AuditLog> findByResourceId(UUID resourceId);

    // Find by IP address
    List<AuditLog> findByIpAddress(String ipAddress);

    // Find by created date range
    List<AuditLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by user ID and action
    List<AuditLog> findByUserIdAndAction(UUID userId, String action);

    // Find by user ID and resource
    List<AuditLog> findByUserIdAndResource(UUID userId, String resource);

    // Find by resource and resource ID
    List<AuditLog> findByResourceAndResourceId(String resource, UUID resourceId);

    // Find by user ID and date range
    List<AuditLog> findByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find audit logs by multiple criteria
    @Query("SELECT al FROM AuditLog al WHERE " +
           "(:userId IS NULL OR al.userId = :userId) AND " +
           "(:action IS NULL OR al.action = :action) AND " +
           "(:resource IS NULL OR al.resource = :resource) AND " +
           "(:resourceId IS NULL OR al.resourceId = :resourceId) AND " +
           "(:ipAddress IS NULL OR al.ipAddress = :ipAddress) AND " +
           "(:startDate IS NULL OR al.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR al.createdAt <= :endDate)")
    List<AuditLog> findAuditLogsByCriteria(@Param("userId") UUID userId,
                                        @Param("action") String action,
                                        @Param("resource") String resource,
                                        @Param("resourceId") UUID resourceId,
                                        @Param("ipAddress") String ipAddress,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    // Find recent audit logs
    List<AuditLog> findTop10ByOrderByCreatedAtDesc();

    // Find audit logs by action and date range
    List<AuditLog> findByActionAndCreatedAtBetween(String action, LocalDateTime startDate, LocalDateTime endDate);

    // Find audit logs by resource and date range
    List<AuditLog> findByResourceAndCreatedAtBetween(String resource, LocalDateTime startDate, LocalDateTime endDate);

    // Find audit logs by IP address and date range
    List<AuditLog> findByIpAddressAndCreatedAtBetween(String ipAddress, LocalDateTime startDate, LocalDateTime endDate);
}
