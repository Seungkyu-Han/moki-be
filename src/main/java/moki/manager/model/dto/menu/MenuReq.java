package moki.manager.model.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MenuReq {

    @Data
    public static class PostNewMenuReq{
        @Schema(description = "메뉴 이름")
        private String menuName;
        @Schema(description = "판매 가격")
        private Integer price;

        private MultipartFile image;

        private Integer maxCount;

        private Integer minCount;
    }

    @Data
    public static class PostNewMenuReqList {
        private List<PostNewMenuReq> menuList;
    }

    @Data
    public static class PostSaleReq {
        @Schema(description = "메뉴 이름, 판매 수량")
        private Map<String, Integer> menuAndSaleMap;
        @Schema(description = "등록일")
        private LocalDate localDate;
    }

    @Data
    public static class PatchReq{
        @Schema(description = "변경할 메뉴 이름")
        private String name;
        @Schema(description = "판매 가격")
        private Integer price;
    }

    @Data
    public static class RandomReq{
        private LocalDate startDate;

        private LocalDate endDate;
    }
}
