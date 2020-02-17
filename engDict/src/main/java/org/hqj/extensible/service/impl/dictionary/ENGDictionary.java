package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.Dictionary;

import java.util.SortedMap;
import java.util.TreeMap;

public class ENGDictionary implements Dictionary {


    private SortedMap<String, String> dict;

    public ENGDictionary(){
        this.dict = new TreeMap<>();

        dict.put("car", "an automobile.");
        dict.put("editor", "a person who edits.");
    }

    @Override
    public String getDefinition(String word) {
        return this.dict.get(word);
    }
}
