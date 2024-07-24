package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

//    @GetMapping("/daily-old")
//    @Operation(description = "사용 X")
//    @Parameters({
//            @Parameter(name = "localDate", description = "날짜")
//    })
//    public ResponseEntity<DateDailyRes> getDailyOld(@RequestParam LocalDate localDate, @Parameter(hidden = true) Authentication authentication) {
//        return dateService.getDailyOld(localDate, authentication);
//    }
//
//    @GetMapping("/monthly-old")
//    @Operation(description = "사용 X")
//    @Parameters({
//            @Parameter(name = "localDate", description = "날짜")
//    })
//    public ResponseEntity<DateMonthlyRes> getMonthlyOld(@RequestParam LocalDate localDate, @Parameter(hidden = true) Authentication authentication) {
//        return dateService.getMonthlyOld(localDate, authentication);
//    }
//
//    @GetMapping("/weekly-old")
//    @Operation(description = "사용 X")
//    @Parameters({
//            @Parameter(name = "localDate", description = "날짜")
//    })
//    public ResponseEntity<DateWeeklyRes> getWeeklyOld(@RequestParam LocalDate localDate, @Parameter(hidden = true) Authentication authentication){
//        return dateService.getWeeklyOld(localDate, authentication);
//    }

    @GetMapping("/daily")
    @Operation(summary = "하루의 매출을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = DateDailyRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<DateDailyRes> getDaily(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return dateService.getDaily(localDate, authentication);
    }

    @GetMapping("/weekly")
    @Operation(summary = "일주일의 매출을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = DateWeeklyRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<DateWeeklyRes> getWeekly(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return dateService.getWeekly(localDate, authentication);
    }

    @GetMapping("/monthly")
    @Operation(summary = "한달의 매출을 조회하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = DateMonthlyRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<DateMonthlyRes> getMonthly(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return dateService.getMonthly(localDate, authentication);
    }
}
