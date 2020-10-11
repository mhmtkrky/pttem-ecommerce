package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStock {
    private Long uuid;
    private Integer count;


    public ProductStock(Long uuid, Integer count) {
        this.uuid = uuid;
        this.count = count;
    }
}
