package com.example.jpamultitenancy.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @ClassName: ThrowableUtil @Author: amy @Description: ThrowableUtil @Date:
 *             2021/6/16 @Version: 1.0
 */
public class ThrowableUtils {

	/**
	 * 获取堆栈信息
	 *
	 * @param throwable
	 * @return
	 */
	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		try (PrintWriter pw = new PrintWriter(sw)) {
			throwable.printStackTrace(pw);
			return sw.toString();
		}
	}
}
