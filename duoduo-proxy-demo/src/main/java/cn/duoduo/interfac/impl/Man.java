package cn.duoduo.interfac.impl;

import cn.duoduo.interfac.IPerson;

public class Man implements IPerson {


    @Override
    public void say() {
        System.out.println("Man saying");
    }

    @Override
    public void eat(int food) {

        System.out.println("Man eat food "+food);
    }
}
