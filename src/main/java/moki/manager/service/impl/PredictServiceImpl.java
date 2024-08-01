package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import moki.manager.model.dto.predict.PredictRes;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.PredictSale;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

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

        return new ResponseEntity<>(getPredictDetail(localDate, localDate, user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetDetailRes> getWeeklyDetail(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val endDate = localDate.plusWeeks(1).minusDays(1);

        return new ResponseEntity<>(getPredictDetail(localDate, endDate, user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetDetailRes> getMonthlyDetail(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val endDate = localDate.plusMonths(1).minusDays(1);

        return new ResponseEntity<>(getPredictDetail(localDate, endDate, user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetRes> getDaily(LocalDate localDate, Authentication authentication) {

        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        return new ResponseEntity<>(
                getPredictTotal(localDate, localDate, user), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetRes> getWeekly(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val endDate = localDate.plusWeeks(1).minusDays(1);

        return new ResponseEntity<>(
                getPredictTotal(localDate, endDate, user), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<PredictRes.PredictGetRes> getMonthly(LocalDate localDate, Authentication authentication) {
        val user = User.builder()
                .id(Integer.valueOf(authentication.getName()))
                .build();

        val endDate = localDate.plusMonths(1).minusDays(1);

        return new ResponseEntity<>(
                getPredictTotal(localDate, endDate, user), HttpStatus.OK
        );
    }

    @Override
    public void callPredictMethod(LocalDate startDate, LocalDate endDate, User user) {
        getPredictTotal(startDate, endDate, user);
    }

    private PredictRes.PredictGetRes getPredictTotal(LocalDate startDate, LocalDate endDate, User user) {

        val predictMap = new LinkedMap<String, Float>();

        val menuNameList = menuNameRepository.findAllByUser(user);

        for(MenuName menuName : menuNameList) {

            AtomicReference<Float> total = new AtomicReference<>(0F);

            getPredictDetailByMenuName(startDate, endDate, menuName).forEach(
                    predictSale -> total.getAndSet(total.get() + predictSale.getCount())
            );

            predictMap.put(
                    menuName.getName(), total.get()
            );
        }

        return new PredictRes.PredictGetRes(predictMap);
    }


    private PredictRes.PredictGetDetailRes getPredictDetail(LocalDate startDate, LocalDate endDate, User user){
        val predictGetResElementList = new ArrayList<PredictRes.PredictGetResElement>();

        val menuNameList = menuNameRepository.findAllByUser(user);

        for (MenuName menuName : menuNameList) {
            predictGetResElementList.add(new PredictRes.PredictGetResElement(getPredictDetailByMenuName(startDate, endDate, menuName)));
        }

        return PredictRes.PredictGetDetailRes.builder()
                .predicts(predictGetResElementList)
                .build();
    }


    private List<PredictSale> getPredictDetailByMenuName(LocalDate startDate, LocalDate endDate, MenuName menuName) {

        Random random = new Random(System.currentTimeMillis());

        if (predictRepository.existsByLocalDateAndMenuName(endDate, menuName)){
            return predictRepository.findByMenuNameAndLocalDateBetween(menuName, startDate, endDate);
        }
        else{
            predictRepository.deleteByMenuNameAndLocalDateBetween(menuName, startDate, endDate);

            val predictSaleList = new ArrayList<PredictSale>();

            for(LocalDate today = startDate; today.isBefore(endDate) || today.isEqual(endDate); today = today.plusDays(1)) {

                val minCount = menuName.getMinCount();
                val maxCount = menuName.getMaxCount();

                val midCount = (minCount + maxCount) / 2;

                val range = (midCount / 10 == 0) ? 1 : midCount / 10;

                val count = midCount - range + random.nextFloat(2 * range);

                predictSaleList.add(
                        PredictSale.builder()
                                .count(count)
                                .localDate(today)
                                .menuName(menuName)
                                .build()
                );

            }

            return predictRepository.saveAll(predictSaleList);
        }

    }
}
