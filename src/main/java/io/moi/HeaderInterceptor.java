package io.moi;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

public class HeaderInterceptor {

    private static String HEADER_KEY;
    private static String HEADER_VALUE;

    /*
      Set header key/value pair:
      Set to env variable if found, set default kv pair otherwise
     */
    static {
        HEADER_KEY = Optional.ofNullable(System.getenv("HEADER_KEY")).orElse("X-Instrumented-By");
        HEADER_VALUE = Optional.ofNullable(System.getenv("HEADER_VALUE")).orElse("aCompagny");
    }

    /*
     * Interceptor:
     * Add a additional call to 'setHeader' method with custom header key/value
     */
    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> zuper, @AllArguments Object[] allArguments) throws Exception {
        try {
            if (allArguments[1].getClass().getName().equals("org.apache.catalina.connector.Response")) {
                Object obj = allArguments[1];
                Class<?> clazz = obj.getClass();
                Method setHeaderMethod = clazz.getDeclaredMethod("setHeader", String.class, String.class);
                setHeaderMethod.invoke(obj, HEADER_KEY, HEADER_VALUE);
            }
        } catch (Exception e) {
            System.out.println("[Custom-Header Agent]: failed to intercept method '" + method.getName() + "': " + e.getMessage());
        }
        return zuper.call();
    }
}
