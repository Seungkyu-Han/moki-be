package moki.manager.model.dto.sale;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(description = "최고 매출 상품")
        private SaleGetRes best;

        @Schema(description = "최저 매출 상품")
        private SaleGetRes worst;
    }

    @Data
    @Builder
    public static class SaleGetRes {

        @Schema(description = "이름")
        private String name;

        @Schema(description = "판매량")
        private Long count;

        @Schema(description = "가격")
        private Integer price;

        private String image;
    }
}
