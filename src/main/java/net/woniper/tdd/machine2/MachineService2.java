package net.woniper.tdd.machine2;

import net.woniper.tdd.machine2.exception.ImpossibleBuyProductException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class MachineService2 {
    private ProductRepository2 productRepository;
    private int balance;

    public void setProductRepository(ProductRepository2 productRepository) {
        this.productRepository = productRepository;
    }

    public void insertCoin(int coin) {
        this.balance += coin;
        if(coin <= 0)
            throw new IllegalArgumentException();

        System.out.println(getPossibleBuyProducts().toString());
    }

    public int getBalance() {
        return this.balance;
    }

    private List<Product2> getPossibleBuyProducts() {
        return productRepository.findAll().stream()
                .filter(product -> product.isBuyPossible(getBalance()))
                .collect(Collectors.toList());
    }

    public Product2 buy(String productName) {
        if(productName == null || productName.isEmpty())
            throw new ImpossibleBuyProductException();

        Product2 product = productRepository.findByName(productName);

        if(product == null || !product.isBuyPossible(getBalance()))
            throw new ImpossibleBuyProductException();

        this.balance -= product.getPrice();

        return product;
    }
}
