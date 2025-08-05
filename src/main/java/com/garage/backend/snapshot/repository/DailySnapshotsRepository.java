package com.garage.backend.snapshot.repository;

import com.garage.backend.snapshot.entity.DailySnapshots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DailySnapshotsRepository extends JpaRepository<DailySnapshots, UUID> {

    // Find by snapshot date
    List<DailySnapshots> findBySnapshotDate(LocalDate snapshotDate);

    // Find by branch ID
    List<DailySnapshots> findByBranchId(UUID branchId);

    // Find by snapshot date range
    List<DailySnapshots> findBySnapshotDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by branch ID and snapshot date
    Optional<DailySnapshots> findByBranchIdAndSnapshotDate(UUID branchId, LocalDate snapshotDate);

    // Find by branch ID and date range
    List<DailySnapshots> findByBranchIdAndSnapshotDateBetween(UUID branchId, LocalDate startDate, LocalDate endDate);

    // Find by total sales range
    List<DailySnapshots> findByTotalSalesBetween(BigDecimal minSales, BigDecimal maxSales);

    // Find by total expenses range
    List<DailySnapshots> findByTotalExpensesBetween(BigDecimal minExpenses, BigDecimal maxExpenses);

    // Find by total profit range
    List<DailySnapshots> findByTotalProfitBetween(BigDecimal minProfit, BigDecimal maxProfit);

    // Custom query to find snapshots by multiple criteria
    @Query("SELECT ds FROM DailySnapshots ds WHERE " +
           "(:branchId IS NULL OR ds.branchId = :branchId) AND " +
           "(:startDate IS NULL OR ds.snapshotDate >= :startDate) AND " +
           "(:endDate IS NULL OR ds.snapshotDate <= :endDate)")
    List<DailySnapshots> findSnapshotsByCriteria(@Param("branchId") UUID branchId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    // Find recent snapshots
    List<DailySnapshots> findTop10ByOrderBySnapshotDateDesc();

    // Find snapshots by total invoices range
    List<DailySnapshots> findByTotalInvoicesBetween(Integer minInvoices, Integer maxInvoices);

    // Find snapshots by total job cards range
    List<DailySnapshots> findByTotalJobCardsBetween(Integer minJobCards, Integer maxJobCards);

    // Find snapshots by total customers range
    List<DailySnapshots> findByTotalCustomersBetween(Integer minCustomers, Integer maxCustomers);

    // Get total sales for a date range
    @Query("SELECT SUM(ds.totalSales) FROM DailySnapshots ds WHERE ds.branchId = :branchId AND ds.snapshotDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalSalesForDateRange(@Param("branchId") UUID branchId,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    // Get total expenses for a date range
    @Query("SELECT SUM(ds.totalExpenses) FROM DailySnapshots ds WHERE ds.branchId = :branchId AND ds.snapshotDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpensesForDateRange(@Param("branchId") UUID branchId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);

    // Get total profit for a date range
    @Query("SELECT SUM(ds.totalProfit) FROM DailySnapshots ds WHERE ds.branchId = :branchId AND ds.snapshotDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalProfitForDateRange(@Param("branchId") UUID branchId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);
}
