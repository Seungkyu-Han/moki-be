package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLoginId(String id);

    @Transactional
    void deleteByLoginId(String id);
}
