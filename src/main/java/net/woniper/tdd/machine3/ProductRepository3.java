package net.woniper.tdd.machine3;

import java.util.List;

/**
 * Created by woniper on 2016. 9. 16..
 */
public interface ProductRepository3 {
    List<Product3> findAll();

    Product3 findByName(String name);
}
