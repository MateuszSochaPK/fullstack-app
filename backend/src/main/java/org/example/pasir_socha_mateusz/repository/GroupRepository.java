package org.example.pasir_socha_mateusz.repository;

import org.example.pasir_socha_mateusz.model.Group;
import org.example.pasir_socha_mateusz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMemberships_User(User user);
}
