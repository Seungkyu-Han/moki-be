package moki.manager.model.dto.menu;

import lombok.Builder;
import lombok.Data;

public class MenuRes {

    @Data
    @Builder
    public static class MenuResElement{

     private String name;

     private String img;

     private Integer price;

     private Integer minCount;

     private Integer maxCount;
    }
}
