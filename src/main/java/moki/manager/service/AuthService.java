package moki.manager.service;

import moki.manager.model.dto.auth.AuthReq;
import moki.manager.model.dto.auth.AuthReq.PatchManager;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AuthService {
    ResponseEntity<AuthLoginRes> postLogin(AuthLoginReq authLoginReq);

    ResponseEntity<HttpStatus> postRegister(AuthReq.AuthRegisterReq authRegisterReq);

    ResponseEntity<HttpStatus> patchManager(PatchManager patchManager);

    ResponseEntity<List<AuthRes.GetUser>> getUserList();

    ResponseEntity<HttpStatus> delete(String id);

    ResponseEntity<HttpStatus> patch(AuthReq.PatchReq patchReq, Authentication authentication);

    ResponseEntity<HttpStatus> deleteInit(Authentication authentication);
}
