package com.example.mybatispluscrud.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @ClassName: CourseExportTemplate @Author: amy @Description: CourseExportTemplate @Date:
 * 2021/11/9 @Version: 1.0
 */
@Data
@ContentRowHeight(20)
@HeadRowHeight(20)
@ColumnWidth(15)
public class CourseExportTemplate {

  /** 课程代码 */
  @ExcelProperty(value = "课程代码", index = 0)
  private String code;

  /** 课程名称 */
  @ExcelProperty(value = "课程名称", index = 1)
  private String name;

  /** 课程类别 */
  @ExcelProperty(value = "课程类别", index = 2)
  private String courseCategory;

  /** 课程类型 */
  @ExcelProperty(value = "课程类型", index = 3)
  private String courseType;

  /** 课程性质 */
  @ExcelProperty(value = "课程性质", index = 4)
  private String courseNature;

  /** 学院 */
  @ExcelProperty(value = "学院", index = 5)
  private String academyName;

  /** 学时 */
  @ExcelProperty(value = "学时", index = 6)
  private String classHour;

  /** 学分 */
  @ExcelProperty(value = "学分", index = 7)
  private String classPoint;

  /** 课程分数 */
  @ExcelProperty(value = "课程分数", index = 8)
  private String score;

  /** 错误原因 */
  @ExcelProperty(value = "错误原因", index = 9)
  private String failReason;
}
