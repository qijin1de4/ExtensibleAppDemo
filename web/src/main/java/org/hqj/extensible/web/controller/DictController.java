package org.hqj.extensible.web.controller;

import org.hqj.extensible.business.HotLoadDictionaryService;
import org.hqj.extensible.business.ShangXiangService;
import org.hqj.extensible.web.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictController {


    private HotLoadDictionaryService hotLoadDictionaryService = new HotLoadDictionaryService();

    private ShangXiangService shangXiangService = new ShangXiangService();


    @RequestMapping("/hot")
    @ResponseBody
    public Result hot(@RequestParam(name="word", required = false) String word, @RequestParam(name="dictName", required = false) String dictName){
        Result result = new Result();
        result.setTimeStamp(System.currentTimeMillis());

        if(StringUtils.isEmpty(word) || !word.matches("[a-zA-Z]+")){
            result.setErrorCode(2);
            result.setMessage("非法参数. 目标词语为空或者为非英语词汇。");
            return result;
        }

        if(StringUtils.isEmpty(dictName)){
            result.setErrorCode(3);
            result.setMessage("非法参数. 未指定字典！");
            return result;
        }

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
            this.shangXiangService.reload();
            return "重新加载成功!";
        }catch(Exception e){
            e.printStackTrace();
            return "重新加载失败!";
        }
    }

    @RequestMapping("/shangxiang")
    public String shangxiang(@RequestParam(name = "token", required = false) String token){
        try{
            String data = this.shangXiangService.getData(token);
            return data;
        }catch(Exception e){
            e.printStackTrace();
            return "error!";
        }
    }
}
