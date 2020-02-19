package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.ShangXiang;

public class OurShangXiang implements ShangXiang {

    @Override
    public String getName() {
        return "Our";
    }

    @Override
    public String getData(String token) {
        return "A";
    }
}
