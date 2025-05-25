package org.example.pasir_socha_mateusz.repository;

import org.example.pasir_socha_mateusz.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByGroupId(Long groupId);
}
