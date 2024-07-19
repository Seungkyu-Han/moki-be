package moki.manager.service;

import moki.manager.model.dto.auth.AuthReq.AuthChangePasswordReq;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthLoginRes> postLogin(AuthLoginReq authLoginReq);

    ResponseEntity<HttpStatus> postRegister(AuthLoginReq authLoginReq);

    ResponseEntity<HttpStatus> postChangePassword(AuthChangePasswordReq authChangePasswordReq);
}
