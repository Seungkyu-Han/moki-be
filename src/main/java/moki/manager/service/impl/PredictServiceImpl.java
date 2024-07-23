package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import moki.manager.model.dto.predict.PredictRes;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.User;
import moki.manager.repository.MenuNameRepository;
import moki.manager.repository.PredictRepository;
import moki.manager.service.PredictService;
import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PredictServiceImpl implements PredictService {

    private final PredictRepository predictRepository;
    private final MenuNameRepository menuNameRepository;


    @Override
    public ResponseEntity<PredictRes.PredictGetDetailRes> getDailyDetail(LocalDate localDate, Authentication authentication) {

        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        return new ResponseEntity<>(
                PredictRes.PredictGetDetailRes.builder()
                        .predicts( menuNameRepository.findAllByUser(user).stream().map(
                                menuName -> PredictRes.PredictGetResElement.builder()
                                        .name(menuName.getName())
                                        .predictData(getPredictByMenuNameAndLocalDate(menuName, localDate, localDate))
                                        .build()).toList())
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetDetailRes> getWeeklyDetail(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        return new ResponseEntity<>(
                PredictRes.PredictGetDetailRes.builder()
                        .predicts( menuNameRepository.findAllByUser(user).stream().map(
                                menuName -> PredictRes.PredictGetResElement.builder()
                                        .name(menuName.getName())
                                        .predictData(getPredictByMenuNameAndLocalDate(menuName, localDate, localDate.plusWeeks(1).minusDays(1)))
                                        .build()).toList())
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetDetailRes> getMonthlyDetail(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        return new ResponseEntity<>(
                PredictRes.PredictGetDetailRes.builder()
                        .predicts( menuNameRepository.findAllByUser(user).stream().map(
                                menuName -> PredictRes.PredictGetResElement.builder()
                                        .name(menuName.getName())
                                        .predictData(getPredictByMenuNameAndLocalDate(menuName, localDate, localDate.plusMonths(1).minusDays(1)))
                                        .build()).toList())
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetRes> getDaily(LocalDate localDate, Authentication authentication) {

        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val predictMap = new LinkedMap<String, Float>();

        menuNameRepository.findAllByUser(user).forEach(
                menuName -> predictMap.put(menuName.getName(), getSumPredictByMenuNameAndLocalDate(menuName, localDate, localDate))
        );

        return new ResponseEntity<>(
                PredictRes.PredictGetRes.builder()
                        .predictData(predictMap)
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetRes> getWeekly(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val predictMap = new LinkedMap<String, Float>();

        menuNameRepository.findAllByUser(user).forEach(
                menuName -> predictMap.put(menuName.getName(), getSumPredictByMenuNameAndLocalDate(menuName, localDate, localDate.plusWeeks(1).minusDays(1)))
        );

        return new ResponseEntity<>(
                PredictRes.PredictGetRes.builder()
                        .predictData(predictMap)
                        .build(), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetRes> getMonthly(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val predictMap = new LinkedMap<String, Float>();

        menuNameRepository.findAllByUser(user).forEach(
                menuName -> predictMap.put(menuName.getName(), getSumPredictByMenuNameAndLocalDate(menuName, localDate, localDate.plusMonths(1).minusDays(1)))
        );

        return new ResponseEntity<>(
                PredictRes.PredictGetRes.builder()
                        .predictData(predictMap)
                        .build(), HttpStatus.OK
        );
    }

    private Map<LocalDate, Float> getPredictByMenuNameAndLocalDate(MenuName menuName, LocalDate startDate, LocalDate endDate) {

        Map<LocalDate, Float> predictMap = new LinkedHashMap<>();

        predictRepository.getPredictDao(menuName, startDate, endDate).forEach(
                predictDao -> predictMap.put(predictDao.getLocalDate(), predictDao.getCount()));

        return predictMap;
    }

    private Float getSumPredictByMenuNameAndLocalDate(MenuName menuName, LocalDate startDate, LocalDate endDate) {

        return predictRepository.getPredictSum(menuName, startDate, endDate);
    }
}
