package _3jeon.server.yogiyo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class YMenu {
    boolean soldout;
    int review_count;
    String subtitle;
    String description;
    int price;
    String slug;
    String section;
    List<YSubMenu> subMenuList;
    int id;
    String name;
}
