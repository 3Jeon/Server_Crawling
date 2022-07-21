package _3jeon.server.yogiyo.component;

import _3jeon.server.config.secret.Secret;
import _3jeon.server.yogiyo.model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class YogiyoJsoup {

    // 주변 레스토랑 불러오기
    public List<YRestaurant> getYRestaurantList(String category, double lat, double lng, int page, int items) {
        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/";
        Connection conn = Jsoup.connect(url)
                .header("Host", "www.yogiyo.co.kr")
                .header("Accept-Encoding", "br, gzip, deflate")
                .header("Accept-Language", "ko-KR;q=1.0, en-US;q=0.9, ja-JP;q=0.8")
                .header("X-ApiSecret", Secret.YOGIYO_API_SECRET)
                .header("X-Apikey", Secret.YOGIYO_API_KEY)
                .header("accept", "text/html,application/xhtml+xml,application/xml")
                .header("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/102.0.5005.108 Whale/3.15.136.18 Safari/537.36")
                .data("items", Integer.toString(items))
                .data("category", category)
                .data("lat", Double.toString(lat))
                .data("lng", Double.toString(lng))
                .data("order", "review_count")
                .data("page", Integer.toString(page))
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
                        Long.parseLong(String.valueOf(tmp.get("id"))),
                        (String) tmp.get("name"),
                        (boolean) tmp.get("open"),
                        (boolean) tmp.get("is_available_delivery"),
                        (boolean) tmp.get("is_available_pickup"),
                        categoryList,
                        (long) tmp.get("additional_discount"),
                        (double) tmp.get("review_avg"),
                        (long)tmp.get("review_count"),
                        (double) tmp.get("lat"),
                        (double) tmp.get("lng"),
                        (double) tmp.get("distance"),
                        (long) tmp.get("min_order_amount"),
                        Long.parseLong(String.valueOf(tmp.get("free_delivery_threshold"))),
                        (String) tmp.get("estimated_delivery_time"),
                        (boolean) tmp.get("has_shop_coupons"),
                        Long.parseLong(String.valueOf(tmp.get("adjusted_delivery_fee"))),
                        (String) tmp.get("phone"),
                        (String) tmp.get("address"),
                        (String) tmp.get("logo_url"),
                        Long.parseLong(String.valueOf(tmp.get("minimum_pickup_minutes")))
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

    // 매장 ID에 대하여 매장의 메뉴 불러오기
    public List<YMenuGroup> getYMenuList(long id) {
        String url = String.format("https://www.yogiyo.co.kr/api/v1/restaurants/%s/menu/", Long.toString(id));

        Connection conn = Jsoup.connect(url)
                .header("Host", "www.yogiyo.co.kr")
                .header("Accept-Encoding", "br, gzip, deflate")
                .header("Accept-Language", "ko-KR;q=1.0, en-US;q=0.9, ja-JP;q=0.8")
                .header("X-ApiSecret", Secret.YOGIYO_API_SECRET)
                .header("X-Apikey", Secret.YOGIYO_API_KEY)
                .header("accept", "text/html,application/xhtml+xml,application/xml")
                .header("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                        " Chrome/102.0.5005.108 Whale/3.15.136.18 Safari/537.36")
                .timeout(5000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;

        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(doc.text());

            List<YMenuGroup> yMenuGroupList = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject tmp = (JSONObject) jsonArray.get(i);
                JSONArray items = (JSONArray) tmp.get("items");

                List<YMenu> yMenus = new ArrayList<>();
                for (int j = 0; j < items.size(); j++) {
                    JSONObject item = (JSONObject) items.get(j);
                    List<YSubMenu> ySubMenus = getYSubMenu((JSONArray) item.get("subchoices"));

                    YMenu yMenu = new YMenu(
                            (boolean) item.get("soldout"),
                            Long.parseLong(String.valueOf(item.get("review_count"))),
                            (String) item.get("subtitle"),
                            (String) item.get("description"),
                            Long.parseLong(String.valueOf(item.get("price"))),
                            (String) item.get("slug"),
                            ySubMenus,
                            Long.parseLong(String.valueOf(item.get("id"))),
                            (String) item.get("name")
                    );
                    yMenus.add(yMenu);
                }

                YMenuGroup group = new YMenuGroup(
                        (String) tmp.get("name"),
                        items,
                        (String) tmp.get("slug")
                );
                yMenuGroupList.add(group);
            }

            return yMenuGroupList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 매장 이름 검색
    public List<YRestaurant> getYSearchRestaurants(double lat, double lng, int items, int page, String search) {
        String url = "https://api.yogiyo.co.kr/discovery/search/all";
        Connection conn = Jsoup.connect(url)
                .header("Host", "api.yogiyo.co.kr")
                .header("Accept-Encoding", "br, gzip, deflate")
                .header("Accept-Language", "ko-KR;q=1.0, en-US;q=0.9, ja-JP;q=0.8")
                .header("X-ApiSecret", Secret.YOGIYO_API_SECRET)
                .header("X-Apikey", Secret.YOGIYO_API_KEY)
                .data("items", Integer.toString(items))
                .data("lat", Double.toString(lat))
                .data("lng", Double.toString(lng))
                .data("page", Integer.toString(page))
                .data("search", search)
                .timeout(5000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<YRestaurant> YRestaurants = new ArrayList<>();
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("restaurant");
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
                        Long.parseLong(String.valueOf(tmp.get("id"))),
                        (String) tmp.get("name"),
                        (boolean) tmp.get("open"),
                        (boolean) tmp.get("is_available_delivery"),
                        (boolean) tmp.get("is_available_pickup"),
                        categoryList,
                        Long.parseLong(String.valueOf(tmp.get("additional_discount"))),
                        (double) tmp.get("review_avg"),
                        Long.parseLong(String.valueOf(tmp.get("review_count"))),
                        (double) tmp.get("lat"),
                        (double) tmp.get("lng"),
                        (double) tmp.get("distance"),
                        Long.parseLong(String.valueOf(tmp.get("min_order_amount"))),
                        (int) Double.parseDouble(String.valueOf(tmp.get("free_delivery_threshold"))),
                        (String) tmp.get("estimated_delivery_time"),
                        (boolean) tmp.get("has_shop_coupons"),
                        Long.parseLong(String.valueOf(tmp.get("adjusted_delivery_fee"))),
                        (String) tmp.get("phone"),
                        (String) tmp.get("address"),
                        (String) tmp.get("logo_url"),
                        Long.parseLong(String.valueOf(tmp.get("minimum_pickup_minutes")))
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

    List<YSubMenu> getYSubMenu(JSONArray jsonArray) {
        List<YSubMenu> ySubMenus = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject cur = (JSONObject) jsonArray.get(i);

            List<YSelectMenu> ySelectMenus = new ArrayList<>();
            JSONArray sub_cur = (JSONArray) cur.get("subchoices");
            for (int j = 0; j < sub_cur.size(); j++) {
                JSONObject tmp = (JSONObject) sub_cur.get(j);
                YSelectMenu ySelectMenu = new YSelectMenu(
                        (String) tmp.get("slug"),
                        (String) tmp.get("description"),
                        Long.parseLong(String.valueOf(tmp.get("price"))),
                        Long.parseLong(String.valueOf(tmp.get("id"))),
                        (boolean) tmp.get("soldout"),
                        (String) tmp.get("name")
                );
                ySelectMenus.add(ySelectMenu);
            }

            YSubMenu ySubMenu = new YSubMenu(
                    (boolean) cur.get("multiple"),
                    (String) cur.get("name"),
                    Long.parseLong(String.valueOf(cur.get("multiple_count"))),
                    ySelectMenus,
                    (boolean) cur.get("mandatory"),
                    (String) cur.get("slug")
            );

            ySubMenus.add(ySubMenu);
        }

        return ySubMenus;
    }
}