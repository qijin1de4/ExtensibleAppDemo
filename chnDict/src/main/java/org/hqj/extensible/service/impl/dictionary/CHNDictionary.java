package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.Dictionary;

import java.util.SortedMap;
import java.util.TreeMap;

public class CHNDictionary  implements Dictionary {

    private SortedMap<String, String> dict;

    public CHNDictionary(){
        this.dict = new TreeMap<>();
        dict.put("car", "普遍指由内燃机电机等驱动的交通工具。xxx");
        dict.put("editor", "在报社，新闻网站等从事文字编辑工作的人。");
    }

    @Override
    public String getDefinition(String word) {
        return dict.get(word);
    }

    @Override
    public String getName() {
        return "英汉字典";
    }
}

