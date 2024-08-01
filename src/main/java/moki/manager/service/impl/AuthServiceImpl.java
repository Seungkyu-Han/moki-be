package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import moki.manager.config.jwt.JwtTokenProvider;
import moki.manager.model.dto.auth.AuthReq;
import moki.manager.model.dto.auth.AuthReq.PatchManager;
import moki.manager.model.dto.auth.AuthReq.AuthLoginReq;
import moki.manager.model.dto.auth.AuthRes;
import moki.manager.model.dto.auth.AuthRes.AuthLoginRes;
import moki.manager.model.entity.User;
import moki.manager.repository.MenuNameRepository;
import moki.manager.repository.UserRepository;
import moki.manager.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final MenuNameRepository menuNameRepository;

    @Override
    public ResponseEntity<HttpStatus> patchManager(PatchManager patchManager) {

        val optionalUser = userRepository.findByLoginId(patchManager.getId());


        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{

            val user = optionalUser.get();

            if(patchManager.getName() != null)
                user.setName(patchManager.getName());

            if(patchManager.getNewPassword() != null)
                user.setPassword(bCryptPasswordEncoder.encode(patchManager.getNewPassword()));

            userRepository.save(optionalUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> patch(AuthReq.PatchReq patchReq, Authentication authentication) {

        val optionalUser = userRepository.findById(Integer.valueOf(authentication.getName()));

        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            val user = optionalUser.get();

            if(patchReq.getName() != null)
                user.setName(patchReq.getName());

            userRepository.save(optionalUser.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteInit(Authentication authentication) {

        val user = userRepository.findById(Integer.valueOf(authentication.getName())).orElseThrow();

        user.setName(null);

        userRepository.save(user);

        menuNameRepository.deleteByUser(user);


        return new ResponseEntity<>(HttpStatus.OK);
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
                            .build(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> postRegister(AuthReq.AuthRegisterReq authRegisterReq) {
        try{
            val user = User.builder()
                    .loginId(authRegisterReq.getId())
                    .name(authRegisterReq.getName())
                    .password(bCryptPasswordEncoder.encode(authRegisterReq.getPassword()))
                    .build();
            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(DataIntegrityViolationException exception){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<List<AuthRes.GetUser>> getUserList() {

        return new ResponseEntity<>(
                userRepository.findAll().stream().map(
                        user -> AuthRes.GetUser.builder()
                                .name(user.getName())
                                .id(user.getLoginId())
                                .password(user.getPassword())
                                .build()
                ).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(String id) {

        userRepository.deleteByLoginId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Boolean isValidPassword(String password, String encryptedPassword){
        return bCryptPasswordEncoder.matches(password, encryptedPassword);
    }
}
