package net.woniper.tdd.machine.machine2;

import java.util.List;

/**
 * Created by woniper on 2016. 9. 16..
 */
public interface ProductRepository2 {
    List<Product2> findAll();

    Product2 findByName(String name);
}
