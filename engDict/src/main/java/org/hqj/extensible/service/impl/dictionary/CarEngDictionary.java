package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.Dictionary;

import java.util.SortedMap;
import java.util.TreeMap;

public class CarEngDictionary implements Dictionary {
    private SortedMap<String, String> dict;

    public CarEngDictionary(){
        dict = new TreeMap<>();
        dict.put("window", "a car window.");
        dict.put("wheel", "car wheel.");
    }

    @Override
    public String getDefinition(String word) {
        return dict.get(word);
    }
}
