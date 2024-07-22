package moki.manager.service;

import moki.manager.model.dto.date.DateRes.DateWeeklyRes;
import moki.manager.model.dto.date.DateRes.DateMonthlyRes;
import moki.manager.model.dto.date.DateRes.DateDailyRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

public interface DateService {
    ResponseEntity<DateDailyRes> getDailyOld(LocalDate localDate, Authentication authentication);

    ResponseEntity<DateMonthlyRes> getMonthlyOld(LocalDate localDate, Authentication authentication);

    ResponseEntity<DateWeeklyRes> getWeeklyOld(LocalDate localDate, Authentication authentication);

    ResponseEntity<DateDailyRes> getDaily(LocalDate localDate, Authentication authentication);
}
