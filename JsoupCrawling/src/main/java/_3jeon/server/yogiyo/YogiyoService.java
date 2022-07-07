package _3jeon.server.yogiyo;

import _3jeon.server.config.BaseException;
import _3jeon.server.yogiyo.component.YogiyoJsoup;
import _3jeon.server.yogiyo.module.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static _3jeon.server.config.BaseResponseStatus.*;

@Service
public class YogiyoService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final YogiyoJsoup yogiyoJsoup;

    public YogiyoService(YogiyoJsoup yogiyoJsoup){
        this.yogiyoJsoup = yogiyoJsoup;
    }

    public List<Restaurant> getRestaurant() throws BaseException {
        try{
            List<Restaurant> restaurantList = yogiyoJsoup.getRestaurantList();
            return restaurantList;
        } catch(Exception exception){
            throw new BaseException(REQUEST_ERROR);
        }
    }
}
