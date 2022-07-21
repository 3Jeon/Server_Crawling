package _3jeon.server.baemin.component;

import _3jeon.server.baemin.model.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BaeminJsoup {

    // 주변 레스토랑 불러오기
    public List<BRestaurant> getBRestaurant(String category, double lat, double lng) {
        String url = "https://shopdp-api.baemin.com/v1/BAEMIN/curations";
        Connection conn = Jsoup.connect(url)
                .header("Host", "shopdp-api.baemin.com")
                .header("User-Agent", "iph1_11.26.1")
                .header("USER-BAEDAL", "baemin")
                .data("displayCategory", category)
                .data("latitude", Double.toString(lat))
                .data("longitude", Double.toString(lng))
                .data("sort", "SORT__DISTANCE")
                .data("distance", "1")
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<BRestaurant> bRestaurants = new ArrayList<>();
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");
            JSONArray jsonArray = (JSONArray) jsonObject.get("shops");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tmp = (JSONObject) jsonArray.get(i);
                // 매장 정보
                JSONObject shopInfo = (JSONObject) tmp.get("shopInfo");
                // 썸네일
                JSONArray thumbnails = (JSONArray) shopInfo.get("thumbnails");
                Iterator<String> iterator = thumbnails.iterator();
                List<String> thumbnailList = new ArrayList<>();
                while(iterator.hasNext())
                    thumbnailList.add(iterator.next().trim());
                // 매장 상태
                JSONObject inOperation = (JSONObject) tmp.get("shopStatus");
                // 매장 점수
                JSONObject shopStatistics = (JSONObject) tmp.get("shopStatistics");
                // 기타 정보
                JSONObject logInfo = (JSONObject) tmp.get("logInfo");
                JSONArray deliveryTips = (JSONArray) logInfo.get("deliveryTips");
                List<Long> deliveryTip = new ArrayList<>();
                Iterator<Long> iterator1 = deliveryTips.iterator();
                while(iterator1.hasNext())
                    deliveryTip.add(iterator1.next());
                JSONArray expectedDeliveryTimes = (JSONArray) logInfo.get("expectedDeliveryTimes");
                List<Long> expectedDeliveryTime = new ArrayList<>();
                Iterator<Long> iterator2 = expectedDeliveryTimes.iterator();
                while(iterator2.hasNext())
                    expectedDeliveryTime.add(iterator2.next());

                BRestaurant BRestaurant = new BRestaurant(
                        Integer.parseInt(String.valueOf(shopInfo.get("shopNumber"))),
                        (String) shopInfo.get("shopName"),
                        lat,
                        lng,
                        thumbnailList,
                        (boolean) inOperation.get("inOperation"),
                        (double) shopStatistics.get("averageStarScore"),
                        deliveryTip,
                        expectedDeliveryTime
                );

                bRestaurants.add(BRestaurant);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return bRestaurants;
    }

    public BRestaurantInfo getBRestaurantInfo(long id, double lat, double lng){
        String url = String.format("https://shopdp-api.baemin.com/v8/shop/%s/detail", String.valueOf(id));
        Connection conn = Jsoup.connect(url)
                .header("Host", "shopdp-api.baemin.com")
                .header("User-Agent", "iph1_11.26.1")
                .data("lat", Double.toString(lat))
                .data("lng", Double.toString(lng))
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        BRestaurantInfo bRestaurantInfo = new BRestaurantInfo();
        try{
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");

            JSONObject shopInfo = (JSONObject) jsonObject.get("shop_info");
            JSONObject shopMenu = (JSONObject) jsonObject.get("shop_menu");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}