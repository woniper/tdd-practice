package net.woniper.tdd.machine.machine3;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class Product3 {
    private String name;
    private int price;
    private int amount;

    public Product3(String name, int price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }

    public boolean impossibleBuy(int balance) {
        return this.price <= balance && this.amount > 0;
    }

    public int getPrice() {
        return price;
    }

    public void buy() {
        this.amount--;
    }
}
