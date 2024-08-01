package moki.manager.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class AuthReq {

    @Data
    public static class AuthLoginReq{
        @Schema(description = "아이디", example = "test")
        private String id;

        @Schema(description = "비밀번호", example = "test")
        private String password;
    }

    @Data
    public static class AuthRegisterReq{
        @Schema(description = "아이디")
        private String id;

        @Schema(description = "가게 이름")
        private String name;

        @Schema(description = "비밀번호")
        private String password;
    }

    @Data
    public static class PatchManager {
        @Schema(description = "아이디")
        private String id;

        @Schema(description = "가게 이름")
        private String name;

        @Schema(description = "새로운 비밀번호")
        private String newPassword;
    }

    @Data
    public static class PatchReq{

        @Schema(description = "가게 이름")
        private String name;

    }
}
