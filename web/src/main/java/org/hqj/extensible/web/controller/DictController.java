package org.hqj.extensible.web.controller;

import org.hqj.extensible.service.DictionaryService;
import org.hqj.extensible.service.HotLoadDictionaryService;
import org.hqj.extensible.web.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public Result hot(@RequestParam(name="word") String word, @RequestParam(name="dictName") String dictName){
        Result result = new Result();
        result.setTimeStamp(System.currentTimeMillis());
        result.setMessage("查询成功！");
        String prefix =  word + " : ";
        try{
            String definition = hotLoadDictionaryService.getDefinition(word, dictName);
            if(definition == null){
                result.setDefinition("");
                result.setMessage(" 未找到该词语的解释！");
                result.setErrorCode(1);
            }else{
                result.setDefinition(prefix + definition);
            }
        }catch(Exception e){
            result.setErrorCode(-1);
            result.setMessage("服务器出错！请联系管理员。出错信息:"+e.getMessage());
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
