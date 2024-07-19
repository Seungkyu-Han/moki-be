package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        Optional<SaleDay> todaySale = saleDayRepository.findBySaleMonthAndDay(todayMonth.get(), localDate.getDayOfMonth());
        Optional<SaleDay> yesterdaySale = saleDayRepository.findBySaleMonthAndDay(yesterdayMonth.get(), yesterday.getDayOfMonth());

        if (todaySale.isEmpty() || yesterdaySale.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        DateDailyRes dateDailyRes = DateDailyRes.builder()
                .today(todaySale.get().getSum())
                .yesterday(yesterdaySale.get().getSum())
                .build();

        return new ResponseEntity<>(dateDailyRes, HttpStatus.OK);
    }


}
