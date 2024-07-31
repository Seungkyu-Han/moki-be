package moki.manager.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "새로운 메뉴 등록 API")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "등록 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class))),
    })
    public ResponseEntity<HttpStatus> postNewMenu(
            @ModelAttribute MenuReq.PostNewMenuReqList postNewMenuReqList,
            @Parameter(hidden = true) Authentication authentication) throws IOException {

        return menuService.putNewMenu(postNewMenuReqList, authentication);
    }

    @GetMapping("/list")
    @Operation(summary = "전체 메뉴 조회 API", description = "해당 사용자의 전체 메뉴를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = MenuRes.MenuResElement.class))),
    })
    public ResponseEntity<List<MenuRes.MenuResElement>> getList(@Parameter(hidden = true) Authentication authentication){
        return menuService.getList(authentication);
    }

    @PostMapping("/sale")
    @Operation(summary = "오늘의 판매 등록 API", description = "오늘의 판매량을 등록")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    )
    public ResponseEntity<HttpStatus> postSale(@RequestBody MenuReq.PostSaleReq postSaleReq, @Parameter(hidden = true) Authentication authentication){
        return menuService.postSale(postSaleReq, authentication);
    }

    @PostMapping("/random")
    @Operation(summary = "해당 날짜까지 랜덤으로 판매를 등록")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    )
    @Parameters(
    )
    public ResponseEntity<HttpStatus> postRandom(
            @RequestBody MenuReq.RandomReq randomReq,
            @Parameter(hidden = true) Authentication authentication){
        return menuService.postRandom(randomReq, authentication);
    }


    @PatchMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "메뉴를 수정하는 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "등록 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    )
    public ResponseEntity<HttpStatus> patch(
            @RequestParam @Parameter(description = "메뉴 이름") String menu,
            @RequestParam @Parameter(description = "판매 가격") Integer price,
            @RequestParam @Parameter(description = "사진 변경 유무") Boolean isFile,
            @RequestPart @Parameter(description = "변경할 사진") MultipartFile multipartFile,
            @Parameter(hidden = true) Authentication authentication) throws IOException {
        return menuService.patch(menu, price, isFile, multipartFile, authentication);
    }

    @DeleteMapping
    @Operation(summary = "메뉴를 삭제하는 API")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "삭제 성공", content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    )
    public ResponseEntity<HttpStatus> delete(
            @RequestParam @Parameter(description = "삭제하려는 메뉴의 이름") String menu,
            @Parameter(hidden = true) Authentication authentication){
        return menuService.delete(menu, authentication);
    }
}
