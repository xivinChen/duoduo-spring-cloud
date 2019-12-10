package cn.duoduo.proxy.statics;

import cn.duoduo.interfac.IPerson;

/**
 * 静态代理，每一个目标对象对应一个 代理类
 */
public class PersonProxy implements IPerson {

    private IPerson iPerson;

    public IPerson getiPerson() {
        return iPerson;
    }

    public void setiPerson(IPerson iPerson) {
        this.iPerson = iPerson;
    }

    @Override
    public void say() {

        iPerson.say();
    }

    @Override
    public void eat(int food) {

        iPerson.eat(food);
    }
}
