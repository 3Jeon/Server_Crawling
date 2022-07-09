import model.Restaurant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YogiyoRestaurant {
    public static void main(String[] args) {
        String API_SECRET = "fe5183cc3dea12bd0ce299cf110a75a2";
        String API_KEY = "iphoneap";
        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/";

        Connection conn = Jsoup.connect(url)
                .header("X-ApiSecret", API_SECRET)
                .header("X-Apikey", API_KEY)
                .header("accept", "text/html,application/xhtml+xml,application/xml")
                .header("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/102.0.5005.108 Whale/3.15.136.18 Safari/537.36")
                .data("category", "치킨")
                .data("items", "3")
                .data("lat", "37.54775437002297")
                .data("lng", "127.06083008344")
                .data("order", "review_count")
                .data("page", "0")
                .timeout(5000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            JSONArray jsonArray = (JSONArray) jsonObject.get("restaurants");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tmp = (JSONObject) jsonArray.get(i);
                JSONArray categories = (JSONArray) tmp.get("categories");
                Iterator<String> iterator = categories.iterator();
                List<String> category = new ArrayList<>();
                while (iterator.hasNext()) {
                    category.add(iterator.next());
                }

                Restaurant restaurant = new Restaurant(
                        Integer.parseInt(String.valueOf(tmp.get("id"))),
                        (String) tmp.get("name"),
                        (boolean) tmp.get("is_available_delivery"),
                        (boolean) tmp.get("is_available_pickup"),
                        category,
                        Integer.parseInt(String.valueOf(tmp.get("additional_discount"))),
                        (double) tmp.get("review_avg"),
                        Integer.parseInt(String.valueOf(tmp.get("review_count"))),
                        (double) tmp.get("lat"),
                        (double) tmp.get("lng"),
                        (double) tmp.get("distance"),
                        Integer.parseInt(String.valueOf(tmp.get("min_order_amount"))),
                        Integer.parseInt(String.valueOf(tmp.get("free_delivery_threshold"))),
                        (boolean) tmp.get("is_deliverable"),
                        (String) tmp.get("estimated_delivery_time"),
                        (boolean) tmp.get("has_shop_coupons"),
                        Integer.parseInt(String.valueOf(tmp.get("adjusted_delivery_fee"))),
                        (String) tmp.get("phone"),
                        (String) tmp.get("address"),
                        (String) tmp.get("logo_url"),
                        Integer.parseInt(String.valueOf(tmp.get("minimum_pickup_minutes")))
                );

                restaurants.add(restaurant);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i=0; i<restaurants.size(); i++){
            System.out.println(restaurants.get(i).toString() + restaurants.get(i).getName());
        }
    }
}