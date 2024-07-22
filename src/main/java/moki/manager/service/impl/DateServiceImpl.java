package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import moki.manager.model.dto.date.DateRes.DateWeeklyRes;
import moki.manager.model.dto.date.DateRes.DateMonthlyRes;
import moki.manager.model.dto.date.DateRes.DateDailyRes;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import moki.manager.model.entity.User;
import moki.manager.repository.SaleDayRepository;
import moki.manager.repository.SaleMonthRepository;
import moki.manager.service.DateService;
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

        val todaySale = saleDayRepository.findByLocalDateAndUser(localDate, user);
        val yesterdaySale = saleDayRepository.findByLocalDateAndUser(yesterday, user);

        DateDailyRes dateDailyRes = DateDailyRes.builder()
                .today((todaySale.isPresent()) ? todaySale.get().getSum() : 0)
                .yesterday((yesterdaySale.isPresent()) ? yesterdaySale.get().getSum() : 0)
                .build();

        return new ResponseEntity<>(dateDailyRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DateMonthlyRes> getMonthly(LocalDate localDate, Authentication authentication) {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        LocalDate lastMonth = localDate.minusMonths(1);
        LocalDate twoMonthsAgo = localDate.minusMonths(2);

        List<SaleDay> thisSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(lastMonth, localDate, user);
        List<SaleDay> lastSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(twoMonthsAgo, lastMonth, user);

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

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        LocalDate lastWeek = localDate.minusWeeks(1);
        LocalDate twoWeeksAgo = localDate.minusWeeks(2);

        List<SaleDay> thisWeekSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(lastWeek, localDate, user);
        List<SaleDay> lastWeekSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(twoWeeksAgo, lastWeek, user);

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
