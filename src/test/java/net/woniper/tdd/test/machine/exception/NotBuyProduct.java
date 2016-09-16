package net.woniper.tdd.test.machine.exception;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class NotBuyProduct extends RuntimeException {
    public NotBuyProduct(String productName) {
        super(productName);
    }
}
