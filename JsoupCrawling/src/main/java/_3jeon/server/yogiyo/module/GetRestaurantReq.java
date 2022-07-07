package _3jeon.server.yogiyo.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantReq {
    String category;
    int items;
    double lat;
    double lng;
    String order;
    int page;
}
