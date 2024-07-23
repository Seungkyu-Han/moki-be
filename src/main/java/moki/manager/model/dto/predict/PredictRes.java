package moki.manager.model.dto.predict;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PredictRes {

    @Data
    @Builder
    public static class PredictGetResElement {

        private String name;

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
        private Map<String, Float> predictData;
    }
}
