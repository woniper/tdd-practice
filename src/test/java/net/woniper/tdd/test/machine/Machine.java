package net.woniper.tdd.test.machine;

import net.woniper.tdd.test.machine.exception.NotBuyProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by woniper on 2016. 9. 13..
 */
public class Machine {

    public static final String COLA =   "콜라";
    public static final String CIDER =  "사이다";
    public static final String FANTA =  "환타";
    public static final String TREVI =  "트레비";
    public static final String COFFEE = "커피";
    public static final String BEER =   "맥주";

    private Money money = new Money();
    private List<Product> products;

    public Machine() {
        this.products = initProducts();
    }

    public void insertCoin(int coin) {
        if(coin <= 0) {
            throw new IllegalArgumentException();
        }

        money.addCoin(coin);
        products.stream()
                .forEach(product -> {
                    product.setBuy(getMoneyChange());
                });
        getBuyPossibleProducts().forEach(System.out::println);
    }

    public int getMoneyChange() {
        return money.getChange();
    }

    public List<Product> getProducts() {
        return products;
    }

    private List<Product> getBuyPossibleProducts() {
        return this.products.stream()
                .filter(Product::isBuy)
                .collect(Collectors.toList());
    }

    private List<Product> initProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(COLA, 500, 3));
        products.add(new Product(CIDER, 700, 5));
        products.add(new Product(FANTA, 1000, 10));
        products.add(new Product(TREVI, 1200, 10));
        products.add(new Product(COFFEE, 1500, 10));
        products.add(new Product(BEER, 2000, 20));
        return products;
    }

    public Product buy(String productName) throws RuntimeException {
        if (productName == null || productName.isEmpty())
            throw new IllegalArgumentException(productName);

        for (Product product : this.products) {
            if (product.getProductName().equals(productName)) {
                if(product.isBuy()) {
                    product.buy();
                    return product;
                } else {
                    throw new NotBuyProduct("금액 부족 또는 수량 부족" + product.toString());
                }
            }
        }

        throw new NotBuyProduct(productName);
    }

    private class Money {
        private int coin;

        public void addCoin(int coin) {
            this.coin += coin;
        }

        public int getChange() {
            return coin;
        }
    }

    public static class Product {
        private String productName;
        private int price;
        private boolean isBuy = false;
        private int amount = 0;

        public Product(String productName, int price, int amount) {
            this.productName = productName;
            this.price = price;
            this.amount = amount;
        }

        public void setBuy(int change) {
            this.isBuy = change >= price;
        }

        public boolean isBuy() {
            return isBuy && amount > 0;
        }

        public void buy() {
            if(isBuy()) {
                amount--;
            }
        }

        public String getProductName() {
            return productName;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "productName='" + productName + '\'' +
                    ", price=" + price +
                    ", isBuy=" + isBuy +
                    ", amount=" + amount +
                    '}';
        }
    }
}
