package moki.manager.presentation;

import moki.manager.model.entity.MenuDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDayRepository extends JpaRepository<MenuDay, Integer> {
}
