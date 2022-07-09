package _3jeon.server.yogiyo;

import _3jeon.server.config.BaseException;
import _3jeon.server.config.BaseResponse;
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
    public BaseResponse<List<YRestaurant>> getRestaurant(
            @RequestParam(required = false) String category,
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        if (category == null)
            category = "all";

        try{
            List<YRestaurant> YRestaurantList = yogiyoService.getRestaurant(category, lat, lon);

            return new BaseResponse<>(YRestaurantList);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
