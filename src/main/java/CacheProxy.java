import java.io.IOException;
import java.lang.reflect.Proxy;

public class CacheProxy {
    public CacheProxy() {
    }

    public <T, E extends T> T cache(E e) throws IOException, ClassNotFoundException {
        return (T) Proxy.newProxyInstance(e.getClass().getClassLoader(), e.getClass().getInterfaces() , new MyHandler(e));
    }
}