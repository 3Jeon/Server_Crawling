package _3jeon.server.yogiyo;

import _3jeon.server.config.BaseException;
import _3jeon.server.config.BaseResponse;
import _3jeon.server.yogiyo.model.YMenuGroup;
import _3jeon.server.yogiyo.model.YRestaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/yogiyo")
public class YogiyoController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final YogiyoService yogiyoService;

    public YogiyoController(YogiyoService yogiyoService){
        this.yogiyoService = yogiyoService;
    }

    @ResponseBody
    @GetMapping("/restaurant")
    public BaseResponse<List<YRestaurant>> getYRestaurant(
            @RequestParam(required = false, defaultValue = "all") String category,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "50") int items
    ) {
        try{
            List<YRestaurant> YRestaurantList = yogiyoService.getYRestaurant(category, lat, lng, page, items);

            return new BaseResponse<>(YRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/{restaurant-id}/menu")
    public BaseResponse<List<YMenuGroup>> getYMenus(@PathVariable(value = "restaurant-id") long restaurantId){
        try{
            List<YMenuGroup> yMenuList = yogiyoService.getYMenuList(restaurantId);

            return new BaseResponse<>(yMenuList);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/search-restaurants")
    public BaseResponse<List<YRestaurant>> getYSearchRestaurants(
            @RequestParam(required = false, defaultValue = "60") int items,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam String search
    ) {
        try{
            List<YRestaurant> YRestaurantList = yogiyoService.getYSearchRestaurants(lat, lng, items, page, search);

            return new BaseResponse<>(YRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
