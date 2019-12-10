package cn.duoduo.interfac.impl;

import cn.duoduo.interfac.IPerson;

public class Woman implements IPerson {

    @Override
    public void say() {
        System.out.println("woman saying");
    }

    @Override
    public void eat(int food) {

        System.out.println("woman eat food "+food);

    }
}
