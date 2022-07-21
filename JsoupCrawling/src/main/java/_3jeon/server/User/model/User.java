package _3jeon.server.User.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    long id;
    String name;
    String phoneNumber;
    String address;
    double lat;
    double lng;
}
