package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import moki.manager.model.dto.menu.MenuReq;
import moki.manager.model.dto.menu.MenuRes;
import moki.manager.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(name = "/new-menu", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<HttpStatus> postNewMenu(
            @RequestParam @Parameter(description = "메뉴명") String name,
            @RequestParam @Parameter(description = "가격") Integer price,
            @RequestPart @Parameter(description = "해당 메뉴의 사진") MultipartFile multipartFile,
            @Parameter(hidden = true) Authentication authentication) throws IOException {
        return menuService.putNewMenu(name, price, multipartFile, authentication);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MenuRes.MenuResElement>> getList(@Parameter(hidden = true) Authentication authentication){
        return menuService.getList(authentication);
    }

    @PostMapping("/sale")
    public ResponseEntity<HttpStatus> postSale(@RequestBody MenuReq.PostSaleReq postSaleReq, @Parameter(hidden = true) Authentication authentication){
        return menuService.postSale(postSaleReq, authentication);
    }

    @PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(description = "메뉴를 수정하는 API")
    public ResponseEntity<HttpStatus> patch(
            @RequestParam String menu,
            @RequestParam Integer price,
            @RequestParam Boolean isFile,
            @RequestPart MultipartFile multipartFile,
            @Parameter(hidden = true) Authentication authentication) throws IOException {
        return menuService.patch(menu, price, isFile, multipartFile, authentication);
    }

    @DeleteMapping
    @Operation(description = "메뉴를 삭제하는 API")
    public ResponseEntity<HttpStatus> delete(
            @RequestParam @Parameter(description = "삭제하려는 메뉴의 이름") String menu,
            @Parameter(hidden = true) Authentication authentication){
        return menuService.delete(menu, authentication);
    }
}
