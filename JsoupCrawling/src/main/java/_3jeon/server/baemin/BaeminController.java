package _3jeon.server.baemin;

import _3jeon.server.User.model.User;
import _3jeon.server.baemin.model.*;
import _3jeon.server.config.BaseException;
import _3jeon.server.config.BaseResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/baemin")
public class BaeminController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BaeminService baeminService;

    public BaeminController(BaeminService baeminService){
        this.baeminService = baeminService;
    }

    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<BRestaurant>> getBRestaurant(
            @RequestParam(required = false, defaultValue = "all") String category,
            @RequestParam double lat,
            @RequestParam double lng
    ) {
        try{
            List<BRestaurant> BRestaurantList = baeminService.getBRestaurant(category, lat, lng);

            return new BaseResponse<>(BRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{restaurant-id}/menu")
    public BaseResponse<BRestaurantInfo> getYMenus(@PathVariable(value = "restaurant-id") int restaurantId){
        try{
            // Test Data
            User user= new User();
            user.setLat(37.54766676253973);
            user.setLng(127.0609096938018);

            BRestaurantInfo bStoreInfo = baeminService.getBRestaurantInfo(restaurantId, user.getLat(), user.getLng());

            return new BaseResponse<>(bStoreInfo);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
//
//    @ResponseBody
//    @GetMapping("/search-restaurants")
//    public BaseResponse<List<YRestaurant>> getYSearchRestaurants(
//            @RequestParam(required = false, defaultValue = "60") int items,
//            @RequestParam double lat,
//            @RequestParam double lng,
//            @RequestParam(required = false, defaultValue = "0") int page,
//            @RequestParam String search
//    ) {
//        try{
//            List<YRestaurant> YRestaurantList = baeminService.getYSearchRestaurants(lat, lng, items, page, search);
//
//            return new BaseResponse<>(YRestaurantList);
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}
