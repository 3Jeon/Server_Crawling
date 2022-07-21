package _3jeon.server.yogiyo;

import _3jeon.server.config.BaseException;
import _3jeon.server.yogiyo.component.YogiyoJsoup;
import _3jeon.server.yogiyo.model.*;
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

    public List<YRestaurant> getYRestaurant(String category, double lat, double lng, int page, int items) throws BaseException {
        try{
            List<YRestaurant> YRestaurantList = yogiyoJsoup.getYRestaurantList(category, lat, lng, page, items);

            return YRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public List<YMenuGroup> getYMenuList(long id) throws BaseException {
        try{
            List<YMenuGroup> YMenuList = yogiyoJsoup.getYMenuList(id);

            return YMenuList;
        } catch (Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public List<YRestaurant> getYSearchRestaurants(double lat, double lng, int items, int page, String search) throws BaseException{
        try{
            List<YRestaurant> YRestaurantList = yogiyoJsoup.getYSearchRestaurants(lat, lng, items, page, search);

            return YRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }
}
