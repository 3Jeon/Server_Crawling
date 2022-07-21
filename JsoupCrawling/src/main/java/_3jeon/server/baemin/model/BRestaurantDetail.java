package _3jeon.server.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BRestaurantDetail {
    long Shop_No;
    String Shop_Nm;
    String Tel_No;
    String Vel_No;
    String Addr;
    double Loc_Pnt_Lat;
    double Loc_Pnt_Lng;
    long Review_Cnt;
    double Star_Pnt_Avg;
    long Favorite_Cnt;
    long Ord_Cnt;

    long Min_Ord_Price;
    List<HeadImgFile> headImgFileList;

    boolean deliveryInOperation;
    boolean useDelivery;
    String shopStatus;
    boolean inDeliveryArea;
    boolean userTakeout;
    long takeoutDiscountRate;
    long takeoutDiscountPrice;
    String expectedCookTime;
    String Expected_Delivery_Time;

}
