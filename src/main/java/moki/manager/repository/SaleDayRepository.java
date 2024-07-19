package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDayRepository extends JpaRepository<SaleDay, Long> {

    @Transactional
    void deleteBySaleMonth(SaleMonth saleMonth);
}
