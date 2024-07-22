package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.menu.MenuReq;
import moki.manager.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/new-menu")
    public ResponseEntity<HttpStatus> postNewMenu(@RequestBody MenuReq.PostNewMenuReq postNewMenuReq, @Parameter(hidden = true) Authentication authentication) {
        return menuService.putNewMenu(postNewMenuReq, authentication);
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> getList(@Parameter(hidden = true) Authentication authentication){
        return menuService.getList(authentication);
    }

    @PostMapping("/sale")
    public ResponseEntity<HttpStatus> postSale(@RequestBody MenuReq.PostSaleReq postSaleReq, @Parameter(hidden = true) Authentication authentication){
        return menuService.postSale(postSaleReq, authentication);
    }


}
