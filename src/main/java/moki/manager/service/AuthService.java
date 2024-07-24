package moki.manager.service;

import moki.manager.model.dto.auth.AuthReq;
import moki.manager.model.dto.auth.AuthReq.patchManager;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthService {
    ResponseEntity<AuthLoginRes> postLogin(AuthLoginReq authLoginReq);

    ResponseEntity<HttpStatus> postRegister(AuthReq.AuthRegisterReq authRegisterReq);

    ResponseEntity<HttpStatus> patch(patchManager patchManager);

    ResponseEntity<List<AuthRes.GetUser>> getUserList();

    ResponseEntity<List<AuthRes.GetUser>> delete(String id);
}
