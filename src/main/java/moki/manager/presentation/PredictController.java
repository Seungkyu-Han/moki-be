package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.predict.PredictRes;
import moki.manager.service.PredictService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/predict")
@RequiredArgsConstructor
public class PredictController {

    private final PredictService predictService;


    @GetMapping("/daily-detail")
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetDetailRes> getDailyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return predictService.getDailyDetail(localDate, authentication);
    }

    @GetMapping("/weekly-detail")
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetDetailRes> getWeeklyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return predictService.getWeeklyDetail(localDate, authentication);
    }

    @GetMapping("/monthly-detail")
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetDetailRes> getMonthlyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return predictService.getMonthlyDetail(localDate, authentication);
    }

    @GetMapping("/daily")
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetRes> getDaily(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return predictService.getDaily(localDate, authentication);
    }

    @GetMapping("/weekly")
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetRes> getWeekly(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return predictService.getWeekly(localDate, authentication);
    }

    @GetMapping("/monthly")
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetRes> getMonthly(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return predictService.getMonthly(localDate, authentication);
    }
}
