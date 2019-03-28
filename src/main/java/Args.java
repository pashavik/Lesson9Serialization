
import CacheAnnotation.Cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Args implements Serializable {
    private final String method;
    private List<Object> objects = new ArrayList<>();

    public Args(Method method, Object[] objects) {
        this.method = method.getName();
        Class[] identityBy = method.getAnnotation(Cache.class).identityBy();
        if (identityBy.length == 0) {
            this.objects = Arrays.asList(objects);
        } else {
            int cursor = 0;
            for (int i = 0; i < objects.length; i++) {
                if (objects[i].getClass() == identityBy[cursor]) {
                    cursor++;
                    this.objects.add(objects[i]);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Args)) return false;

        Args args = (Args) o;

        if (!method.equals(args.method)) return false;
        return objects != null ? objects.equals(args.objects) : args.objects == null;
    }

    @Override
    public int hashCode() {
        int result = method.hashCode();
        result = 31 * result + (objects != null ? objects.hashCode() : 0);
        return result;
    }
}