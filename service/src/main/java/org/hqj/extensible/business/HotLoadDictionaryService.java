package org.hqj.extensible.business;

import org.hqj.extensible.service.SPIHotLoadDictionaryService;

public class HotLoadDictionaryService {

    private SPIHotLoadDictionaryService spiHotLoadDictionaryService = SPIHotLoadDictionaryService.getInstance();

    public String getDefinition(String word, String dictName){
        return spiHotLoadDictionaryService.getDefinition(word, dictName);
    }

    public void reload() throws Exception {
        spiHotLoadDictionaryService.reload();
    }
}
