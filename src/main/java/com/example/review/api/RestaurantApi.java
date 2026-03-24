package com.example.review.api;

import com.example.review.api.request.CreateAndEditRestaurantRequest;
import com.example.review.api.response.RestaurantDetailView;
import com.example.review.api.response.RestaurantView;
import com.example.review.model.RestaurantEntity;
import com.example.review.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantApi {

    private final RestaurantService restaurantService;

    //맛집 리스트 조회
    @GetMapping("/restaurants")
    public List<RestaurantView> getRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    //맛집 정보 조회
    @GetMapping("/restaurant/{restaurantId}")
    public RestaurantDetailView getRestaurant(
            @PathVariable Long restaurantId
    ){
        return restaurantService.getRestaurantDetail(restaurantId);
    }

    //맛집 생성 *
    @PostMapping("/restaurant")
    public void createRestaurant(

            //이름, 주소, 메뉴<이름, 가격>을 매개변수로 받아
            @RequestBody CreateAndEditRestaurantRequest request
    ) {
        restaurantService.createRestaurant(request);
    }

    //맛집 수정
    @PutMapping("/restaurant/{restaurantId}")
    public void editRestaurant(
            //수정할 맛집 Id, 수정할 (이름, 주소, 메뉴<이름, 가격>) 매개변수로 받아
            @PathVariable Long restaurantId,
            @RequestBody CreateAndEditRestaurantRequest request
    ){
        restaurantService.editRestaurant(restaurantId, request);
    }

    //맛집 삭제
    @DeleteMapping("/restaurant/{restaurantId}")
    public void deleteRestaurant(
            //삭제할 restaurantId 매개변수로 받아
            @PathVariable Long restaurantId
    ){
        restaurantService.deleteRestaurant(restaurantId);
    }

}
