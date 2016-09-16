package net.woniper.tdd.test.machine;

import net.woniper.tdd.test.machine.exception.NotBuyProduct;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by woniper on 2016. 9. 13..
 */
public class MachineTest {

    private Machine machine;

    @Before
    public void setUp() throws Exception {
        machine = new Machine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertMoneyZeroThrownIllegaArguEx() throws Exception {
        machine.insertCoin(0);
    }

    @Test
    public void testInsertCoinAndChange() throws Exception {
        int totalMoney = 3000;
        machine.insertCoin(1000);
        machine.insertCoin(1000);
        machine.insertCoin(1000);
        assertEquals(machine.getMoneyChange(), totalMoney, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_상품구매_요청_미등록_상품_empty() throws Exception {
        machine.insertCoin(500);
        machine.buy("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_상품구매_요청_미등록_상품_null() throws Exception {
        machine.insertCoin(500);
        machine.buy(null);
    }

    @Test(expected = NotBuyProduct.class)
    public void test_상품구매_요청_미등록_상품_notFound() throws Exception {
        machine.insertCoin(500);
        machine.buy("아무거나");
    }

    @Test(expected = NotBuyProduct.class)
    public void test_상품구매_요청_금액_부족() throws Exception {
        machine.insertCoin(500);
        Machine.Product product = machine.buy(Machine.CIDER);
        assertNotNull(product);
    }

    // todo 개선이 필요할듯
    @Test(expected = NotBuyProduct.class)
    public void test_상품구매_요청_수량_부족() throws Exception {
        machine.insertCoin(2000);
        machine.buy(Machine.COLA);
        machine.buy(Machine.COLA);
        machine.buy(Machine.COLA);
        // 이 부분에서 수량부족 에러
        machine.buy(Machine.COLA);
    }

    // todo 여기는 mock 객체를 사용하는게 좋을듯.
    @Test
    public void 동전_입력_후_상품_리스트() throws Exception {
        machine.insertCoin(1000);
        List<Machine.Product> products = machine.getProducts();
        long isBuyCount = products.stream().filter(Machine.Product::isBuy).count();
        assertEquals(isBuyCount, 3);
    }
}
