package moki.manager.model.dto.date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

public class DateRes {

    @Data
    @Builder
    public static class DateDailyRes{
        @Schema(description = "오늘 매출")
        private Integer today;
        @Schema(description = "어제 매출")
        private Integer yesterday;
    }

    @Data
    @Builder
    public static class DateMonthlyRes{
        @Schema(description = "이번달 매출")
        private Integer thisMonth;
        @Schema(description = "저번달 매출")
        private Integer lastMonth;
    }

    @Data
    @Builder
    public static class DateWeeklyRes{
        @Schema(description = "이번주 매출")
        private Integer thisWeek;
        @Schema(description = "저번주 매출")
        private Integer lastWeek;
    }
}
