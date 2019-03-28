import CacheAnnotation.Cache;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MyHandler implements InvocationHandler, Serializable {
    private Object object;
    private Map<Args, Object> cache = new HashMap<>();

    public MyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws InvocationTargetException, IllegalAccessException, IOException {
        if (!method.isAnnotationPresent(Cache.class)) return method.invoke(object, objects);
        else {
            Args args = new Args(method, objects);
            Cache methodAnnotation = method.getAnnotation(Cache.class);
            switch (methodAnnotation.cacheType()) {
                case IN_MEMORY:
                    if (cache.containsKey(args)) {
                        System.out.println("Result from memory");
                        return cache.get(args);
                    } else {
                        Object result = method.invoke(object, objects);
                        cache.put(args, result);
                        return result;
                    }
                case FILE:
                    try {
                        cache = CacheSerialazier.deSerialize(method.getName());
                    } catch (ClassNotFoundException | IOException e) {
                        /*ignore*/
                    }

                    if (cache.containsKey(args)) {
                        System.out.println("Result from file");
                        return cache.get(args);
                    } else {
                        Object result = method.invoke(object, objects);
                        cache.put(args, result);
                        CacheSerialazier.serialize(cache, method.getName());
                        return result;
                    }
            }
            return method.invoke(object, objects);
        }
    }
}

