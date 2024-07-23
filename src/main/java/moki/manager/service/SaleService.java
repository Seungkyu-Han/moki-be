package moki.manager.service;

import moki.manager.model.dto.sale.SaleRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface SaleService {
    ResponseEntity<HttpStatus> postUpload(Integer year, Integer month, MultipartFile multipartFile, Authentication authentication);

    ResponseEntity<HttpStatus> deleteUpload(Integer year, Integer month, Authentication authentication);

    ResponseEntity<SaleRes.SaleGetDailyDetailRes> getSaleDailyDetail(LocalDate localDate, Authentication authentication);

    ResponseEntity<SaleRes.SaleGetRankRes> getSaleDailyRank(LocalDate localDate, Authentication authentication);

    ResponseEntity<SaleRes.SaleGetRankRes> getSaleMonthlyRank(LocalDate localDate, Authentication authentication);

    ResponseEntity<SaleRes.SaleGetRankRes> getSaleWeeklyRank(LocalDate localDate, Authentication authentication);
}
