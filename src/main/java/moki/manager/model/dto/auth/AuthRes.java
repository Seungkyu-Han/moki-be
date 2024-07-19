package moki.manager.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class AuthRes {

    @Data
    @Builder
    public static class AuthLoginRes{
        private String token;
    }
}
