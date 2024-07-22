package moki.manager.model.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

public class MenuReq {

    @Data
    public static class PostNewMenuReq{
        private String menuName;
        private Integer price;
    }

    @Data
    public static class PostSaleReq {
        private Map<String, Integer> menuAndSaleMap;

        private LocalDate localDate;
    }
}
