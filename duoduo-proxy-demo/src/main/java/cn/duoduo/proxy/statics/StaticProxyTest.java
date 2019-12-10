package cn.duoduo.proxy.statics;

import cn.duoduo.interfac.impl.Man;
import cn.duoduo.interfac.impl.Woman;

public class StaticProxyTest {

    public static void main(String[] args) {

        //代理男生说话
        Man man = new Man();
        PersonProxy personProxy = new PersonProxy();
        personProxy.setiPerson(man);
        personProxy.say();

        //代理女生说话
        Woman woman = new Woman();
        personProxy.setiPerson(woman);
        personProxy.say();
    }
}
