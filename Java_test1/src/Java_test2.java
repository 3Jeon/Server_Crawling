import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class Java_test2 {
    public static void main(String[] args) {
        String url = "https://www.yogiyo.co.kr" +
                "/mobile/?gclid=CjwKCAjwwo-WBhAMEiwAV4dybb4QrqD9topK7yhJUUyZfsl9Bu-CingFfYrukeoPZIEbYIcZHqakpRoCIeAQAvD_BwE#" +
                "/499638/";
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(doc.toString());
    }
}