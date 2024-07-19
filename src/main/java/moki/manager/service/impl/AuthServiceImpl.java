package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import moki.manager.config.jwt.JwtTokenProvider;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.repository.UserRepository;
import moki.manager.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<AuthLoginRes> postLogin(AuthLoginReq authLoginReq) {

        val optionalUser = userRepository.findByLoginId(authLoginReq.getId());

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (isValidPassword(authLoginReq.getPassword(), optionalUser.get().getPassword())) {
            return new ResponseEntity<>(
                    AuthLoginRes.builder()
                            .token(jwtTokenProvider.createAccessToken(optionalUser.get().getId()))
                            .build()
                    ,
                    HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private Boolean isValidPassword(String password, String encryptedPassword){
        return password.equals(encryptedPassword);
    }
}
