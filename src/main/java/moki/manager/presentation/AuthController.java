package moki.manager.presentation;

import lombok.RequiredArgsConstructor;
import moki.manager.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
}
