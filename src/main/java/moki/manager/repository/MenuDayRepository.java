package moki.manager.repository;

import moki.manager.model.entity.MenuDay;
import moki.manager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MenuDayRepository extends JpaRepository<MenuDay, Integer> {
    Optional<MenuDay> findByUserAndLocalDate(User user, LocalDate localDate);
    List<MenuDay> findByUserAndLocalDateBetween(User user, LocalDate startDate, LocalDate endDate);

    Optional<MenuDay> findTopByUserOrderByIdDesc(User user);
}
