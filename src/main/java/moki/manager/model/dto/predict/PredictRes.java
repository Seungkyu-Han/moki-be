package moki.manager.model.dto.predict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PredictRes {

    @Data
    @Builder
    public static class PredictGetResElement {

        @Schema(description = "메뉴 이름", example = "아메리카노")
        private String name;

        @Schema(description = "날짜, 예측량")
        private Map<LocalDate, Float> predictData;
    }

    @Data
    @Builder
    public static class PredictGetDetailRes {

        private List<PredictGetResElement> predicts;
    }

    @Data
    @Builder
    public static class PredictGetRes {
        @Schema(description = "메뉴명, 예측량")
        private Map<String, Float> predictData;
    }
}
