package moki.manager.model.dto.sale;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class SaleRes {

    @Data
    @Builder
    public static class SaleGetDailyDetailRes {

        private List<SaleGetRes> saleGetResList;
    }

    @Data
    @Builder
    public static class SaleGetRankRes{
        private SaleGetRes best;

        private SaleGetRes worst;
    }

    @Data
    @Builder
    public static class SaleGetRes {

        private String name;

        private Long count;

        private Integer price;
    }
}