package com.syl.convertor;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by ainsc on 2017/8/2.
 */
public class TrimConvetor implements Converter<String,String> {
    public String convert(String s) {

        if (s!=null&&s.length()>0) {
            return s;
        }

        return null;
    }
}
