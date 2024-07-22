package moki.manager.repository;

import moki.manager.model.entity.MenuName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuNameRepository extends JpaRepository<MenuName, Integer> {

    boolean existsByName(String menuName);

    Optional<MenuName> findByName(String key);
}
