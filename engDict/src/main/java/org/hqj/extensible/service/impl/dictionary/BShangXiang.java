package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.ShangXiang;

public class BShangXiang implements ShangXiang {

    @Override
    public String getName() {
        return "B";
    }

    @Override
    public String getData(String token) {
        return "B";
    }
}
