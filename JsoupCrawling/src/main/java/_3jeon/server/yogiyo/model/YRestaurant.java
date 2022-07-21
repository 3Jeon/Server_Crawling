package _3jeon.server.yogiyo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class YRestaurant {
    long id;
    String name;
    boolean open;
    boolean is_available_delivery;
    boolean is_available_pickup;
    List<String> category;
    long additional_discount;
    double review_avg;
    long review_count;
    double lat;
    double lng;
    double distance;
    long min_order_amount;
    long free_delivery_threshold;
    String estimated_delivery_time;
    boolean has_shop_coupons;
    long adjusted_delivery_fee;
    String phone;
    String address;
    String logo_url;
    long minimum_pickup_minutes;
}
