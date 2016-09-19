package net.woniper.tdd.machine.machine1;

import net.woniper.tdd.machine.machine1.exception.NotBuyProduct1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by woniper on 2016. 9. 13..
 */
public class Machine1 {

    public static final String COLA =   "콜라";
    public static final String CIDER =  "사이다";
    public static final String FANTA =  "환타";
    public static final String TREVI =  "트레비";
    public static final String COFFEE = "커피";
    public static final String BEER =   "맥주";

    private Money money = new Money();
    private List<Product> products;

    public Machine1() {
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
                    throw new NotBuyProduct1("금액 부족 또는 수량 부족" + product.toString());
                }
            }
        }

        throw new NotBuyProduct1(productName);
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

    /**
     * 1. Product 클래스는 내부 클래스가 아닌 일반 클래스로 생성 되야 할 것 같다
     * 내부 클래스로 존재할게 아니라 외부에서 주입받아야되는 대상이라고 생각됨.
     *
     * 2. 실제 자판기를 생각해보자.
     * 원래 자판기는 상품을 갖고 있는게 아니라 자판기 관리자가 자판기에 상품을 넣는(주입)다.
     * 테스트 코드를 작성하다보니 Product 코드는 외부에서 주입 받는게 더 테스트 코드를 작성하기에
     * 좋다고 판단된다.
     *
     * 3.MachineTest1.동전_입력_후_상품_리스트 메소드를 보면 상품 리스트를 가져와서
     * count를 계산 할게 아니라, mock객체로 product를 주입해 테스트하는게 맞는듯하다.
     * 지금과 같은 코드는 Machine 코드안에 product amount를 몇개로 생성했느냐를 봐야만
     * 테스트 코드를 작성할 수 있다. 하지만 외부에서 mock객체를 주입한다면 구지 Machine
     * 클래스를 확인 할 필요가 있을까?
     *
     */
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

        /**
         * 상품을 구매와 구매에 필요한 데이터 처리는 Machine이 하는게 적당한거 같다.
         */
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
