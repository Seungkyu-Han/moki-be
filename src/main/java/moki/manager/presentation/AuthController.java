package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.auth.AuthReq;
import moki.manager.model.dto.auth.AuthReq.PatchManager;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "사용자의 아이디와 비밀번호를 입력")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(schema = @Schema(implementation = AuthLoginRes.class))),
            @ApiResponse(responseCode = "401", description = "ID가 틀림",
                    content = @Content(schema = @Schema(implementation = HttpStatus.class))),
            @ApiResponse(responseCode = "403", description = "비밀번호가 틀림",
                    content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    })
    public ResponseEntity<AuthLoginRes> postLogin(@RequestBody AuthLoginReq authLoginReq){
        return authService.postLogin(authLoginReq);
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "아이디, 비밀번호 가맹점을 입력하여 계정을 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공",
                    content = @Content(schema = @Schema(implementation = HttpStatus.class))),
            @ApiResponse(responseCode = "409", description = "해당 ID가 존재",
                    content = @Content(schema = @Schema(implementation = HttpStatus.class))),
    })
    public ResponseEntity<HttpStatus> postRegister(@RequestBody AuthReq.AuthRegisterReq authRegisterReq){
        return authService.postRegister(authRegisterReq);
    }

    @PatchMapping("/manager")
    @Operation(summary = "유저 정보 변경 API", description = "관리자가 회원 정보를 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class))),
            @ApiResponse(responseCode = "404", description = "해당 유저 없음", content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    })
    public ResponseEntity<HttpStatus> patch(@RequestBody AuthReq.PatchManager patchManager){
        return authService.patchManager(patchManager);
    }

    @PatchMapping
    @Operation(summary = "본인의 정보를 변경")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "변경 성공")
    })
    public ResponseEntity<HttpStatus> patch(
            @RequestBody AuthReq.PatchReq patchManager,
            @Parameter(hidden = true) Authentication authentication){
        return authService.patch(patchManager, authentication);
    }

    @GetMapping("/list")
    @Operation(summary = "전체 사용자를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = AuthRes.class))),
    })
    public ResponseEntity<List<AuthRes.GetUser>> getUserList(){
        return authService.getUserList();
    }

    @DeleteMapping
    @Operation(summary = "유저 삭제", description = "관리자가 유저를 삭제함")
    @Parameters({
            @Parameter(name = "id", description = "삭제할 유저의 아이디")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class))),
    })
    public ResponseEntity<HttpStatus> delete(
            @RequestParam String id
    ){
        return authService.delete(id);
    }
}
