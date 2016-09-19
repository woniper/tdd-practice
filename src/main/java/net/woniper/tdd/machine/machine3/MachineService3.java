package net.woniper.tdd.machine.machine3;

import net.woniper.tdd.machine.machine3.exception.ImpossibleBuyException;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class MachineService3 {

    private int coin;
    private ProductRepository3 productRepository3;

    public void insertCoin(int coin) {
        if(coin <= 0)
            throw new IllegalArgumentException();

        printImpossibleBuyProduct();
        this.coin += coin;
    }

    private void printImpossibleBuyProduct() {
        productRepository3.findAll().stream()
                .filter(product3 -> product3.impossibleBuy(getBalance()))
                .forEach(System.out::println);
    }

    public int getBalance() {
        return coin;
    }

    public void setProductRepository3(ProductRepository3 productRepository3) {
        this.productRepository3 = productRepository3;
    }

    public Product3 buy(String productName) {
        if(productName == null || productName.isEmpty())
            throw new IllegalArgumentException();

        Product3 product3 = productRepository3.findByName(productName);

        if(product3 == null || !product3.impossibleBuy(getBalance()))
            throw new ImpossibleBuyException();

        product3.buy();
        this.coin -= product3.getPrice();
        return product3;
    }
}
