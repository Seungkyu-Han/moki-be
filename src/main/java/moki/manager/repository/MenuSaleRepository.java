package moki.manager.repository;

import moki.manager.model.dao.sale.SaleRankDao;
import moki.manager.model.entity.MenuDay;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.MenuSale;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    @Query("SELECT new moki.manager.model.dao.sale.SaleRankDao(mn.name, mn.price, SUM(ms.count)) " +
            "FROM MenuSale ms " +
            "LEFT JOIN MenuDay md ON ms.menuDay.id = md.id " +
            "LEFT JOIN MenuName mn ON ms.menuName.id = mn.id " +
            "WHERE md.localDate BETWEEN :startDate AND :endDate " +
            "AND md.user = :user " +
            "GROUP BY mn.id, mn.price " +
            "ORDER BY SUM(ms.count)")
    List<SaleRankDao> getSaleRank(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(
            "SELECT mn.name, sum(ms.count), mn.price FROM MenuSale ms " +
                    "LEFT JOIN MenuDay md ON md.id = ms.menuDay.id" +
                    " JOIN MenuName mn ON mn.id = ms.menuName.id " +
                    "WHERE md.localDate between :startDate and :endDate " +
                    "AND md.user = :user " +
                    "GROUP BY mn.id ORDER BY sum(ms.count) DESC"
    )
    List<Object[]> findTotalByUser(User user, LocalDate startDate, LocalDate endDate);
}
