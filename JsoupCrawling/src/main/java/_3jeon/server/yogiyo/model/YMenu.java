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
    long review_count;
    String subtitle;
    String description;
    long price;
    String slug;
    List<YSubMenu> subMenuList;
    long id;
    String name;
}
