package org.hqj.extensible.business;

import org.hqj.extensible.service.SPIShangXiangService;

public class ShangXiangService {

    SPIShangXiangService spiShangXiangService = SPIShangXiangService.getInstance();

    private String getData(){
        return "A";
    }

    public String getData(String token){
        String spiData = spiShangXiangService.getData(token);
        if(spiData != null){
            return getData() + spiData;
        }
        return getData();
    }

    public void reload() throws Exception {
        spiShangXiangService.reload();
    }
}
