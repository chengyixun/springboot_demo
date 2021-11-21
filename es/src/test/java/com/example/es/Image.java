package com.example.es;

import com.example.es.commons.annotation.Entity;
import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 17:21
 * @Description:
 * @Modified By:
 */
@Data
@Entity(index = "viid",type = "image",alias = "viid")
public class Image {

    @JestId
    private String id;

    private String code;

}
