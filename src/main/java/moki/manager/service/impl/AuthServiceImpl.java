package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {



    @Override
    public ResponseEntity<AuthLoginRes> postLogin(AuthLoginReq authLoginReq) {
        return null;
    }
}
