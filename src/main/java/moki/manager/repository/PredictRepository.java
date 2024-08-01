package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.PredictSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PredictRepository extends JpaRepository<PredictSale, Integer> {

    @Transactional
    void deleteByMenuNameAndLocalDateBetween(MenuName menuName, LocalDate startDate, LocalDate endDate);

    Boolean existsByLocalDateAndMenuName(LocalDate endDate, MenuName menuName);

    List<PredictSale> findByMenuNameAndLocalDateBetweenOrderByLocalDate(MenuName menuName, LocalDate startDate, LocalDate endDate);
}
