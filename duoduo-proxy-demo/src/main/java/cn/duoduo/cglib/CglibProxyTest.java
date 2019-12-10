package cn.duoduo.cglib;

import cn.duoduo.interfac.IPerson;
import cn.duoduo.interfac.impl.Man;

public class CglibProxyTest {

    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        IPerson proxyObj = (IPerson) cglibProxy.createProxyObj(Man.class);
        proxyObj.eat(10);
    }
}
