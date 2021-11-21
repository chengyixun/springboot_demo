package com.example.mybatisplus.demo;

import lombok.Builder;
import lombok.Data;

/**
 * {@link AddPrice}  续重
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-19
 */
@Data
@Builder
public class AddPrice {

    private Integer begin;
    private Integer end;
    private Long createTime;
}
