package com.example.review.api;

import com.example.review.api.request.CreateAndEditRestaurantRequest;
import com.example.review.model.RestaurantEntity;
import com.example.review.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RestaurantApi {

    private final RestaurantService restaurantService;

    //맛집 리스트 조회
    @GetMapping("/restaurants")
    public String getRestaurants() {
        return "This is getRestaurants";
    }

    //맛집 정보 조회
    @GetMapping("/restaurant/{restaurantId}")
    public String getRestaurant(
            @PathVariable Long restaurantId
    ){
        return "This is getRestaurants," + restaurantId;
    }

    //맛집 생성
    @PostMapping("/restaurant")
    public RestaurantEntity createRestaurant(
            @RequestBody CreateAndEditRestaurantRequest request
    ) {
        return restaurantService.createRestaurant(request);
    }

    //맛집 수정
    @PutMapping("/restaurant/{restaurantId}")
    public String editRestaurant(
            @PathVariable Long restaurantId,
             @RequestBody CreateAndEditRestaurantRequest request
    ){
        return "This is editRestaurants," + restaurantId + "name= " + request.getName() + " address= " + request.getAddress();
    }

    //맛집 삭제
    @DeleteMapping("/restaurant/{restaurantId}")
    public String deleteRestaurant(
            @PathVariable Long restaurantId
    ){
        return "This is deleteRestaurants," + restaurantId;
    }

}
