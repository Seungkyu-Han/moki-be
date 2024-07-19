package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SaleDayRepository extends JpaRepository<SaleDay, Long> {

    @Transactional
    void deleteBySaleMonth(SaleMonth saleMonth);

    Optional<SaleDay> findBySaleMonthAndDay(SaleMonth saleMonth, Integer day);
}
