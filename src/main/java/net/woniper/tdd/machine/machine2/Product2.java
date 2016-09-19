package net.woniper.tdd.machine.machine2;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class Product2 {
    private String name;
    private int price;
    private int amount;

    public Product2(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isBuyPossible(int price) {
        return getPrice() <= price && getAmount() > 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
