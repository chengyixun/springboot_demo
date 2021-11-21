package com.example.admin;

import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;

/** @ClassName: ToolUtil @Author: amy @Description: ToolUtil @Date: 2021/9/26 @Version: 1.0 */
public class ToolUtil {

  private static final String FILE_CODE_NOT_FOUND = "admin.file.code.not.found";

  private static final String EXCEL_TEMPLATE_NOT_FOUND = "admin.excel.template.not.found";

  private static final String ACCESSLOG_MODULE_FILE = "admin.accesslog.module.file";

  public static Map<String, Object> beanToMap(Object object) {
    Map<String, Object> map = Maps.newHashMap();
    Field[] declaredFields = object.getClass().getDeclaredFields();
    try {
      for (Field field : declaredFields) {
      //  field.setAccessible(true);
        map.put(field.getName(), field.get(object));
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return map;
  }

  public static void main(String[] args) {
    //

    Map<String, Object> map = beanToMap(ToolUtil.class);
    System.out.println(map);
  }
}
