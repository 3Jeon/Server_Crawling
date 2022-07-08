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
    public static void main(String [] args){
        String API_SECRET = "fe5183cc3dea12bd0ce299cf110a75a2";
        String API_KEY = "iphoneap";
        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/?category=치킨&items=5&lat=37.54775437002297&lng=127.06083008344&page=0";
        Document doc = null;
        try{
            doc = Jsoup.connect(url)
                    .header("X-ApiSecret", API_SECRET)
                    .header("X-Apikey", API_KEY)
                    .header("accept", "text/html,application/xhtml+xml,application/xml")
                    .header("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)" +
                            " Chrome/102.0.5005.108 Whale/3.15.136.18 Safari/537.36")
                    .timeout(3000)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            JSONArray jsonArray = (JSONArray) jsonObject.get("restaurants");
            for(int i=0; i<jsonArray.size(); i++){
                JSONObject tmp = (JSONObject) jsonArray.get(i);
                System.out.println(tmp.get("id"));
                System.out.println(tmp.get("name"));
                System.out.println(tmp.get("is_available_delivery"));
                System.out.println(tmp.get("is_available_pickup"));

                JSONArray categories = (JSONArray)tmp.get("categories");
                Iterator<String> iterator = categories.iterator();
                while(iterator.hasNext()){
                    System.out.print(iterator.next() + '\t');
                }
                System.out.println();

                System.out.println(tmp.get("lat"));
                System.out.println(tmp.get("lng"));
                System.out.println(tmp.get("distance"));
                System.out.println(tmp.get("min_order_amount"));
                System.out.println(tmp.get("free_delivery_threshold"));
                System.out.println(tmp.get("is_deliverable"));
                System.out.println(tmp.get("open"));
                System.out.println(tmp.get("estimated_delivery_time"));
                System.out.println(tmp.get("adjusted_delivery_fee"));
                System.out.println(tmp.get("phone"));
                System.out.println(tmp.get("address"));
                System.out.println(tmp.get("logo_url"));
                System.out.println(tmp.get("minimum_pickup_minutes"));
                System.out.println("----------------------------------------------------\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println(doc.text());
    }
}