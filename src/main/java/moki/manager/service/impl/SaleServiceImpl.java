package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import moki.manager.model.entity.SaleDay;
import moki.manager.model.entity.SaleMonth;
import moki.manager.model.entity.User;
import moki.manager.repository.SaleDayRepository;
import moki.manager.repository.SaleMonthRepository;
import moki.manager.service.SaleService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleMonthRepository saleMonthRepository;
    private final SaleDayRepository saleDayRepository;

    @Override
    public ResponseEntity<HttpStatus> postUpload(Integer year, Integer month, MultipartFile multipartFile, Authentication authentication) {

        this.deleteUpload(year, month, authentication);

        try (InputStream is = multipartFile.getInputStream();

             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            ArrayList<SaleDay> saleDays = new ArrayList<>();

            int i;

            for (i = 0; i < getLastDayOfMonth(year, month); i++) {
                Row row = sheet.getRow(i + 2);

                Integer count = row.getCell(1) != null ? (int)row.getCell(1).getNumericCellValue() : 0;
                Integer sum = row.getCell(2) != null ? (int)row.getCell(2).getNumericCellValue() : 0;

                SaleDay saleDay = SaleDay.builder()
                        .count(count)
                        .sum(sum)
                        .time0(row.getCell(3) != null ? (int)row.getCell(3).getNumericCellValue() : 0)
                        .time1(row.getCell(4) != null ? (int)row.getCell(4).getNumericCellValue() : 0)
                        .time2(row.getCell(5) != null ? (int)row.getCell(5).getNumericCellValue() : 0)
                        .time3(row.getCell(6) != null ? (int)row.getCell(6).getNumericCellValue() : 0)
                        .time4(row.getCell(7) != null ? (int)row.getCell(7).getNumericCellValue() : 0)
                        .time5(row.getCell(8) != null ? (int)row.getCell(8).getNumericCellValue() : 0)
                        .time6(row.getCell(9) != null ? (int)row.getCell(9).getNumericCellValue() : 0)
                        .time7(row.getCell(10) != null ? (int)row.getCell(10).getNumericCellValue() : 0)
                        .time8(row.getCell(11) != null ? (int)row.getCell(11).getNumericCellValue() : 0)
                        .time9(row.getCell(12) != null ? (int)row.getCell(12).getNumericCellValue() : 0)
                        .time10(row.getCell(13) != null ? (int)row.getCell(13).getNumericCellValue() : 0)
                        .time11(row.getCell(14) != null ? (int)row.getCell(14).getNumericCellValue() : 0)
                        .time12(row.getCell(15) != null ? (int)row.getCell(15).getNumericCellValue() : 0)
                        .time13(row.getCell(16) != null ? (int)row.getCell(16).getNumericCellValue() : 0)
                        .time14(row.getCell(17) != null ? (int)row.getCell(17).getNumericCellValue() : 0)
                        .time15(row.getCell(18) != null ? (int)row.getCell(18).getNumericCellValue() : 0)
                        .time16(row.getCell(19) != null ? (int)row.getCell(19).getNumericCellValue() : 0)
                        .time17(row.getCell(20) != null ? (int)row.getCell(20).getNumericCellValue() : 0)
                        .time18(row.getCell(21) != null ? (int)row.getCell(21).getNumericCellValue() : 0)
                        .time19(row.getCell(22) != null ? (int)row.getCell(22).getNumericCellValue() : 0)
                        .time20(row.getCell(23) != null ? (int)row.getCell(23).getNumericCellValue() : 0)
                        .time21(row.getCell(24) != null ? (int)row.getCell(24).getNumericCellValue() : 0)
                        .time22(row.getCell(25) != null ? (int)row.getCell(25).getNumericCellValue() : 0)
                        .time23(row.getCell(26) != null ? (int)row.getCell(26).getNumericCellValue() : 0)
                        .build();

                saleDays.add(saleDay);
            }

            Row row = sheet.getRow(i + 2);

            Integer sum = row.getCell(2) != null ? (int)row.getCell(2).getNumericCellValue() : 0;
            Integer count = row.getCell(1) != null ? (int)row.getCell(1).getNumericCellValue() : 0;

            SaleMonth saleMonth = SaleMonth.builder()
                    .count(count)
                    .sum(sum)
                    .year(year)
                    .month(month)
                    .user(
                            User.builder().id(Integer.valueOf(authentication.getName())).build()
                    )
                    .time0(row.getCell(3) != null ? (int)row.getCell(3).getNumericCellValue() : 0)
                    .time1(row.getCell(4) != null ? (int)row.getCell(4).getNumericCellValue() : 0)
                    .time2(row.getCell(5) != null ? (int)row.getCell(5).getNumericCellValue() : 0)
                    .time3(row.getCell(6) != null ? (int)row.getCell(6).getNumericCellValue() : 0)
                    .time4(row.getCell(7) != null ? (int)row.getCell(7).getNumericCellValue() : 0)
                    .time5(row.getCell(8) != null ? (int)row.getCell(8).getNumericCellValue() : 0)
                    .time6(row.getCell(9) != null ? (int)row.getCell(9).getNumericCellValue() : 0)
                    .time7(row.getCell(10) != null ? (int)row.getCell(10).getNumericCellValue() : 0)
                    .time8(row.getCell(11) != null ? (int)row.getCell(11).getNumericCellValue() : 0)
                    .time9(row.getCell(12) != null ? (int)row.getCell(12).getNumericCellValue() : 0)
                    .time10(row.getCell(13) != null ? (int)row.getCell(13).getNumericCellValue() : 0)
                    .time11(row.getCell(14) != null ? (int)row.getCell(14).getNumericCellValue() : 0)
                    .time12(row.getCell(15) != null ? (int)row.getCell(15).getNumericCellValue() : 0)
                    .time13(row.getCell(16) != null ? (int)row.getCell(16).getNumericCellValue() : 0)
                    .time14(row.getCell(17) != null ? (int)row.getCell(17).getNumericCellValue() : 0)
                    .time15(row.getCell(18) != null ? (int)row.getCell(18).getNumericCellValue() : 0)
                    .time16(row.getCell(19) != null ? (int)row.getCell(19).getNumericCellValue() : 0)
                    .time17(row.getCell(20) != null ? (int)row.getCell(20).getNumericCellValue() : 0)
                    .time18(row.getCell(21) != null ? (int)row.getCell(21).getNumericCellValue() : 0)
                    .time19(row.getCell(22) != null ? (int)row.getCell(22).getNumericCellValue() : 0)
                    .time20(row.getCell(23) != null ? (int)row.getCell(23).getNumericCellValue() : 0)
                    .time21(row.getCell(24) != null ? (int)row.getCell(24).getNumericCellValue() : 0)
                    .time22(row.getCell(25) != null ? (int)row.getCell(25).getNumericCellValue() : 0)
                    .time23(row.getCell(26) != null ? (int)row.getCell(26).getNumericCellValue() : 0)
                    .build();

            saleMonthRepository.save(saleMonth);

            for (SaleDay saleDay : saleDays) {
                saleDay.setSaleMonth(saleMonth);
            }

            saleDayRepository.saveAll(saleDays);


        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.", e);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUpload(Integer year, Integer month, Authentication authentication) {

        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val existedSaleMonth = saleMonthRepository.findByYearAndMonthAndUser(year, month, user);

        if (existedSaleMonth.isPresent()) {
            saleDayRepository.deleteBySaleMonth(existedSaleMonth.get());
            saleMonthRepository.delete(existedSaleMonth.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public int getLastDayOfMonth(int year, int month) {
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        return lastDayOfMonth.getDayOfMonth();
    }
}
