package _3jeon.server.yogiyo;

import _3jeon.server.config.BaseException;
import _3jeon.server.yogiyo.component.YogiyoJsoup;
import _3jeon.server.yogiyo.model.YRestaurant;
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

    public List<YRestaurant> getRestaurant(String category, double lat, double lon) throws BaseException {
        try{
            List<YRestaurant> YRestaurantList = yogiyoJsoup.getYRestaurantList(category, lat, lon);
            return YRestaurantList;
        } catch(Exception exception){
            throw new BaseException(REQUEST_ERROR);
        }
    }
}
