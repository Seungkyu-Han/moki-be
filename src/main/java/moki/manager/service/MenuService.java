package moki.manager.service;

import moki.manager.model.dto.menu.MenuReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    ResponseEntity<HttpStatus> putNewMenu(MenuReq.PostNewMenuReq postNewMenuReq, Authentication authentication);

    ResponseEntity<List<String>> getList(Authentication authentication);

    ResponseEntity<HttpStatus> postSale(MenuReq.PostSaleReq postSaleReq, Authentication authentication);

    ResponseEntity<HttpStatus> patch(String menu, Integer price, Boolean isFile, MultipartFile multipartFile, Authentication authentication) throws IOException;
}
