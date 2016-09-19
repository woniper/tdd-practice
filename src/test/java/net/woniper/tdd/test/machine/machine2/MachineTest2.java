package net.woniper.tdd.test.machine.machine2;

import net.woniper.tdd.machine.machine2.MachineService2;
import net.woniper.tdd.machine.machine2.Product2;
import net.woniper.tdd.machine.machine2.ProductRepository2;
import net.woniper.tdd.machine.machine2.exception.ImpossibleBuyProductException;
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
public class MachineTest2 {

    private MachineService2 machineService;
    private String COLA = "콜라";
    private String CIDER = "사이다";
    private String COFFEE = "커피";
    private ProductRepository2 productRepository;

    @Before
    public void setUp() throws Exception {
        machineService = new MachineService2();
        productRepository = mock(ProductRepository2.class);
        fixtureMockProudct();
        machineService.setProductRepository(productRepository);
    }

    private void fixtureMockProudct() {
        Product2 cola = new Product2(COLA, 500, 3);
        Product2 cider = new Product2(CIDER, 700, 2);
        Product2 coffee = new Product2(COFFEE, 1200, 1);
        when(productRepository.findAll()).thenReturn(Arrays.asList(cola, cider, coffee));
        when(productRepository.findByName(COLA)).thenReturn(cola);
        when(productRepository.findByName(CIDER)).thenReturn(cider);
        when(productRepository.findByName(COFFEE)).thenReturn(coffee);
    }

    // 동전 주입 테스트
    @Test
    public void test_insertCoin_0원_이하면_throwException() throws Exception {
        assertInsertCoinThrownIllegalArgumentEx(0);
        assertInsertCoinThrownIllegalArgumentEx(-100);
    }

    @Test
    public void test_insertCoin_후_현재잔액() throws Exception {
        machineService.insertCoin(1000);
        int balance = machineService.getBalance();
        assertEquals(1000, balance, 0);
    }

    private void assertInsertCoinThrownIllegalArgumentEx(int coin) {
        IllegalArgumentException exception = null;
        try {
            machineService.insertCoin(coin);
        } catch (Exception e) {
            exception = (IllegalArgumentException)e;
        }
        assertThat(exception, isA(IllegalArgumentException.class));
    }

    // 상품 구매 테스트
    @Test(expected = ImpossibleBuyProductException.class)
    public void test_상품_구매시_thrownEx() throws Exception {
        // given
        machineService.insertCoin(1000);

        // when
        machineService.buy(COFFEE);
    }

    @Test
    public void test_상품_구매시_상품명_valid_thrownEx() throws Exception {
        // given
        machineService.insertCoin(1000);

        // then
        assertBuyThrownImpossibleBuyProductEx("");
        assertBuyThrownImpossibleBuyProductEx(null);
        assertBuyThrownImpossibleBuyProductEx("아무거나 상품");
    }

    @Test
    public void test_상품구매() throws Exception {
        // given
        machineService.insertCoin(1000);

        // when
        Product2 product = machineService.buy(COLA);

        // then
        assertNotNull(product);
    }

    private void assertBuyThrownImpossibleBuyProductEx(String productName) {
        ImpossibleBuyProductException exception = null;

        try {
            machineService.buy(productName);
        } catch (Exception e) {
            exception = (ImpossibleBuyProductException)e;
        }

        assertThat(exception, isA(ImpossibleBuyProductException.class));
    }

    // 품절 테스트
    @Test(expected = ImpossibleBuyProductException.class)
    public void test_상품구매_품절_테스트() throws Exception {
        // given
        machineService.insertCoin(1700);
        machineService.buy(COLA);
        machineService.buy(COLA);
        machineService.buy(COLA);

        // when
        machineService.buy(COLA);
    }
}
