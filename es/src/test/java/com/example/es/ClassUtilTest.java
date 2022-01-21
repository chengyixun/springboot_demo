package com.example.es;

import com.example.es.commons.annotation.Entity;
import com.example.es.commons.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-13 17:22
 * @Description:
 * @Modified By:
 */
@Slf4j
public class ClassUtilTest {

	public static void main(String[] args) {
		Entity annotation = ClassUtil.getAnnotation(VideoSlice.class, Entity.class);
		System.out.println(annotation);
	}
}
