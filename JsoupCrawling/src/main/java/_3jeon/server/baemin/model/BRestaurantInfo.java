package _3jeon.server.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BRestaurantInfo {
    BRestaurantDetail bRestaurantDetail;
    BMenu bMenu;
}
