package org.hqj.extensible.web.controller;

import org.hqj.extensible.service.DictionaryService;
import org.hqj.extensible.service.HotLoadDictionaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictController {

    private DictionaryService dictionaryService = DictionaryService.getInstance();

    private HotLoadDictionaryService hotLoadDictionaryService = HotLoadDictionaryService.getInstance();

    @RequestMapping("/definition")
    public String definition(@RequestParam(name="word") String word){
        String result =  word + " : ";
        String definition = dictionaryService.getDefinition(word);
        if(definition == null){
            result += " found no definition.";
        }else{
            result += definition;
        }
        return result;
    }

    @RequestMapping("/hot")
    public String hot(@RequestParam(name="word") String word, @RequestParam(name="dictName") String dictName){
        String result =  word + " : ";
        String definition = hotLoadDictionaryService.getDefinition(word, dictName);
        if(definition == null){
            result += " 未找到该词语的解释！";
        }else{
            result += definition;
        }
        return result;
    }

    @RequestMapping("/reload")
    public String reload(){
        try{
            this.hotLoadDictionaryService.reload();
            return "重新加载成功!";
        }catch(Exception e){
            e.printStackTrace();
            return "重新加载失败!";
        }
    }
}
