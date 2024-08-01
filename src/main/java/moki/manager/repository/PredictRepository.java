package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.dao.predict.PredictDao;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.PredictSale;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PredictRepository extends JpaRepository<PredictSale, Integer> {


    @Query(
            "SELECT SUM(ps.count) FROM PredictSale ps " +
                    "WHERE ps.localDate BETWEEN :startDate and :endDate and ps.menuName = :menuName"
    )
    Float getPredictSum(MenuName menuName, LocalDate startDate, LocalDate endDate);

    @Transactional
    void deleteByMenuNameAndLocalDateBetween(MenuName menuName, LocalDate startDate, LocalDate endDate);

    Boolean existsByLocalDateAndMenuName(LocalDate endDate, MenuName menuName);

    List<PredictSale> findByMenuNameAndLocalDateBetween(MenuName menuName, LocalDate startDate, LocalDate endDate);
}
