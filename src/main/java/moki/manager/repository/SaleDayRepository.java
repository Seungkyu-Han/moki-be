package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleDayRepository extends JpaRepository<SaleDay, Long> {

    @Transactional
    void deleteBySaleMonth(SaleMonth saleMonth);

    Optional<SaleDay> findBySaleMonthAndLocalDate(SaleMonth saleMonth, LocalDate localDate);

    @Query(
            "SELECT sd FROM SaleDay sd " +
                    "WHERE (sd.saleMonth = :thisSaleMonth or sd.saleMonth = :lastSaleMonth) and sd.localDate between :startDate and :endDate"
    )
    List<SaleDay> findAllByMonth(SaleMonth thisSaleMonth, SaleMonth lastSaleMonth, LocalDate startDate, LocalDate endDate);

    List<SaleDay> findAllByLocalDateBetween(LocalDate startDate, LocalDate endDate);
}
