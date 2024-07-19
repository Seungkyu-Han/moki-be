package moki.manager.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class AuthReq {

    @Data

    public static class AuthLoginReq{
        @Schema(description = "아이디")
        private String id;

        @Schema(description = "비밀번호")
        private String password;
    }
}
