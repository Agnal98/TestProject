package com.innobiz.orca.appconfig.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class MapperConfig {

    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<?, ?> stringTrimmer = new AbstractConverter<Object, Object>() {
            protected Object convert(Object source) {
                if (source instanceof String)
                    return ((String) source).trim();
                 else
                    return source;
            }
        };

        Converter<Timestamp, String>  toStringDate = new AbstractConverter<Timestamp, String>() {
            protected String convert(Timestamp source) {
                return source == null ? null : formatter.format(new Date(source.getTime()));
            }
        };
        modelMapper.addConverter(toStringDate);
        modelMapper.addConverter(stringTrimmer);
        return modelMapper;
    }
}
