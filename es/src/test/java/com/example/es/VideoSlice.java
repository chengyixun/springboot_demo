package com.example.es;

import com.example.es.commons.annotation.Entity;
import com.example.es.commons.param.CrudEntity;
import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 17:19
 * @Description:
 * @Modified By:
 */
@Data
@Entity(index = "viid", type = "videoSlice", alias = "viid")
public class VideoSlice  {

    @JestId
    private String id;

    private String code;
}
