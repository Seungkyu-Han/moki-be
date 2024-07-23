package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.auth.AuthReq.AuthChangePasswordReq;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "ID가 틀림"),
            @ApiResponse(responseCode = "403", description = "비밀번호가 틀림")
    })
    public ResponseEntity<AuthLoginRes> postLogin(@RequestBody AuthLoginReq authLoginReq){
        return authService.postLogin(authLoginReq);
    }

    @PostMapping("/register")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "해당 ID가 존재")
    })
    public ResponseEntity<HttpStatus> postRegister(@RequestBody AuthLoginReq authLoginReq){
        return authService.postRegister(authLoginReq);
    }

    @PatchMapping("/change-password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    public ResponseEntity<HttpStatus> postChangePassword(@RequestBody AuthChangePasswordReq authChangePasswordReq){
        return authService.postChangePassword(authChangePasswordReq);
    }

    @GetMapping("/list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<List<AuthRes.GetUser>> getUserList(){
        return authService.getUserList();
    }

    @DeleteMapping()
    @Parameters({
            @Parameter(name = "id", description = "삭제할 유저의 아이디")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<List<AuthRes.GetUser>> delete(
            @RequestParam String id
    ){
        return authService.delete(id);
    }
}
