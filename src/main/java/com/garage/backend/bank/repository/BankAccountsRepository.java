package com.garage.backend.bank.repository;

import com.garage.backend.bank.entity.BankAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountsRepository extends JpaRepository<BankAccounts, UUID> {

    // Find by account number
    Optional<BankAccounts> findByAccountNumber(String accountNumber);

    // Find by bank name
    List<BankAccounts> findByBankName(String bankName);

    // Find active accounts
    List<BankAccounts> findByIsActiveTrue();

    // Find by account type
    List<BankAccounts> findByAccountType(String accountType);

    // Find by IFSC code
    List<BankAccounts> findByIfscCode(String ifscCode);

    // Check if account number exists
    boolean existsByAccountNumber(String accountNumber);

    // Custom query to find accounts by multiple criteria
    @Query("SELECT ba FROM BankAccounts ba WHERE " +
           "(:bankName IS NULL OR ba.bankName = :bankName) AND " +
           "(:accountType IS NULL OR ba.accountType = :accountType) AND " +
           "(:isActive IS NULL OR ba.isActive = :isActive)")
    List<BankAccounts> findAccountsByCriteria(@Param("bankName") String bankName,
                                             @Param("accountType") String accountType,
                                             @Param("isActive") Boolean isActive);

    // Find accounts by account holder name containing text
    List<BankAccounts> findByAccountHolderNameContainingIgnoreCase(String accountHolderName);
}
