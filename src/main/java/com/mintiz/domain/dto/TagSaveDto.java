package com.mintiz.domain.dto;

import com.mintiz.domain.Tag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagSaveDto {

    String tag_name;

    public Tag toEntity(){
        return Tag.builder()
                .tag_name(tag_name)
                .build();
    }
}
