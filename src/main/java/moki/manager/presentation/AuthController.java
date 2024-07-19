package moki.manager.presentation;

import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<AuthLoginRes> postLogin(@RequestBody AuthLoginReq authLoginReq){
        return authService.postLogin(authLoginReq);
    }
}
