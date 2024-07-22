package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.date.DateRes.DateWeeklyRes;
import moki.manager.model.dto.date.DateRes.DateMonthlyRes;
import moki.manager.model.dto.date.DateRes.DateDailyRes;
import moki.manager.service.DateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/date")
@RequiredArgsConstructor
public class DateController {

    private final DateService dateService;

    @GetMapping("/daily")
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<DateDailyRes> getDaily(@RequestParam LocalDate localDate, @Parameter(hidden = true) Authentication authentication) {
        return dateService.getDaily(localDate, authentication);
    }

    @GetMapping("/monthly")
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<DateMonthlyRes> getMonthly(@RequestParam LocalDate localDate, @Parameter(hidden = true) Authentication authentication) {
        return dateService.getMonthly(localDate, authentication);
    }

    @GetMapping("/weekly")
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<DateWeeklyRes> getWeekly(@RequestParam LocalDate localDate, @Parameter(hidden = true) Authentication authentication){
        return dateService.getWeekly(localDate, authentication);
    }
}
