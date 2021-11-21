package com.example.mongodb.common.constants;

/**
 * {@link Constants} common constants
 *
 * @author <a href="mailto:tangtongda@gmail.com">Tino.Tang</a>
 * @version ${project.version} - 2020/10/19
 */
public interface Constants {

  /** 空格 */
  String SPACE = " ";

  /** SQL查询正序 */
  String ASC = " asc";

  /** SQL查询倒叙 */
  String DESC = " desc";

  /** 冒号 */
  String COLON = ":";

  /** 分号 */
  String SEMICOLON = ";";

  /** 逗号 */
  String COMMA = ",";

  /** 下划线 */
  String BOTTOM_LINE = "_";

  /** 中横线 */
  String MIDDLE_LINE = "-";

  String WAVE = "~";

  /** common static key no */
  Integer NO = 0;

  /** common static key yes */
  Integer YES = 1;

  interface WXConstants {
    String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
    String CIPHER = "BC";
    String AES = "AES";
    String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  }
}
