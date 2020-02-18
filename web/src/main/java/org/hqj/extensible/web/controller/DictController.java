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
    String definition(@RequestParam(name="word") String word){
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
    String hot(@RequestParam(name="word") String word){
        String result =  word + " : ";
        String definition = hotLoadDictionaryService.getDefinition(word);
        if(definition == null){
            result += " found no definition.";
        }else{
            result += definition;
        }
        return result;
    }

    @RequestMapping("/reload")
    String reload(){
        try{
            this.hotLoadDictionaryService.reload();
            return "Successfully reloaded !";
        }catch(Exception e){
            e.printStackTrace();
            return "Failed to reaload !";
        }
    }
}
