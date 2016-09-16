package net.woniper.tdd.test.machine2;

import java.util.List;

/**
 * Created by woniper on 2016. 9. 16..
 */
public interface ProductRepository {
    List<Product> findAll();

    Product findByName(String name);
}
