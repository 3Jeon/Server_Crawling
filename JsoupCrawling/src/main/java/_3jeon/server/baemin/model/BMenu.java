package _3jeon.server.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BMenu {
    long menuId;
    String name;
    String image;
    String description;
    boolean soldOut;
    String price;
}
