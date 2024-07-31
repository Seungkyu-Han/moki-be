package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuNameRepository extends JpaRepository<MenuName, Integer> {

    Optional<MenuName> findByName(String key);

    List<MenuName> findAllByUser(User user);

    Optional<MenuName> findByNameAndUser(String key, User user);

    @Transactional
    void deleteByNameAndUser(String name, User user);

    boolean existsByNameAndUser(String name, User user);

    void deleteByName(String name);
}
