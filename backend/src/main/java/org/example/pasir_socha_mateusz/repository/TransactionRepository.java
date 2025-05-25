package org.example.pasir_socha_mateusz.repository;
import org.example.pasir_socha_mateusz.model.Transaction;
import org.example.pasir_socha_mateusz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);
}
