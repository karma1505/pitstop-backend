package com.garage.backend.loyalty.repository;

import com.garage.backend.loyalty.entity.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, UUID> {

    // Find by customer ID
    Optional<LoyaltyProgram> findByCustomerId(UUID customerId);

    // Find by program name
    List<LoyaltyProgram> findByProgramName(String programName);

    // Find by program name containing text
    List<LoyaltyProgram> findByProgramNameContainingIgnoreCase(String programName);

    // Find active programs
    List<LoyaltyProgram> findByIsActiveTrue();

    // Find by points balance range
    List<LoyaltyProgram> findByPointsBalanceBetween(Integer minPoints, Integer maxPoints);

    // Find by total points earned range
    List<LoyaltyProgram> findByTotalPointsEarnedBetween(Integer minPoints, Integer maxPoints);

    // Find by total points redeemed range
    List<LoyaltyProgram> findByTotalPointsRedeemedBetween(Integer minPoints, Integer maxPoints);

    // Find by points to currency ratio
    List<LoyaltyProgram> findByPointsToCurrencyRatio(BigDecimal ratio);

    // Find by last activity date range
    List<LoyaltyProgram> findByLastActivityDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find by customer ID and active status
    List<LoyaltyProgram> findByCustomerIdAndIsActive(UUID customerId, Boolean isActive);

    // Find by program name and active status
    List<LoyaltyProgram> findByProgramNameAndIsActive(String programName, Boolean isActive);

    // Find programs with high points balance
    List<LoyaltyProgram> findByPointsBalanceGreaterThan(Integer minPoints);

    // Find programs with low points balance
    List<LoyaltyProgram> findByPointsBalanceLessThan(Integer maxPoints);

    // Custom query to find loyalty programs by multiple criteria
    @Query("SELECT lp FROM LoyaltyProgram lp WHERE " +
           "(:customerId IS NULL OR lp.customerId = :customerId) AND " +
           "(:programName IS NULL OR lp.programName LIKE %:programName%) AND " +
           "(:isActive IS NULL OR lp.isActive = :isActive) AND " +
           "(:minPoints IS NULL OR lp.pointsBalance >= :minPoints) AND " +
           "(:maxPoints IS NULL OR lp.pointsBalance <= :maxPoints)")
    List<LoyaltyProgram> findLoyaltyProgramsByCriteria(@Param("customerId") UUID customerId,
                                                     @Param("programName") String programName,
                                                     @Param("isActive") Boolean isActive,
                                                     @Param("minPoints") Integer minPoints,
                                                     @Param("maxPoints") Integer maxPoints);

    // Find recent loyalty programs
    List<LoyaltyProgram> findTop10ByOrderByCreatedAtDesc();

    // Find loyalty programs by total points earned
    List<LoyaltyProgram> findByTotalPointsEarned(Integer totalPointsEarned);

    // Find loyalty programs by total points redeemed
    List<LoyaltyProgram> findByTotalPointsRedeemed(Integer totalPointsRedeemed);

    // Find loyalty programs by points to currency ratio range
    List<LoyaltyProgram> findByPointsToCurrencyRatioBetween(BigDecimal minRatio, BigDecimal maxRatio);

    // Get total points balance for all active programs
    @Query("SELECT SUM(lp.pointsBalance) FROM LoyaltyProgram lp WHERE lp.isActive = true")
    Integer getTotalPointsBalanceForActivePrograms();

    // Get total points earned for all programs
    @Query("SELECT SUM(lp.totalPointsEarned) FROM LoyaltyProgram lp")
    Integer getTotalPointsEarnedForAllPrograms();

    // Get total points redeemed for all programs
    @Query("SELECT SUM(lp.totalPointsRedeemed) FROM LoyaltyProgram lp")
    Integer getTotalPointsRedeemedForAllPrograms();
}
