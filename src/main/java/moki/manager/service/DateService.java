package moki.manager.service;

import moki.manager.model.dto.date.DateRes.DateMonthlyRes;
import moki.manager.model.dto.date.DateRes.DateDailyRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

public interface DateService {
    ResponseEntity<DateDailyRes> getDaily(LocalDate localDate, Authentication authentication);

    ResponseEntity<DateMonthlyRes> getMonthly(LocalDate localDate, Authentication authentication);
}
