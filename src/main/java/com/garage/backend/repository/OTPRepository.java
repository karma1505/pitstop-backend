package com.garage.backend.repository;

import com.garage.backend.entity.OTPCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTPCode, Long> {
    
    // Find valid OTP by phone number and type
    @Query("SELECT o FROM OTPCode o WHERE o.phoneNumber = :phoneNumber AND o.type = :type AND o.isUsed = false AND o.expiresAt > :now ORDER BY o.createdAt DESC")
    Optional<OTPCode> findValidOTPByPhoneAndType(@Param("phoneNumber") String phoneNumber, 
                                                  @Param("type") String type, 
                                                  @Param("now") LocalDateTime now);
    
    // Find all OTPs by phone number and type (for rate limiting)
    @Query("SELECT o FROM OTPCode o WHERE o.phoneNumber = :phoneNumber AND o.type = :type ORDER BY o.createdAt DESC")
    List<OTPCode> findAllByPhoneAndType(@Param("phoneNumber") String phoneNumber, 
                                        @Param("type") String type);
    
    // Count recent OTP requests for rate limiting
    @Query("SELECT COUNT(o) FROM OTPCode o WHERE o.phoneNumber = :phoneNumber AND o.type = :type AND o.createdAt >= :since")
    long countRecentOTPRequests(@Param("phoneNumber") String phoneNumber, 
                                @Param("type") String type, 
                                @Param("since") LocalDateTime since);
    
    // Find expired OTPs for cleanup
    @Query("SELECT o FROM OTPCode o WHERE o.expiresAt < :now")
    List<OTPCode> findExpiredOTPs(@Param("now") LocalDateTime now);
    
    // Delete expired OTPs
    @Query("DELETE FROM OTPCode o WHERE o.expiresAt < :now")
    void deleteExpiredOTPs(@Param("now") LocalDateTime now);
} 