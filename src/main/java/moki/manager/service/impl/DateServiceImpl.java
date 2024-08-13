package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import moki.manager.model.dto.date.DateRes.DateWeeklyRes;
import moki.manager.model.dto.date.DateRes.DateMonthlyRes;
import moki.manager.model.dto.date.DateRes.DateDailyRes;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.User;
import moki.manager.repository.MenuDayRepository;
import moki.manager.repository.MenuNameRepository;
import moki.manager.repository.MenuSaleRepository;
import moki.manager.repository.SaleDayRepository;
import moki.manager.repository.SaleMonthRepository;
import moki.manager.service.DateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class DateServiceImpl implements DateService {

    private final SaleMonthRepository saleMonthRepository;
    private final SaleDayRepository saleDayRepository;

    private final MenuDayRepository menuDayRepository;
    private final MenuNameRepository menuNameRepository;
    private final MenuSaleRepository menuSaleRepository;

    @Override
    public ResponseEntity<DateDailyRes> getDaily(LocalDate localDate, Authentication authentication) {

        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val yesterday = localDate.minusDays(1);

        AtomicInteger todaySale = new AtomicInteger(0);
        AtomicInteger yesterdaySale = new AtomicInteger(0);

        val todayMenu = menuDayRepository.findByUserAndLocalDate(user, localDate);
        val yesterdayMenu = menuDayRepository.findByUserAndLocalDate(user, yesterday);

        todayMenu.ifPresent(menuDay -> menuSaleRepository.findByMenuDay(menuDay).forEach(
                menuSale -> todaySale.addAndGet(menuSale.getMenuName().getPrice() * menuSale.getCount())
        ));

        yesterdayMenu.ifPresent(menuDay -> menuSaleRepository.findByMenuDay(menuDay).forEach(
                menuSale -> yesterdaySale.addAndGet(menuSale.getMenuName().getPrice() * menuSale.getCount())
        ));


        return new ResponseEntity<>(
                DateDailyRes.builder()
                        .today(todaySale.get())
                        .yesterday(yesterdaySale.get())
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DateWeeklyRes> getWeekly(LocalDate localDate, Authentication authentication) {

        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val startDate2 = localDate.with(DayOfWeek.MONDAY);
        val endDate2 = localDate.with(DayOfWeek.SUNDAY);
        val startDate1 = startDate2.minusDays(7);
        val endDate1 = endDate2.minusDays(7);

        return new ResponseEntity<>(
                DateWeeklyRes.builder()
                        .today(getSumBetweenLocalDate(user, startDate1, endDate1))
                        .yesterday(getSumBetweenLocalDate(user, startDate2, endDate2))
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DateMonthlyRes> getMonthly(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val startDate2 = localDate.withDayOfMonth(1);
        val endDate2 = localDate.withDayOfMonth(localDate.lengthOfMonth());
        val startDate1 = startDate2.minusMonths(1);
        val endDate1 = endDate2.minusMonths(1);

        return new ResponseEntity<>(
                DateMonthlyRes.builder()
                        .yesterday(getSumBetweenLocalDate(user, startDate1, endDate1))
                        .today(getSumBetweenLocalDate(user, startDate2, endDate2))
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<DateDailyRes> getDailyOld(LocalDate localDate, Authentication authentication) {

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
    public ResponseEntity<DateMonthlyRes> getMonthlyOld(LocalDate localDate, Authentication authentication) {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        LocalDate lastMonth = localDate.minusMonths(1);
        LocalDate twoMonthsAgo = localDate.minusMonths(2);

        List<SaleDay> thisSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(lastMonth, localDate, user);
        List<SaleDay> lastSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(twoMonthsAgo, lastMonth, user);

        return new ResponseEntity<>(
                DateMonthlyRes.builder()
                .today(thisSaleDays.stream()
                        .mapToInt(SaleDay::getSum)
                        .sum())
                .yesterday(lastSaleDays.stream()
                        .mapToInt(SaleDay::getSum)
                        .sum())
                .build(), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<DateWeeklyRes> getWeeklyOld(LocalDate localDate, Authentication authentication) {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        LocalDate lastWeek = localDate.minusWeeks(1);
        LocalDate twoWeeksAgo = localDate.minusWeeks(2);

        List<SaleDay> thisWeekSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(lastWeek, localDate, user);
        List<SaleDay> lastWeekSaleDays = saleDayRepository.findAllByLocalDateBetweenAndUser(twoWeeksAgo, lastWeek, user);

        return new ResponseEntity<>(
                DateWeeklyRes.builder()
                        .today(thisWeekSaleDays.stream()
                                .mapToInt(SaleDay::getSum)
                                .sum())
                        .yesterday(lastWeekSaleDays.stream()
                                .mapToInt(SaleDay::getSum)
                                .sum())
                        .build(), HttpStatus.OK);
    }

    private int getSumBetweenLocalDate(User user, LocalDate startDate, LocalDate endDate) {

        AtomicInteger sum = new AtomicInteger(0);

        val menuDays = menuDayRepository.findByUserAndLocalDateBetween(user, startDate, endDate);

        menuDays.forEach(
                menuDay -> {
                    val sumValue = menuSaleRepository.getSumByMenuDay(menuDay);
                    if(sumValue != null)
                        sum.addAndGet(sumValue);
                }
        );

        return sum.get();
    }
}
