package org.hqj.extensible.service.impl.dictionary;

import org.hqj.extensible.spi.dictionary.ShangXiang;

public class AShangXiang implements ShangXiang {

    @Override
    public String getName() {
        return "A";
    }

    @Override
    public String getData(String token) {
        return "A";
    }
}
