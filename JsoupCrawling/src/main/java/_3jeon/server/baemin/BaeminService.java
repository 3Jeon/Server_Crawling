package _3jeon.server.baemin;

import _3jeon.server.baemin.component.BaeminJsoup;
import _3jeon.server.baemin.model.BRestaurant;
import _3jeon.server.baemin.model.BRestaurantInfo;
import _3jeon.server.config.BaseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static _3jeon.server.config.BaseResponseStatus.RESPONSE_ERROR;

@Service
public class BaeminService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BaeminJsoup baeminJsoup;

    public BaeminService(BaeminJsoup baeminJsoup){
        this.baeminJsoup = baeminJsoup;
    }

    public List<BRestaurant> getBRestaurant(String category, double lat, double lng) throws BaseException {
        try{
            List<BRestaurant> BRestaurantList = baeminJsoup.getBRestaurant(category, lat, lng);

            return BRestaurantList;
        } catch(Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }

    public BRestaurantInfo getBRestaurantInfo(long id, double lat, double lng) throws BaseException {
        try{
            BRestaurantInfo bRestaurantInfo = baeminJsoup.getBRestaurantInfo(id, lat, lng);

            return bRestaurantInfo;
        } catch (Exception exception){
            throw new BaseException(RESPONSE_ERROR);
        }
    }
//
//    public List<YRestaurant> getYSearchRestaurants(double lat, double lng, int items, int page, String search) throws BaseException{
//        try{
//            List<YRestaurant> YRestaurantList = yogiyoJsoup.getYSearchRestaurants(lat, lng, items, page, search);
//
//            return YRestaurantList;
//        } catch(Exception exception){
//            throw new BaseException(RESPONSE_ERROR);
//        }
//    }
}
