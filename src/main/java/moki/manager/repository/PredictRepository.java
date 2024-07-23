package moki.manager.repository;

import moki.manager.model.dao.predict.PredictDao;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.PredictSale;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PredictRepository extends JpaRepository<PredictSale, Integer> {
    List<PredictSale> findByMenuNameAndLocalDate(MenuName menuName, LocalDate localDate);


    @Query(
            "SELECT new moki.manager.model.dao.predict.PredictDao(ps.localDate, ps.count) " +
                    "FROM PredictSale ps " +
                    "WHERE ps.menuName = :menuName and ps.localDate between :startDate and :endDate"
    )
    List<PredictDao> getPredictDao(MenuName menuName, LocalDate startDate, LocalDate endDate);


    @Query(
            "SELECT SUM(ps.count) FROM PredictSale ps " +
                    "WHERE ps.localDate BETWEEN :startDate and :endDate and ps.menuName = :menuName"
    )
    Float getPredictSum(MenuName menuName, LocalDate startDate, LocalDate endDate);
}
