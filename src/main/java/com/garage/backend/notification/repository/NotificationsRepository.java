package com.garage.backend.notification.repository;

import com.garage.backend.notification.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, UUID> {

    // Find by user ID
    List<Notifications> findByUserId(UUID userId);

    // Find by type
    List<Notifications> findByType(String type);

    // Find by status
    List<Notifications> findByStatus(String status);

    // Find by created date range
    List<Notifications> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by sent date range
    List<Notifications> findBySentAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by read date range
    List<Notifications> findByReadAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by user ID and status
    List<Notifications> findByUserIdAndStatus(UUID userId, String status);

    // Find by user ID and type
    List<Notifications> findByUserIdAndType(UUID userId, String type);

    // Find by type and status
    List<Notifications> findByTypeAndStatus(String type, String status);

    // Find by user ID and date range
    List<Notifications> findByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime startDate, LocalDateTime endDate);

    // Find unread notifications for a user
    List<Notifications> findByUserIdAndStatusOrderByCreatedAtDesc(UUID userId, String status);

    // Find pending notifications
    List<Notifications> findByStatusOrderByCreatedAtAsc(String status);

    // Find failed notifications
    List<Notifications> findByStatusAndRetryCountLessThan(String status, Integer maxRetries);

    // Custom query to find notifications by multiple criteria
    @Query("SELECT n FROM Notifications n WHERE " +
           "(:userId IS NULL OR n.userId = :userId) AND " +
           "(:type IS NULL OR n.type = :type) AND " +
           "(:status IS NULL OR n.status = :status) AND " +
           "(:startDate IS NULL OR n.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR n.createdAt <= :endDate)")
    List<Notifications> findNotificationsByCriteria(@Param("userId") UUID userId,
                                                 @Param("type") String type,
                                                 @Param("status") String status,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);

    // Find recent notifications
    List<Notifications> findTop10ByOrderByCreatedAtDesc();

    // Find notifications by title containing text
    List<Notifications> findByTitleContainingIgnoreCase(String title);

    // Find notifications by message containing text
    List<Notifications> findByMessageContainingIgnoreCase(String message);

    // Count unread notifications for a user
    @Query("SELECT COUNT(n) FROM Notifications n WHERE n.userId = :userId AND n.status = 'PENDING'")
    Long countUnreadNotifications(@Param("userId") UUID userId);

    // Count notifications by type and status
    @Query("SELECT COUNT(n) FROM Notifications n WHERE n.type = :type AND n.status = :status")
    Long countNotificationsByTypeAndStatus(@Param("type") String type, @Param("status") String status);
}
