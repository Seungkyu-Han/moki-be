package moki.manager.repository;

import jakarta.transaction.Transactional;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SaleDayRepository extends JpaRepository<SaleDay, Long> {

    void deleteByUserAndLocalDateBetween(User user, LocalDate startDate, LocalDate endDate);

    Optional<SaleDay> findByLocalDateAndUser(LocalDate localDate, User user);


    List<SaleDay> findAllByLocalDateBetweenAndUser(LocalDate startDate, LocalDate endDate, User user);
}
