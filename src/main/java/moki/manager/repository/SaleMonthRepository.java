package moki.manager.repository;

import moki.manager.model.entity.SaleMonth;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleMonthRepository extends JpaRepository<SaleMonth, Integer> {

    Optional<SaleMonth> findByYearAndMonthAndUser(Integer year, Integer month, User user);
}
