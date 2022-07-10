package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class YMenuGroup {
    String name;
    List<YMenu> items;
    String slug;
}
