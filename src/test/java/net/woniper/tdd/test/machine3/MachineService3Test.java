package net.woniper.tdd.test.machine3;

import net.woniper.tdd.machine3.MachineService3;
import net.woniper.tdd.machine3.Product3;
import net.woniper.tdd.machine3.ProductRepository3;
import net.woniper.tdd.machine3.exception.ImpossibleBuyException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by woniper on 2016. 9. 16..
 */
public class MachineService3Test {

    private MachineService3 machineService3;
    private static final String COLA    = "콜라";
    private static final String CIDER   = "사이다";
    private static final String COFFEE  = "커피";

    @Before
    public void setUp() throws Exception {
        machineService3 = new MachineService3();
        ProductRepository3 repository = mock(ProductRepository3.class);
        fixtureMockProductRepository(repository);
        machineService3.setProductRepository3(repository);
    }

    /**
     * mockito를 사용해 ProductRepository mock fixture data
     * @param repository
     */
    private void fixtureMockProductRepository(ProductRepository3 repository) {
        Product3 cola = new Product3(COLA, 500, 3);
        Product3 cider = new Product3(CIDER, 700, 2);
        Product3 coffee = new Product3(COFFEE, 1200, 1);
        when(repository.findAll()).thenReturn(Arrays.asList(cola, cider, coffee));
        when(repository.findByName(COLA)).thenReturn(cola);
        when(repository.findByName(CIDER)).thenReturn(cider);
        when(repository.findByName(COFFEE)).thenReturn(coffee);
    }

    // TEST coin

    /**
     * 동전 유효성 체크 (0원 이상)
     * @throws Exception
     */
    @Test
    public void testInsertCoinThrownIllegalArgEx() throws Exception {
        assertInsertCoinThrownIllegalArgumentEx(0);
        assertInsertCoinThrownIllegalArgumentEx(-100);
        assertInsertCoinThrownIllegalArgumentEx(-9999);
    }

    /**
     * 현재 잔액 테스트 (정상 테스트)
     * @throws Exception
     */
    @Test
    public void testInsertCoinAndBalance() throws Exception {
        // given
        int totalCoin = 3000;
        machineService3.insertCoin(1000);
        machineService3.insertCoin(1000);
        machineService3.insertCoin(1000);

        // when
        int balance = machineService3.getBalance();

        // then
        assertEquals(totalCoin, balance);
    }

    private void assertInsertCoinThrownIllegalArgumentEx(int coin) {
        IllegalArgumentException ex = null;
        try {
            machineService3.insertCoin(coin);
        } catch (Exception e) {
            ex = (IllegalArgumentException)e;
        }
        assertThat(ex, isA(IllegalArgumentException.class));
    }

    // TEST buy

    /**
     * 상품 구매 시 상품명 인자값 null, empty String validation check
     * @throws Exception
     */
    @Test
    public void testBuyProductThrownIllegalEx() throws Exception {
        assertBuyProductIllegalArgumentEx("");
        assertBuyProductIllegalArgumentEx(null);
    }

    /**
     * 상품 구매 시 유효한 상품인지 체크 (상품명)
     * @throws Exception
     */
    @Test(expected = ImpossibleBuyException.class)
    public void testBuyProductThrownImPossibleBuyEx() throws Exception {
        machineService3.insertCoin(1000);
        machineService3.buy("아무거나 상품");
    }

    /**
     * 상품 구매 시 상품 수량이 존재하는지(품절이 아닌지) 체크
     * @throws Exception
     */
    @Test(expected = ImpossibleBuyException.class)
    public void testBuyProductNotEnoughAmount() throws Exception {
        // given
        machineService3.insertCoin(2000);
        machineService3.buy(COLA);
        machineService3.buy(COLA);
        machineService3.buy(COLA);

        // when
        machineService3.buy(COLA);
    }

    /**
     * 상품 구매 시 돈이 충분한지 체크
     * @throws Exception
     */
    @Test(expected = ImpossibleBuyException.class)
    public void testBuyProductNotEnoughCoin() throws Exception {
        // given
        machineService3.insertCoin(400);

        // when
        machineService3.buy(COLA);
    }

    /**
     * 정상적인 상품 구매 테스트
     * @throws Exception
     */
    @Test
    public void testBuyProduct() throws Exception {
        // given
        machineService3.insertCoin(1000);

        // when
        Product3 product3 = machineService3.buy(COLA);

        // then
        assertNotNull(product3);
    }

    /**
     *
     * @param productName
     */
    private void assertBuyProductIllegalArgumentEx(String productName) {
        IllegalArgumentException ex = null;

        try {
            machineService3.buy(productName);
        } catch(Exception e) {
            ex = (IllegalArgumentException)e;
        }

        assertThat(ex, isA(IllegalArgumentException.class));
    }
}
