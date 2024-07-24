package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.predict.PredictRes;
import moki.manager.service.PredictService;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "하루 예측 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PredictRes.PredictGetDetailRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetDetailRes> getDailyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return predictService.getDailyDetail(localDate, authentication);
    }

    @GetMapping("/weekly-detail")
    @Operation(summary = "일주일 예측 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PredictRes.PredictGetDetailRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetDetailRes> getWeeklyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return predictService.getWeeklyDetail(localDate, authentication);
    }

    @GetMapping("/monthly-detail")
    @Operation(summary = "한달 예측 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PredictRes.PredictGetDetailRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "예측을 시작할 날짜")
    })
    public ResponseEntity<PredictRes.PredictGetDetailRes> getMonthlyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication) {
        return predictService.getMonthlyDetail(localDate, authentication);
    }

    @GetMapping("/daily")
    @Operation(summary = "하루 예측 총합 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PredictRes.PredictGetRes.class))),
    })
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
    @Operation(summary = "일주일 예측 총합 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PredictRes.PredictGetRes.class))),
    })
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
    @Operation(summary = "한달 예측 총합 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PredictRes.PredictGetRes.class))),
    })
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
