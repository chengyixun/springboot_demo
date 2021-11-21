package com.example.admin.fun;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName: PersonSupportVO
 * @Author: amy
 * @Description: PersonSupportVO
 * @Date: 2021/8/22
 * @Version: 1.0
 */
@Data
@Builder
public class PersonSupportVO {

    private String personId;

    private String supportDate;

    private String supportUnitId;
}
