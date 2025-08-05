package com.garage.backend.authentication.repository;

import com.garage.backend.authentication.entity.OTPCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPCode, Long> {

    // ========== Email OTP Queries ==========
    @Query("SELECT o FROM OTPCode o WHERE o.emailId = :emailId AND o.type = :type AND o.isUsed = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    Optional<OTPCode> findValidOTPByEmailAndType(@Param("emailId") String emailId,
                                                 @Param("type") String type,
                                                 @Param("now") LocalDateTime now);

    @Query("SELECT o FROM OTPCode o WHERE o.emailId = :emailId AND o.type = :type ORDER BY o.createdAt DESC")
    List<OTPCode> findAllByEmailAndType(@Param("emailId") String emailId,
                                        @Param("type") String type);

    @Query("SELECT COUNT(o) FROM OTPCode o WHERE o.emailId = :emailId AND o.type = :type AND o.createdAt >= :since")
    long countRecentEmailOTPRequests(@Param("emailId") String emailId,
                                     @Param("type") String type,
                                     @Param("since") LocalDateTime since);

    // ========== Common Operations ==========
    @Query("SELECT o FROM OTPCode o WHERE o.expiresAt < :now")
    List<OTPCode> findExpiredOTPs(@Param("now") LocalDateTime now);

    @Modifying
    @Query("DELETE FROM OTPCode o WHERE o.expiresAt < :now")
    void deleteExpiredOTPs(@Param("now") LocalDateTime now);
}