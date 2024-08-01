package moki.manager.model.dto.predict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import moki.manager.model.entity.PredictSale;
import org.apache.commons.collections4.map.LinkedMap;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictRes {

    @Data
    public static class PredictGetResElement {

        @Schema(description = "메뉴 이름", example = "아메리카노")
        private String name;

        @Schema(description = "날짜, 예측량")
        private Map<LocalDate, Float> predictData;

        public PredictGetResElement(List<PredictSale> predictSaleList) {
            this.name = predictSaleList.get(0).getMenuName().getName();
            Map<LocalDate, Float> predictData = new LinkedMap<>();
            predictSaleList.forEach(predictSale -> predictData.put(predictSale.getLocalDate(), predictSale.getCount()));
            this.predictData = predictData;
        }
    }

    @Data
    @Builder
    public static class PredictGetDetailRes {

        private List<PredictGetResElement> predicts;
    }

    @Data
    @AllArgsConstructor
    public static class PredictGetRes {
        @Schema(description = "메뉴명, 예측량")
        private Map<String, Float> predictData;
    }
}
