import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Iterator;

public class Java_test1 {
    public static void main(String[] args) {
        String url = "http://www.cgv.co.kr/movies/";
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements element = doc.select("div.sect-movie-chart");

        Iterator<Element> ie1 = element.select("strong.rank").iterator();
        Iterator<Element> ie2 = element.select("strong.title").iterator();
        Iterator<Element> ie3 = element.select("strong.percent").iterator();

        System.out.println(element.toString());

//        System.out.println();
//        while(ie1.hasNext())
//            System.out.print(ie1.next().text() + '\t');
//        System.out.println();
//        while(ie2.hasNext())
//            System.out.print(ie2.next().text() + '\t');
//        System.out.println();
//        while(ie3.hasNext())
//            System.out.print(ie3.next().text() + '\t');

        while (ie1.hasNext()) {
            System.out.println(ie1.next().text() + '\t' + ie2.next().text() + '\t' + ie3.next().text());
        }
    }
}