package com.example.review.service;

import com.example.review.api.request.CreateAndEditRestaurantRequest;
import com.example.review.api.request.CreateAndEditRestaurantRequestMenu;
import com.example.review.api.response.RestaurantDetailView;
import com.example.review.api.response.RestaurantView;
import com.example.review.model.MenuEntity;
import com.example.review.model.RestaurantEntity;
import com.example.review.repository.MenuRepository;
import com.example.review.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public RestaurantEntity createRestaurant( //RestaurantEntity로 반환한다.
                                              //이름, 주소 메뉴<이름, 가격>을 매개변수로 받는다.
                                              CreateAndEditRestaurantRequest request
    ) {
        //restaurant 테이블에서 이름, 주소, 생성일자, 수정일자를 받아 restaurant에 저장
        RestaurantEntity restaurant = RestaurantEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .createdAt(ZonedDateTime.now())
                .updatedAt(ZonedDateTime.now())
                .build();

        // 받아온 정보 restaurantrepo에 저장
        restaurantRepository.save(restaurant);

        List<MenuEntity> menuEntities = request.getMenus().stream()
                .map((menu) -> MenuEntity.builder()
                        .restaurant(restaurant)
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build()
                ).toList();

        menuRepository.saveAll(menuEntities);

        return restaurant;
    }


    //맛집 이름, 주소 / 메뉴 따로 수정할 수 있도록 리팩토링 필요
    @Transactional
    public void editRestaurant(
            // 맛집 Id, 이름, 주소, 메뉴<이름, 가격> 매개변수로 받아
            Long restaurantId,
            CreateAndEditRestaurantRequest request
    ) {
        //restaurantrepo에서 매개변수로 받은 restaurantId를 조회 있으면 조회해서 restaurant에 저장 없으면 "없는 맛집입니다." exception
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> new RuntimeException("없는 맛집입니다."));
        //매개변수로 받은 request에서 이름, 주소를 restaurantrepo에 저장
        restaurant.changeNameAndAddress(request.getName(), request.getAddress());
        restaurantRepository.save(restaurant);

        //menurepo에서 restaurantId로 해당 맛집을 조회하여 menus 리스트에 담아
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);
        //해당 메뉴 menurepo에서 삭제
        menuRepository.deleteAll(menus);

        //메뉴 재등록
        List<MenuEntity> menuEntities = request.getMenus().stream()
                .map((menu) -> MenuEntity.builder()
                        .restaurant(restaurant)
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .createdAt(ZonedDateTime.now())
                        .updatedAt(ZonedDateTime.now())
                        .build()
                ).toList();

        menuRepository.saveAll(menuEntities); // INSERT 1번으로 처리하도록 수정

    }

    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        restaurantRepository.delete(restaurant); //menu가 자동 삭제되므로 중복 삭제가 되지 않도록 수정
    }

    @Transactional(readOnly = true)
    public List<RestaurantView> getAllRestaurants() {
        List<RestaurantEntity> restaurants = restaurantRepository.findAll();

        return restaurants.stream().map((restaurant) -> RestaurantView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .build()
        ).toList();
    }

    @Transactional(readOnly = true)
    public RestaurantDetailView getRestaurantDetail(Long restaurantId) {
        RestaurantEntity restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        List<MenuEntity> menus = menuRepository.findAllByRestaurantId(restaurantId);

        return RestaurantDetailView.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .createdAt(restaurant.getCreatedAt())
                .updatedAt(restaurant.getUpdatedAt())
                .menus(
                        menus.stream().map((menu) -> RestaurantDetailView.Menu.builder()
                                .id(menu.getId())
                                .name(menu.getName())
                                .price(menu.getPrice())
                                .createdAt(menu.getCreatedAt())
                                .updatedAt(menu.getUpdatedAt())
                                .build()
                        ).toList()
                )
                .build();
    }
}