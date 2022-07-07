package _3jeon.server.yogiyo;

import _3jeon.server.config.BaseException;
import _3jeon.server.config.BaseResponse;
import _3jeon.server.yogiyo.module.Restaurant;
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
    @GetMapping("/all")
    public BaseResponse<List<Restaurant>> getRestaurant() {
        try{
            List<Restaurant> restaurantList = yogiyoService.getRestaurant();
            return new BaseResponse<>(restaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
