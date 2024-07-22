package moki.manager.repository;

import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MenuNameRepository extends JpaRepository<MenuName, Integer> {

    boolean existsByName(String menuName);

    Optional<MenuName> findByName(String key);

    List<MenuName> findAllByUser(User user);

    Optional<MenuName> findByNameAndUser(String key, User user);
}
