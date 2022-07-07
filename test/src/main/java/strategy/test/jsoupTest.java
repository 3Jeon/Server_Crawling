package strategy.test;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//위도, 경도 추가하기
//db 저장 방법 알아보기

@Component
@RequiredArgsConstructor
public class jsoupTest {
    private static final String url = "https://www.yogiyo.co.kr/api/v1/restaurants-geo/";

    //yml로 옮기기
    String apiKey = "iphoneap";
    String apiSecret = "fe5183cc3dea12bd0ce299cf110a75a2";

    public void process() {
        Connection conn = Jsoup.connect(url)
                .header("X-ApiKey", apiKey)
                .header("X-ApiSecret", apiSecret);

        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> list = getDataList(document);

    }

    private List<String> getDataList(Document document) {
        List<String> list = new ArrayList<>();
        Elements selects = document.select(".sentence-list");

        for(Element select : selects) {
            System.out.println(select.html());
        }

        return list;
    }

}
