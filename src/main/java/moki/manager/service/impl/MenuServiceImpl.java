package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import moki.manager.model.dto.menu.MenuReq;
import moki.manager.model.dto.menu.MenuRes;
import moki.manager.model.entity.MenuDay;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.MenuSale;
import moki.manager.model.entity.User;
import moki.manager.repository.MenuDayRepository;
import moki.manager.repository.MenuNameRepository;
import moki.manager.repository.MenuSaleRepository;
import moki.manager.service.MenuService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuNameRepository menuNameRepository;
    private final MenuDayRepository menuDayRepository;
    private final MenuSaleRepository menuSaleRepository;

    @Value("${resource.path}")
    private String filePath;

    @Override
    public ResponseEntity<HttpStatus> putNewMenu(
            String name,
            Integer price,
            MultipartFile multipartFile,
            Authentication authentication) throws IOException {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        if (!menuNameRepository.existsByNameAndUser(name, user)){

            val menuName = MenuName.builder()
                    .name(name)
                    .price(price)
                    .user(user)
                    .build();

            if (multipartFile != null){
                val path = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

                Files.copy(multipartFile.getInputStream(), Paths.get(filePath + path));

                menuName.setImg("/img/" + path);
            }

            menuNameRepository.save(menuName);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }
        else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<List<MenuRes.MenuResElement>> getList(Authentication authentication) {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();


        return new ResponseEntity<>(
                menuNameRepository.findAllByUser(user).stream().map(
                        menuName -> MenuRes.MenuResElement.builder()
                                .name(menuName.getName())
                                .price(menuName.getPrice())
                                .img(menuName.getImg())
                                .build()
                ).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> postSale(MenuReq.PostSaleReq postSaleReq, Authentication authentication) {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        var optionalMenuDay = menuDayRepository.findByUserAndLocalDate(user, postSaleReq.getLocalDate());

        val menuDay = optionalMenuDay.orElseGet(() -> menuDayRepository.save(MenuDay.builder()
                .user(User.builder().id(Integer.valueOf(authentication.getName())).build())
                .localDate(postSaleReq.getLocalDate())
                .build()));

        postSaleReq.getMenuAndSaleMap().forEach((key, value) -> {
            val menu = menuNameRepository.findByNameAndUser(key, user);
            if (menu.isPresent()) {

                val menuSale = MenuSale.builder()
                        .count(value)
                        .menuName(menu.get())
                        .menuDay(menuDay)
                        .build();

                menuSaleRepository.deleteByMenuDayAndMenuName(menuDay, menu.get());
                menuSaleRepository.save(menuSale);
            }
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> patch(String menu, Integer price, Boolean isFile, MultipartFile multipartFile, Authentication authentication) throws IOException {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        val optionalMenuName = menuNameRepository.findByNameAndUser(menu, user);

        if (optionalMenuName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        val menuName = optionalMenuName.get();

        menuName.setPrice(price);

        if (isFile){
            val path = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

            Files.copy(multipartFile.getInputStream(), Paths.get(filePath + path));

            menuName.setImg("/img/" + path);
        }

        menuNameRepository.save(menuName);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(String menu, Authentication authentication) {

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        val menuName = menuNameRepository.findByNameAndUser(menu, user);

        if (menuName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println(menuName.get());

        menuNameRepository.delete(menuName.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> postRandom(MenuReq.RandomReq randomReq, Authentication authentication) {

        LocalDate localDate = (randomReq.getLocalDate() != null)? randomReq.getLocalDate() : LocalDate.now();

        val user = User.builder().id(Integer.valueOf(authentication.getName())).build();

        Optional<MenuDay> lastMenuDay = menuDayRepository.findTopByUserOrderByIdDesc(user);

        LocalDate startDate = lastMenuDay.map(day -> day.getLocalDate().plusDays(1)).orElseGet(() -> LocalDate.of(2024, 4, 1));

        val menuNameList = menuNameRepository.findAllByUser(user);

        Random random = new Random(System.currentTimeMillis());

        for (LocalDate today = startDate; localDate.isAfter(today) || localDate.isEqual(today); today = today.plusDays(1)){
            val menuDay = menuDayRepository.save(MenuDay.builder()
                    .localDate(today)
                    .user(user)
                    .build());
            menuNameList.forEach(
                    menuName -> {
                        menuSaleRepository.save(
                                MenuSale.builder()
                                        .menuName(menuName)
                                        .menuDay(menuDay)
                                        .count(100 + random.nextInt(101))
                                        .build()
                        );
                    }
            );
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
