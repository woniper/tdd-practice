package net.woniper.tdd.machine1.exception;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class NotBuyProduct1 extends RuntimeException {
    public NotBuyProduct1(String productName) {
        super(productName);
    }
}
