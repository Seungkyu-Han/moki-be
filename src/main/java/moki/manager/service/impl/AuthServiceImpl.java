package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import moki.manager.config.jwt.JwtTokenProvider;
import moki.manager.model.dto.auth.AuthReq.AuthChangePasswordReq;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.model.entity.User;
import moki.manager.repository.UserRepository;
import moki.manager.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<HttpStatus> postChangePassword(AuthChangePasswordReq authChangePasswordReq) {

        val optionalUser = userRepository.findByLoginId(authChangePasswordReq.getId());

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            optionalUser.get().setPassword(bCryptPasswordEncoder.encode(authChangePasswordReq.getNewPassword()));
            userRepository.save(optionalUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

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

    @Override
    public ResponseEntity<HttpStatus> postRegister(AuthLoginReq authLoginReq) {
        try{
            val user = User.builder()
                    .loginId(authLoginReq.getId())
                    .password(bCryptPasswordEncoder.encode(authLoginReq.getPassword()))
                    .build();
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(DataIntegrityViolationException exception){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    private Boolean isValidPassword(String password, String encryptedPassword){
        return bCryptPasswordEncoder.matches(password, encryptedPassword);
    }
}
