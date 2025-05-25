package org.example.pasir_socha_mateusz.repository;

import org.example.pasir_socha_mateusz.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByGroupId(Long groupId);
}
