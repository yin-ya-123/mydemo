package com.yin.utils.Packaging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class MapWrapper {

    public static Object wrap(Map<String, Object> map) {
        return Proxy.newProxyInstance(
            MapWrapper.class.getClassLoader(),
            new Class<?>[]{DynamicObject.class},
            new MapInvocationHandler(map)
        );
    }

    public interface DynamicObject {
        Object getUserId();
        Object getUSERID();
        // 其他字段的 getter 方法
    }

    static class MapInvocationHandler implements InvocationHandler {
        private final Map<String, Object> map;

        public MapInvocationHandler(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String fieldName = method.getName();
            if (fieldName.startsWith("get")) {
                String key = fieldName.substring(3);
                return map.get(key);
            }
            return null;
        }
    }
}
