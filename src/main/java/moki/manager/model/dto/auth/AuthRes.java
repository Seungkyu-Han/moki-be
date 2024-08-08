package moki.manager.model.dto.auth;

import lombok.Builder;
import lombok.Data;

public class AuthRes {

    @Data
    @Builder
    public static class AuthLoginRes{
        private String token;

        private String name;
    }

    @Data
    @Builder
    public static class GetUser{
        private String name;
        private String id;
        private String password;

    }
}
