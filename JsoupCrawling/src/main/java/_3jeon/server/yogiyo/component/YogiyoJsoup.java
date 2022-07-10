package _3jeon.server.yogiyo.component;

import _3jeon.server.config.secret.Secret;
import _3jeon.server.yogiyo.model.YMenu;
import _3jeon.server.yogiyo.model.YRestaurant;
import _3jeon.server.yogiyo.model.YSelectMenu;
import _3jeon.server.yogiyo.model.YSubMenu;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class YogiyoJsoup {

    public List<YRestaurant> getYRestaurantList(String category, double lat, double lon) {
        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/";
        Connection conn = Jsoup.connect(url)
                .header("X-ApiSecret", Secret.YOGIYO_API_SECRET)
                .header("X-Apikey", Secret.YOGIYO_API_KEY)
                .header("accept", "text/html,application/xhtml+xml,application/xml")
                .header("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/102.0.5005.108 Whale/3.15.136.18 Safari/537.36")
                .data("items", "50")
                .data("category", category)
                .data("lat", Double.toString(lat))
                .data("lng", Double.toString(lon))
                .data("order", "review_count")
                .data("page", "0")
                .timeout(5000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<YRestaurant> YRestaurants = new ArrayList<>();
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            JSONArray jsonArray = (JSONArray) jsonObject.get("restaurants");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tmp = (JSONObject) jsonArray.get(i);
                JSONArray categories = (JSONArray) tmp.get("categories");
                Iterator<String> iterator = categories.iterator();
                List<String> categoryList = new ArrayList<>();
                while (iterator.hasNext()) {
                    categoryList.add(iterator.next().trim());
                }

                YRestaurant YRestaurant = new YRestaurant(
                        Integer.parseInt(String.valueOf(tmp.get("id"))),
                        (String) tmp.get("name"),
                        (boolean) tmp.get("is_available_delivery"),
                        (boolean) tmp.get("is_available_pickup"),
                        categoryList,
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

                YRestaurants.add(YRestaurant);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return YRestaurants;
    }

    public List<YMenu> getYMenuList(int id) {
        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/{}/menu/".format(Integer.toString(id));
        Connection conn = Jsoup.connect(url)
                .header("X-ApiSecret", Secret.YOGIYO_API_SECRET)
                .header("X-Apikey", Secret.YOGIYO_API_KEY)
                .header("accept", "text/html,application/xhtml+xml,application/xml")
                .header("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/102.0.5005.108 Whale/3.15.136.18 Safari/537.36")
                .timeout(5000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<YMenu> YMenus = new ArrayList<>();

        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            JSONArray jsonArray = (JSONArray) jsonObject.get("items");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tmp = (JSONObject) jsonArray.get(i);

                JSONArray subchoices = (JSONArray) tmp.get("subchoices");
                List<YSubMenu> ySubMenus = getYSubMenu(subchoices);

                YMenu yMenu = new YMenu(
                        (boolean) tmp.get("soldout"),
                        Integer.parseInt(String.valueOf(tmp.get("review_couunt"))),
                        (String) tmp.get("subtitle"),
                        (String) tmp.get("description"),
                        Integer.parseInt(String.valueOf(tmp.get("price"))),
                        (String) tmp.get("slug"),
                        ySubMenus,
                        Integer.parseInt(String.valueOf(tmp.get("id"))),
                        (String) tmp.get("name")
                );

                YMenus.add(yMenu);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return YMenus;
    }

    List<YSubMenu> getYSubMenu(JSONArray jsonArray){
        List<YSubMenu> ySubMenus = new ArrayList<>();
        for(int i=0; i<jsonArray.size(); i++){
            JSONObject cur = (JSONObject) jsonArray.get(i);

            List<YSelectMenu> ySelectMenus = new ArrayList<>();
            JSONArray sub_cur = (JSONArray) cur.get("subchoices");
            for(int j=0; j<sub_cur.size(); j++){
                JSONObject tmp = (JSONObject) sub_cur.get(j);
                YSelectMenu ySelectMenu = new YSelectMenu(
                        (String) tmp.get("slug"),
                        (String) tmp.get("description"),
                        Integer.parseInt(String.valueOf(tmp.get("price"))),
                        Integer.parseInt(String.valueOf(tmp.get("id"))),
                        (boolean) tmp.get("soldout"),
                        (String) tmp.get("name")
                );
                ySelectMenus.add(ySelectMenu);
            }

            YSubMenu ySubMenu = new YSubMenu(
                    (boolean) cur.get("multiple"),
                    (String) cur.get("name"),
                    Integer.parseInt(String.valueOf(cur.get("multiple_count"))),
                    ySelectMenus,
                    (boolean) cur.get("mandatory"),
                    (String) cur.get("slug")
            );

            ySubMenus.add(ySubMenu);
        }

        return ySubMenus;
    }
}