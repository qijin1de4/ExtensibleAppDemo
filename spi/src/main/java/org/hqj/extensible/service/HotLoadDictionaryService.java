package org.hqj.extensible.service;

import org.hqj.extensible.spi.dictionary.Dictionary;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.spi.FileSystemProvider;
import java.text.MessageFormat;
import java.util.*;

public class HotLoadDictionaryService {

    private static HotLoadDictionaryService service;

    private ServiceLoader<Dictionary> serviceLoader;

    private HotLoadDictionaryService(){
        this.serviceLoader = ServiceLoader.load(Dictionary.class);
    }

    public synchronized static HotLoadDictionaryService getInstance(){
        if(service == null){
            service = new HotLoadDictionaryService();
        }
        return service;
    }

    public String getDefinition(String word, String dictName){
        String definition = null;
        try{
            //serviceLoader.reload();
            Iterator<Dictionary> dictionaries = serviceLoader.iterator();
            while(definition == null  && dictionaries.hasNext()){
                Dictionary dictionary = dictionaries.next();
                if(dictName.equals(dictionary.getName())){
                    definition = dictionary.getDefinition(word);
                }
            }
        } catch(ServiceConfigurationError e){
            definition = null;
            e.printStackTrace();
        }
        return definition;
    }

    public void reload() throws IOException, ClassNotFoundException {
        Path jarDir = Paths.get(new File("/Users/huqijin/tmp/").toURI());
        URLClassLoader urlClzLoader;
        List<URL>  jarURLs = new ArrayList<>();
        FileSystemProvider fileSystemProvider = getZipFileSystemProvider();
        List<String> implsToLoad = new ArrayList<>();

        if(Files.exists(jarDir, LinkOption.NOFOLLOW_LINKS)){
            List<Path> jarFiles = listJars(jarDir);

            for(Path path : jarFiles){
                info("LOCATE JAR : " + path.toFile().getName());
                jarURLs.add(path.toUri().toURL());
                FileSystem fs = fileSystemProvider.newFileSystem(path, new HashMap<String, Object>());
                Path serviceDir = fs.getPath("/META-INF", "services");
                info("SCANNING SERVICES.");

                if(Files.exists(serviceDir)){
                    DirectoryStream<Path> serviceListings = Files.newDirectoryStream(serviceDir);
                    for(Path px : serviceListings){
                        List<String> services = Files.readAllLines(px, Charset.forName("UTF-8"));
                        info(MessageFormat.format("SERVICE FOUND : {0}", Arrays.toString(services.toArray())));
                        implsToLoad.addAll(services);
                    }
                }
            }

            urlClzLoader = new URLClassLoader(jarURLs.toArray(new URL[jarURLs.size()]));

            load(implsToLoad, urlClzLoader);

            serviceLoader = ServiceLoader.load(Dictionary.class, urlClzLoader);

            Iterator<Dictionary> iterator = serviceLoader.iterator();
            while(iterator.hasNext()){
                info(iterator.next().getClass().getName());
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
            info(MessageFormat.format("LOAD CLASS {0}", clzName));
            Class<?> clzz = classLoader.loadClass(clzName);
            info(MessageFormat.format("CLASS {0} LOADED", clzz.getName()));
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
