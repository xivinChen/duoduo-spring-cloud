package cn.duoduo.config;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class CacheKeyGenerator implements KeyGenerator {

    private static final String separation = "-";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String methodName = method.getName();
        for (Object param : params) {
            methodName += separation+param;
        }
        return methodName;
    }
}
