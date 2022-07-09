package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Restaurant {
    int id;
    String name;
    boolean is_available_delivery;
    boolean is_available_pickup;
    List<String> category;
    int additional_discount;
    double review_avg;
    int review_count;
    double lat;
    double lon;
    double distance;
    int min_order_amount;
    int free_delivery_threshold;
    boolean is_deliverable;
    String estimated_delivery_time;
    boolean has_shop_coupons;
    int adjusted_delivery_fee;
    String phone;
    String address;
    String logo_url;
    int minimum_pickup_minutes;
}
