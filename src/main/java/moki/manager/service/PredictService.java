package moki.manager.service;

import moki.manager.model.dto.predict.PredictRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

public interface PredictService {
    ResponseEntity<PredictRes.PredictGetDetailRes> getDailyDetail(LocalDate localDate, Authentication authentication);

    ResponseEntity<PredictRes.PredictGetDetailRes> getWeeklyDetail(LocalDate localDate, Authentication authentication);

    ResponseEntity<PredictRes.PredictGetDetailRes> getMonthlyDetail(LocalDate localDate, Authentication authentication);

    ResponseEntity<PredictRes.PredictGetRes> getDaily(LocalDate localDate, Authentication authentication);

    ResponseEntity<PredictRes.PredictGetRes> getWeekly(LocalDate localDate, Authentication authentication);

    ResponseEntity<PredictRes.PredictGetRes> getMonthly(LocalDate localDate, Authentication authentication);
}
