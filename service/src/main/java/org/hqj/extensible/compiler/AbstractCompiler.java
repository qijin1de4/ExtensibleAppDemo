package org.hqj.extensible.compiler;

import org.hqj.extensible.util.clz.ClzUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractCompiler implements Compiler {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("package\\s+([$_a-zA-Z)][$_a-zA-Z0-9\\.]*);");
    private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*");

    @Override
    public Class<?> compile(String source, ClassLoader clzLoader) {
        source = source.trim();
        Matcher matcher  = PACKAGE_PATTERN.matcher(source);
        String pkg = "";
        if(matcher.find()){
            pkg = matcher.group(1).trim();
        }

        matcher = CLASS_PATTERN.matcher(source);
        String clz = "";
        if(matcher.find()){
            clz = matcher.group(1).trim();
        } else {
          throw new IllegalArgumentException("No Class definition in this source : "+source);
        }

        String clzName = pkg != null && pkg.trim().length() > 0 ? pkg + "." + clz : clz;

        try{
            return Class.forName(clzName, true,  ClzUtil.getCallerClzLoader(getClass()));
        }catch( ClassNotFoundException e){
            if(!source.endsWith("}")){
                throw new IllegalStateException("The java code not endsWith \"}\", source: \n" + source + "\n");
            }else {
                try{
                    return doCompile(clzName, source);
                }catch(RuntimeException re){
                    throw re;
                }catch(Throwable t){
                    throw new IllegalStateException("Failed to compile class, cause: " + t.getMessage() + ", class: " + clzName + ", code: \n" + source + "\n, stack: " + ClzUtil.toString(t));
                }
            }
        }
    }

    protected abstract Class<?> doCompile(String name, String source) throws Throwable;
}
