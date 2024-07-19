package moki.manager.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface SaleService {
    ResponseEntity<HttpStatus> postUpload(Integer year, Integer month, MultipartFile multipartFile, Authentication authentication);

    ResponseEntity<HttpStatus> deleteUpload(Integer year, Integer month, Authentication authentication);
}
