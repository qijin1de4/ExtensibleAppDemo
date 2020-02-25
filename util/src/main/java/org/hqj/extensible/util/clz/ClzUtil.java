package org.hqj.extensible.util.clz;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class ClzUtil {

    public static ClassLoader getCallerClzLoader(Class<?> callerClz){
        return callerClz.getClassLoader();
    }

    public static String toString(Throwable e) {
        StringWriter w = new StringWriter();
        PrintWriter p = new PrintWriter(w);
        p.print(e.getClass().getName() + ": ");
        if (e.getMessage() != null) {
            p.print(e.getMessage() + "\n");
        }
        p.println();
        try {
            e.printStackTrace(p);
            return w.toString();
        } finally {
            p.close();
        }
    }

    private static Class<?> arrayForName(String clzName) throws ClassNotFoundException {
        return Class.forName(
                clzName.endsWith("[]") ?
                        "[L" + clzName.substring(0, clzName.length() -2)
                        : clzName, true, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> forName(String clzName){
        try{
            return clzForName(clzName);
        }catch(ClassNotFoundException e){
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static Class<?> clzForName(String clzName) throws ClassNotFoundException {

        switch(clzName) {
            case "boolean":
                return boolean.class;
            case "byte":
                return byte.class;
            case "char":
                return char.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "boolean[]":
                return boolean[].class;
            case "byte[]":
                return byte[].class;
            case "char[]":
                return char[].class;
            case "short[]":
                return short[].class;
            case "int[]":
                return int[].class;
            case "long[]":
                return long[].class;
            case "float[]":
                return float[].class;
            case "double[]":
                return double[].class;
            default:
        }

        try{
            return arrayForName(clzName);
        }catch(ClassNotFoundException e){
            // try to load from java.lang package
            if(clzName.indexOf(".") == -1){
                try{
                    return arrayForName("java.lang." + clzName);
                }catch(ClassNotFoundException e1){
                    // ignore this exception. let the original one to be thrown.
                }
            }
            throw e;
        }
    }

    public static Class<?> forName (String[] pkgs, String clzName) {
        try {
            return clzForName(clzName);
        } catch(ClassNotFoundException e){
            if(pkgs != null && pkgs.length > 0) {
                for(String pkg : pkgs) {
                    try{
                        return clzForName(pkgs + "." + clzName);
                    }catch(ClassNotFoundException e1) {
                        // ignore
                    }
                }
            }
            throw new IllegalStateException(e.getMessage() , e);
        }
    }

    public static Class<?> getBoxedClz(Class<?> type) {
        if (type == boolean.class) {
            return Boolean.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == double.class) {
            return Double.class;
        } else {
            return type;
        }
    }
}
