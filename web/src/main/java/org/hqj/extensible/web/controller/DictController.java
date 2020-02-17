package org.hqj.extensible.web.controller;

import org.hqj.extensible.service.DictionaryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictController {

    private DictionaryService dictionaryService = DictionaryService.getInstance();

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
}
