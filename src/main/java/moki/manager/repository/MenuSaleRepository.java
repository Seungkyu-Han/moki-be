package moki.manager.repository;

import moki.manager.model.entity.MenuDay;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.MenuSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuSaleRepository extends JpaRepository<MenuSale, Integer> {
    List<MenuSale> findByMenuDay(MenuDay todayMenu);

    void deleteByMenuDayAndMenuName(MenuDay menuDay, MenuName menuName);

    @Query(
            "SELECT SUM(ms.count * ms.menuName.price) FROM MenuSale ms " +
                    "where ms.menuDay = :menuDay"
    )
    Integer getSumByMenuDay(MenuDay menuDay);
}
