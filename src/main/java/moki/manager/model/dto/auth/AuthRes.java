package moki.manager.model.dto.auth;

import lombok.Data;

public class AuthRes {

    @Data
    public static class AuthLoginRes{
        private String token;
    }
}
