package cn.duoduo.JDKProxy;

public class InterfaceTestImpl implements InterfaceTest {

    public InterfaceTestImpl() {
        super();
        System.out.println("正在 生成 一个  InterfaceTestImpl 实例");
    }

    @Override
    public String say(String name) {
        System.out.println("say is :"+name);
        return name+" hello,JDK implement AOP";
    }

    @Override
    public void eat(String food) {

        System.out.println("eat food is :"+food);

    }
}
