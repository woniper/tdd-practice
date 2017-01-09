package net.woniper.tdd.toby.chapter7.test;

import net.woniper.tdd.toby.chapter7.proxy.Hello;
import net.woniper.tdd.toby.chapter7.proxy.HelloTarget;
import net.woniper.tdd.toby.chapter7.proxy.HelloUppercase;
import net.woniper.tdd.toby.chapter7.proxy.UppercaseHandler;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by woniper on 2017. 1. 2..
 */
public class ProxyTest {

    @Test
    public void methodSignaturePointcut() throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int net.woniper.tdd.toby.chapter7.test.Target.minus(int, int) throws java.lang.RuntimeException)");

        assertThat(pointcut.getClassFilter().matches(Target.class)
                && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null),
                is(true));

        assertThat(pointcut.getClassFilter().matches(Target.class)
                && pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null),
                is(false));

        assertThat(pointcut.getClassFilter().matches(Bean.class)
                && pointcut.getMethodMatcher().matches(Bean.class.getMethod("method"), null),
                is(false));
    }

    @Test
    public void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("woniper"), is("Hello woniper"));
        assertThat(hello.sayHi("woniper"), is("Hi woniper"));
        assertThat(hello.sayThankYou("woniper"), is("Thank You woniper"));
    }

    @Test
    public void targetProxy() throws Exception {
        Hello hello = new HelloUppercase();
        assertThat(hello.sayHello("woniper"), is("HELLO WONIPER"));
        assertThat(hello.sayHi("woniper"), is("HI WONIPER"));
        assertThat(hello.sayThankYou("woniper"), is("THANK YOU WONIPER"));
    }

    @Test
    public void dynamicProxy() throws Exception {
        Hello hello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget())
        );
        assertThat(hello.sayHello("woniper"), is("HELLO WONIPER"));
        assertThat(hello.sayHi("woniper"), is("HI WONIPER"));
        assertThat(hello.sayThankYou("woniper"), is("THANK YOU WONIPER"));
    }

    @Test
    public void proxyFactoryBean() throws Exception {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(new HelloTarget());
        factoryBean.addAdvice(new UppercaseAdvice());

        Hello hello = (Hello)factoryBean.getObject();
        assertThat(hello.sayHello("woniper"), is("HELLO WONIPER"));
        assertThat(hello.sayHi("woniper"), is("HI WONIPER"));
        assertThat(hello.sayThankYou("woniper"), is("THANK YOU WONIPER"));
    }

    @Test
    public void pointcutAdvisor() throws Exception {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(new HelloTarget());

        // pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        // advice
        UppercaseAdvice advice = new UppercaseAdvice();

        factoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, advice));

        Hello hello = (Hello)factoryBean.getObject();
        assertThat(hello.sayHello("woniper"), is("HELLO WONIPER"));
        assertThat(hello.sayHi("woniper"), is("HI WONIPER"));
        assertThat(hello.sayThankYou("woniper"), is("Thank You woniper"));
    }

    @Test
    public void classNamePointcutAdvisor() throws Exception {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut(){
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloT");
                    }
                };
            }
        };
        classMethodPointcut.setMappedName("sayH*");

        checkAdvice(new HelloTarget(), classMethodPointcut, true);

        class HelloWorld extends HelloTarget {};
        checkAdvice(new HelloWorld(), classMethodPointcut, false);

        class HelloToby extends HelloTarget {};
        checkAdvice(new HelloToby(), classMethodPointcut, true);
    }

    private void checkAdvice(Object target, Pointcut pointcut, boolean adviced) {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(target);
        factoryBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) factoryBean.getObject();

        if(adviced) {
            assertThat(proxiedHello.sayHello("woniper"), is("HELLO WONIPER"));
            assertThat(proxiedHello.sayHi("woniper"), is("HI WONIPER"));
            assertThat(proxiedHello.sayThankYou("woniper"), is("Thank You woniper"));
        } else {
            assertThat(proxiedHello.sayHello("woniper"), is("Hello woniper"));
            assertThat(proxiedHello.sayHi("woniper"), is("Hi woniper"));
            assertThat(proxiedHello.sayThankYou("woniper"), is("Thank You woniper"));
        }

    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            String ret = (String)methodInvocation.proceed();
            return ret.toUpperCase();
        }
    }
}