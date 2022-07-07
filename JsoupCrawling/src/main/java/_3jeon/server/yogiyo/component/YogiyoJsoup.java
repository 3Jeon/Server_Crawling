package _3jeon.server.yogiyo.component;

import _3jeon.server.config.BaseException;
import _3jeon.server.config.secret.Secret;
import _3jeon.server.yogiyo.module.Restaurant;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;

@Component
public class YogiyoJsoup {

    public List<Restaurant> getRestaurantList(){
        String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/?category=치킨&items=20&lat=37.54775437002297&lng=127.06083008344&page=0";
        Connection conn = Jsoup.connect(url)
                .header("X-ApiSecret",Secret.API_SECRET)
                .header("X-Apikey", Secret.API_KEY);

        Document doc = null;
        try {
            doc = conn.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(doc.toString());
        return null;
    }
}
/*
X-ApiSecret: fe5183cc3dea12bd0ce299cf110a75a2
x-Apikey: iphoneap
 */