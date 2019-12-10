package cn.duoduo.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(" cglib 代理 前 处理 ！！！！");
        Object result = methodProxy.invokeSuper(o, objects);
        return result;
    }

    /**
     * 根据 类型 生成代理对象 ，次方法不一定要放在 此代理中
     * @param clazz
     * @return
     */
    public Object createProxyObj(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
}
