package org.hqj.extensible.service;

import org.hqj.extensible.spi.dictionary.ShangXiang;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.spi.FileSystemProvider;
import java.text.MessageFormat;
import java.util.*;

public class ShangXiangService {

    private static ShangXiangService service;

    private ServiceLoader<ShangXiang> serviceLoader;

    private ShangXiangService(){
        this.serviceLoader = ServiceLoader.load(ShangXiang.class);
    }

    public synchronized static ShangXiangService getInstance(){
        if(service == null){
            service = new ShangXiangService();
        }
        return service;
    }

    public String getData(String token){
        String data = null;
        try{
            //serviceLoader.reload();
            Iterator<ShangXiang> iterator = serviceLoader.iterator();
            while(iterator.hasNext()){
                ShangXiang shangXiang = iterator.next();
                if(token.equals(shangXiang.getName())){
                    data = shangXiang.getData(token);
                }
            }
        } catch(ServiceConfigurationError e){
            data = null;
            e.printStackTrace();
        }
        return data;
    }

    public void reload() throws IOException, ClassNotFoundException {
        Path jarDir = Paths.get(new File("/Users/huqijin/tmp/").toURI());
        URLClassLoader urlClzLoader;
        List<URL> jarURLs = new ArrayList<>();
        FileSystemProvider fileSystemProvider = getZipFileSystemProvider();
        List<String> implsToLoad = new ArrayList<>();

        if(Files.exists(jarDir, LinkOption.NOFOLLOW_LINKS)){
            List<Path> jarFiles = listJars(jarDir);

            for(Path path : jarFiles){
                info("定位到 JAR文件 : " + path.toFile().getName());
                jarURLs.add(path.toUri().toURL());
                FileSystem fs = fileSystemProvider.newFileSystem(path, new HashMap<String, Object>());
                Path serviceDir = fs.getPath("/META-INF", "services");
                info("开始扫描服务实现类。");

                if(Files.exists(serviceDir)){
                    DirectoryStream<Path> serviceListings = Files.newDirectoryStream(serviceDir);
                    for(Path px : serviceListings){
                        List<String> services = Files.readAllLines(px, Charset.forName("UTF-8"));
                        info(MessageFormat.format("找到服务实现 : {0}", Arrays.toString(services.toArray())));
                        implsToLoad.addAll(services);
                    }
                }
            }

            urlClzLoader = new URLClassLoader(jarURLs.toArray(new URL[jarURLs.size()]));

            //load(implsToLoad, urlClzLoader);

            serviceLoader = ServiceLoader.load(ShangXiang.class, urlClzLoader);

            Iterator<ShangXiang> iterator = serviceLoader.iterator();
            while(iterator.hasNext()){
                info("加载服务实现类："+iterator.next().getClass().getName());
            }
        }

    }

    private List<Path> listJars(Path path) throws IOException {
        List<Path> jars = new ArrayList<>();
        DirectoryStream<Path> ds = Files.newDirectoryStream(path, "*.jar");
        for(Path filePath : ds){
            if(!Files.isDirectory(filePath)){
                jars.add(filePath);
            }
        }
        return  jars;
    }

    private void load(final List<String> clzNames, final ClassLoader classLoader) throws IOException, ClassNotFoundException {
        for(String clzName : clzNames){
            info(MessageFormat.format("开始加载类： {0}", clzName));
            Class<?> clzz = classLoader.loadClass(clzName);
            info(MessageFormat.format("类: {0} 加载完毕。", clzz.getName()));
        }
    }

    private static FileSystemProvider getZipFileSystemProvider(){
        for(FileSystemProvider provider : FileSystemProvider.installedProviders()){
            if("jar".equals(provider.getScheme())){
                return  provider;
            }
        }
        return null;
    }

    private void info(String msg ){
        System.out.println(msg);
    }
}
