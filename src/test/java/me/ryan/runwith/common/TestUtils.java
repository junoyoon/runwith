package me.ryan.runwith.common;

import me.ryan.runwith.annotation.Repeat;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class TestUtils {

    public static int getRepeatCount(Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        return repeat == null ? 1 : repeat.value();
    }

    public static boolean isRepeatable(Method method) {
        return method.getAnnotation(Repeat.class) != null;
    }

    public static long getRepeatTimeout(Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        return repeat == null ? 0 : Math.max(0, repeat.timeout());
    }

    public static long getTestTimeout(FrameworkMethod frameworkMethod) {
        Test test = frameworkMethod.getAnnotation(Test.class);
        return test == null ? 0 : Math.max(0, test.timeout());
    }

    public static String[] getRepeatParams(Method method) {
        Repeat repeat = method.getAnnotation(Repeat.class);
        return repeat == null ? new String[]{} : repeat.params();
    }

    public static Object[] getParamArgs(Method method, String param) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        param = param.replaceAll(" ", "");
        String[] paramArray = param.split(",");
        return makeParamObjectFromParamArray(parameterTypes, paramArray);
    }

    private static Object[] makeParamObjectFromParamArray(Class<?>[] parameterTypes, String[] paramArray) {
        Object[] result = new Object[paramArray.length];
        for (int i = 0; i < paramArray.length; i++) {
            result[i] = convertTypeFromParamArray(parameterTypes[i], paramArray[i]);
        }
        return result;
    }

    private static Object convertTypeFromParamArray(Class<?> parameterType, String value) {
        if (int.class.equals(parameterType) || Integer.class.equals(parameterType)) {
            return Integer.parseInt(value);
        } else if (long.class.equals(parameterType) || Long.class.equals(parameterType)) {
            return Long.parseLong(value);
        } else if (short.class.equals(parameterType) || Short.class.equals(parameterType)) {
            return Short.parseShort(value);
        } else if (double.class.equals(parameterType) || Double.class.equals(parameterType)) {
            return Double.parseDouble(value);
        } else if (float.class.equals(parameterType) || Float.class.equals(parameterType)) {
            return Float.parseFloat(value);
        } else if (char.class.equals(parameterType) || Character.class.equals(parameterType)) {
            return value.charAt(0);
        } else if (boolean.class.equals(parameterType) || Boolean.class.equals(parameterType)) {
            return Boolean.parseBoolean(value);
        } else if (byte.class.equals(parameterType) || Byte.class.equals(parameterType)) {
            return Byte.parseByte(value);
        } else if (BigDecimal.class.equals(parameterType)) {
            return new BigDecimal(value);
        } else if (String.class.equals(parameterType)) {
            return value;
        }
        throw new IllegalArgumentException(String.format("Parameter type (%s) cannot be handled!" +
                " Only primitive types, BigDecimals and Strings can be used.", parameterType));
    }
}
