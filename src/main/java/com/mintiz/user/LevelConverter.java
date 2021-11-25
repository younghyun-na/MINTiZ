package com.mintiz.user;

import com.mintiz.domain.Level;
import org.springframework.stereotype.Component;

import javax.persistence.Convert;
import java.lang.annotation.Annotation;

/*
// 전달받은 파라미터를 Level 타입으로 변환해주는 Converter
@Component
public class LevelConverter implements Converter<Integer, Level> {

    public Level convert(Integer source) {
        return Level.convert(source);
    }
}
*/