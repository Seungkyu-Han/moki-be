package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
    @Parameters({
            @Parameter(name = "localDate", description = "날짜")
    })
    public ResponseEntity<SaleRes.SaleGetDailyDetailRes> getSaleDailyDetail(
            @RequestParam LocalDate localDate,
            @Parameter(hidden = true) Authentication authentication
    ){
        return saleService.getSaleDailyDetail(localDate, authentication);
    }
}
