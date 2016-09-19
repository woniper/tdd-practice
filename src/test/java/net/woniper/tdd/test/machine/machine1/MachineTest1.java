package net.woniper.tdd.test.machine.machine1;

import net.woniper.tdd.machine1.Machine1;
import net.woniper.tdd.machine1.exception.NotBuyProduct1;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by woniper on 2016. 9. 13..
 */
public class MachineTest1 {

    private Machine1 machine;

    @Before
    public void setUp() throws Exception {
        machine = new Machine1();
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

    @Test(expected = NotBuyProduct1.class)
    public void test_상품구매_요청_미등록_상품_notFound() throws Exception {
        machine.insertCoin(500);
        machine.buy("아무거나");
    }

    @Test(expected = NotBuyProduct1.class)
    public void test_상품구매_요청_금액_부족() throws Exception {
        machine.insertCoin(500);
        Machine1.Product product = machine.buy(Machine1.CIDER);
        assertNotNull(product);
    }

    /**
     * 1. 2000원을 넣고, 4번 구매를 발생시켜 NotBuyProduct1 에러를 발생시키는게
     * 좋을 테스트 코드일까?
     *
     * 2. 4번 구매를 발생 시킨다는 것은 Machine 코드를 들여다 봐야된다.
     *
     * @throws Exception
     */
    @Test(expected = NotBuyProduct1.class)
    public void test_상품구매_요청_수량_부족() throws Exception {
        machine.insertCoin(2000);
        machine.buy(Machine1.COLA);
        machine.buy(Machine1.COLA);
        machine.buy(Machine1.COLA);
        // 이 부분에서 수량부족 에러
        machine.buy(Machine1.COLA);
    }

    /**
     * mock 객체를 사용해서 테스트 코드 자체에서 Machine에 ProductMock을 주입한다면
     * 더 깔끔한 코드와 테스트 코드를 작성할 수 있지 않을까?
     * @throws Exception
     */
    @Test
    public void 동전_입력_후_상품_리스트() throws Exception {
        machine.insertCoin(1000);
        List<Machine1.Product> products = machine.getProducts();
        long isBuyCount = products.stream().filter(Machine1.Product::isBuy).count();
        assertEquals(isBuyCount, 3);
    }
}
