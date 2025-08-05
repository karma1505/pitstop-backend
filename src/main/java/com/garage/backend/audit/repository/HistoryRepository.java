package com.garage.backend.audit.repository;

import com.garage.backend.audit.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {

    // Find by table name
    List<History> findByTableName(String tableName);

    // Find by record ID
    List<History> findByRecordId(UUID recordId);

    // Find by action
    List<History> findByAction(String action);

    // Find by user ID
    List<History> findByUserId(UUID userId);

    // Find by created date range
    List<History> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by table name and record ID
    List<History> findByTableNameAndRecordId(String tableName, UUID recordId);

    // Find by table name and action
    List<History> findByTableNameAndAction(String tableName, String action);

    // Find by user ID and action
    List<History> findByUserIdAndAction(UUID userId, String action);

    // Find by user ID and date range
    List<History> findByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to find history by multiple criteria
    @Query("SELECT h FROM History h WHERE " +
           "(:tableName IS NULL OR h.tableName = :tableName) AND " +
           "(:recordId IS NULL OR h.recordId = :recordId) AND " +
           "(:action IS NULL OR h.action = :action) AND " +
           "(:userId IS NULL OR h.userId = :userId) AND " +
           "(:startDate IS NULL OR h.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR h.createdAt <= :endDate)")
    List<History> findHistoryByCriteria(@Param("tableName") String tableName,
                                     @Param("recordId") UUID recordId,
                                     @Param("action") String action,
                                     @Param("userId") UUID userId,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    // Find recent history
    List<History> findTop10ByOrderByCreatedAtDesc();

    // Find history by table name and date range
    List<History> findByTableNameAndCreatedAtBetween(String tableName, LocalDateTime startDate, LocalDateTime endDate);

    // Find history by action and date range
    List<History> findByActionAndCreatedAtBetween(String action, LocalDateTime startDate, LocalDateTime endDate);
}
