package org.hqj.extensible.compiler;

public interface Compiler {

    /**
     * Compile java source code.
     * @param source
     * @param clzLoader
     * @return
     */
    Class<?> compile(String source, ClassLoader clzLoader);
}
