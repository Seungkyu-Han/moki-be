package moki.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import moki.manager.model.dto.menu.MenuReq;
import moki.manager.model.entity.MenuDay;
import moki.manager.model.entity.MenuName;
import moki.manager.model.entity.MenuSale;
import moki.manager.model.entity.User;
import moki.manager.repository.MenuDayRepository;
import moki.manager.repository.MenuNameRepository;
import moki.manager.repository.MenuSaleRepository;
import moki.manager.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuNameRepository menuNameRepository;
    private final MenuDayRepository menuDayRepository;
    private final MenuSaleRepository menuSaleRepository;

    @Override
    public ResponseEntity<HttpStatus> putNewMenu(MenuReq.PostNewMenuReq postNewMenuReq, Authentication authentication) {

        if (!menuNameRepository.existsByName(postNewMenuReq.getMenuName())){
            menuNameRepository.save(
                    MenuName.builder()
                            .user(User.builder().id(Integer.valueOf(authentication.getName())).build())
                            .id(null)
                            .price(postNewMenuReq.getPrice())
                            .name(postNewMenuReq.getMenuName())
                            .build()
            );

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getList(Authentication authentication) {
        return new ResponseEntity<>(menuNameRepository.findAllByUser(
                User.builder().id(Integer.valueOf(authentication.getName())).build()
        ).stream().map(MenuName::getName).toList(), HttpStatus.OK);
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
}
