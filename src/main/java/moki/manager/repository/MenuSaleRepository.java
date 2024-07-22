package moki.manager.repository;

import moki.manager.model.entity.MenuDay;
import moki.manager.model.entity.MenuSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuSaleRepository extends JpaRepository<MenuSale, Integer> {
    List<MenuSale> findByMenuDay(MenuDay todayMenu);
}
