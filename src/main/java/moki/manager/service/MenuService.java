package moki.manager.service;

import moki.manager.model.dto.menu.MenuReq;
import moki.manager.model.dto.menu.MenuRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    ResponseEntity<HttpStatus> putNewMenu(String name, Integer price, MultipartFile multipartFile, Authentication authentication) throws IOException;

    ResponseEntity<List<MenuRes.MenuResElement>> getList(Authentication authentication);

    ResponseEntity<HttpStatus> postSale(MenuReq.PostSaleReq postSaleReq, Authentication authentication);

    ResponseEntity<HttpStatus> patch(String menu, Integer price, Boolean isFile, MultipartFile multipartFile, Authentication authentication) throws IOException;

    ResponseEntity<HttpStatus> delete(String menu, Authentication authentication);

    ResponseEntity<HttpStatus> postRandom(MenuReq.RandomReq randomReq, Authentication authentication);
}
