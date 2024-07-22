package moki.manager.model.dto.date;

import lombok.Builder;
import lombok.Data;

public class DateRes {

    @Data
    @Builder
    public static class DateDailyRes{
        private Integer today;
        private Integer yesterday;
    }

    @Data
    @Builder
    public static class DateMonthlyRes{
        private Integer thisMonth;
        private Integer lastMonth;
    }

    @Data
    @Builder
    public static class DateWeeklyRes{
        private Integer thisWeek;
        private Integer lastWeek;
    }
}
