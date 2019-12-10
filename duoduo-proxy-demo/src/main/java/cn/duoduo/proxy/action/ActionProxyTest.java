package cn.duoduo.proxy.action;

import cn.duoduo.interfac.IPerson;
import cn.duoduo.interfac.impl.Man;
import cn.duoduo.interfac.impl.Woman;

public class ActionProxyTest {

    public static void main(String[] args) {
        Man man = new Man();
        Woman woman = new Woman();

        //男生说话代理
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(man);
        IPerson iPerson = (IPerson) myInvocationHandler.createProxyObj();
        iPerson.say();

        //女生说话代理
        myInvocationHandler.setTarget(woman);
        iPerson =(IPerson) myInvocationHandler.createProxyObj();
        iPerson.say();
        iPerson.eat(10);

    }
}
