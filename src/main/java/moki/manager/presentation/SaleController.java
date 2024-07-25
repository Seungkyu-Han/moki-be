package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.sale.SaleRes;
import moki.manager.service.SaleService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Parameters({
//            @Parameter(name = "year", description = "해당 엑셀파일의 년도"),
//            @Parameter(name = "month", description = "해당 엑셀파일의 월")
//    })
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "저장 성공")
//    })
//    public ResponseEntity<HttpStatus> postUpload(
//            @RequestParam Integer year,
//            @RequestParam Integer month,
//            @RequestPart MultipartFile multipartFile,
//            @Parameter(hidden = true) Authentication authentication){
//        return saleService.postUpload(year, month, multipartFile, authentication);
//    }
//
//    @DeleteMapping("/upload")
//    @Parameters({
//            @Parameter(name = "year", description = "삭제할 엑셀파일의 년도"),
//            @Parameter(name = "month", description = "삭제할 엑셀파일의 월")
//    })
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "삭제 성공")
//    })
//    public ResponseEntity<HttpStatus> deleteUpload(
//            @RequestParam Integer year,
//            @RequestParam Integer month,
//            @Parameter(hidden = true) Authentication authentication
//    ){
//        return saleService.deleteUpload(year, month, authentication);
//    }


    @GetMapping("/daily-detail")
    @Operation(summary = "하루 매출 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SaleRes.SaleGetDailyDetailRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetDailyDetailRes> getSaleDailyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleDailyDetail(localDate, authentication);
    }

    @GetMapping("/weekly-detail")
    @Operation(summary = "일주일 매출 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SaleRes.SaleGetDailyDetailRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetDailyDetailRes> getSaleWeeklyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleWeeklyDetail(localDate, authentication);
    }

    @GetMapping("/monthly-detail")
    @Operation(summary = "한달 매출 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SaleRes.SaleGetDailyDetailRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetDailyDetailRes> getSaleMonthlyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleMonthlyDetail(localDate, authentication);
    }

    @GetMapping("/daily-rank")
    @Operation(summary = "하루 매출 랭킹 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SaleRes.SaleGetRankRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetRankRes> getSaleDailyRank(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleDailyRank(localDate, authentication);
    }

    @GetMapping("/weekly-rank")
    @Operation(summary = "일주일 매출 랭킹 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SaleRes.SaleGetRankRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetRankRes> getSaleWeeklyRank(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleWeeklyRank(localDate, authentication);
    }

    @GetMapping("/monthly-rank")
    @Operation(summary = "한달 매출 랭킹 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = SaleRes.SaleGetRankRes.class))),
    })
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetRankRes> getSaleMonthlyRank(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleMonthlyRank(localDate, authentication);
    }
}
