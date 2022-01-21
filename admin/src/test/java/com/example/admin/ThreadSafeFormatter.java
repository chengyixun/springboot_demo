package com.example.admin;

import java.text.SimpleDateFormat;

/**
 * {@link ThreadSafeFormatter}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-19
 */
public class ThreadSafeFormatter {

	public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

}
