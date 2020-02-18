package org.hqj.extensible.service;

import org.hqj.extensible.spi.dictionary.Dictionary;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class DictionaryService {

    private static DictionaryService service;

    private ServiceLoader<Dictionary> serviceLoader;

    private DictionaryService(){
        this.serviceLoader = ServiceLoader.load(Dictionary.class);
    }

    public synchronized static DictionaryService getInstance(){
        if(service == null){
            service = new DictionaryService();
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
