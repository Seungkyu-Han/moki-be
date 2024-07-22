package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moki.manager.model.dto.date.DateRes.DateWeeklyRes;
import moki.manager.model.dto.date.DateRes.DateMonthlyRes;
import moki.manager.model.dto.date.DateRes.DateDailyRes;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import moki.manager.model.entity.User;
import moki.manager.repository.SaleDayRepository;
import moki.manager.repository.SaleMonthRepository;
import moki.manager.service.DateService;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DateServiceImpl implements DateService {

    private final SaleMonthRepository saleMonthRepository;
    private final SaleDayRepository saleDayRepository;


    @Override
    public ResponseEntity<DateDailyRes> getDaily(LocalDate localDate, Authentication authentication) {

        User user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        LocalDate yesterday = localDate.minusDays(1);

        Optional<SaleMonth> todayMonth = saleMonthRepository.findByYearAndMonthAndUser(localDate.getYear(), localDate.getMonthValue(), user);
        Optional<SaleMonth> yesterdayMonth = saleMonthRepository.findByYearAndMonthAndUser(yesterday.getYear(), yesterday.getMonthValue(), user);

        if (todayMonth.isEmpty() || yesterdayMonth.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<SaleDay> todaySale = saleDayRepository.findBySaleMonthAndLocalDate(todayMonth.get(), localDate);
        Optional<SaleDay> yesterdaySale = saleDayRepository.findBySaleMonthAndLocalDate(yesterdayMonth.get(), yesterday);

        if (todaySale.isEmpty() || yesterdaySale.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DateDailyRes dateDailyRes = DateDailyRes.builder()
                .today(todaySale.get().getSum())
                .yesterday(yesterdaySale.get().getSum())
                .build();

        return new ResponseEntity<>(dateDailyRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DateMonthlyRes> getMonthly(LocalDate localDate, Authentication authentication) {

        LocalDate lastMonth = localDate.minusMonths(1);
        LocalDate twoMonthsAgo = localDate.minusMonths(2);

        List<SaleDay> thisSaleDays = saleDayRepository.findAllByLocalDateBetween(lastMonth, localDate);
        List<SaleDay> lastSaleDays = saleDayRepository.findAllByLocalDateBetween(twoMonthsAgo, lastMonth);

        return new ResponseEntity<>(
                DateMonthlyRes.builder()
                .thisMonth(thisSaleDays.stream()
                        .mapToInt(SaleDay::getSum)
                        .sum())
                .lastMonth(lastSaleDays.stream()
                        .mapToInt(SaleDay::getSum)
                        .sum())
                .build(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<DateWeeklyRes> getWeekly(LocalDate localDate, Authentication authentication) {

        LocalDate lastWeek = localDate.minusWeeks(1);
        LocalDate twoWeeksAgo = localDate.minusWeeks(2);

        List<SaleDay> thisWeekSaleDays = saleDayRepository.findAllByLocalDateBetween(lastWeek, localDate);
        List<SaleDay> lastWeekSaleDays = saleDayRepository.findAllByLocalDateBetween(twoWeeksAgo, lastWeek);

        return new ResponseEntity<>(
                DateWeeklyRes.builder()
                        .thisWeek(thisWeekSaleDays.stream()
                                .mapToInt(SaleDay::getSum)
                                .sum())
                        .lastWeek(lastWeekSaleDays.stream()
                                .mapToInt(SaleDay::getSum)
                                .sum())
                        .build(), HttpStatus.OK);
    }
}
