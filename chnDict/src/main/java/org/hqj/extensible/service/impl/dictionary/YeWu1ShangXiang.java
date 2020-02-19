package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.ShangXiang;

public class YeWu1ShangXiang implements ShangXiang {

    @Override
    public String getName() {
        return "YeWu1";
    }

    @Override
    public String getData(String token) {
        return "B";
    }
}
