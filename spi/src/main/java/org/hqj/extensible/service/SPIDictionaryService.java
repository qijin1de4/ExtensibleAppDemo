package org.hqj.extensible.service;

import org.hqj.extensible.spi.dictionary.Dictionary;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class SPIDictionaryService {

    private static SPIDictionaryService service;

    private ServiceLoader<Dictionary> serviceLoader;

    private SPIDictionaryService(){
        this.serviceLoader = ServiceLoader.load(Dictionary.class);
    }

    public synchronized static SPIDictionaryService getInstance(){
        if(service == null){
            service = new SPIDictionaryService();
        }
        return service;
    }

    public String getDefinition(String word){

        String definition = null;

        try{
            serviceLoader.reload();

            Iterator<Dictionary> dictionaries = serviceLoader.iterator();
            while(definition == null  && dictionaries.hasNext()){
                definition = dictionaries.next().getDefinition(word);
            }
        } catch(ServiceConfigurationError e){
            definition = null;
            e.printStackTrace();
        }

        return definition;
    }
}
