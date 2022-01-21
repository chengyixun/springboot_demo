package com.example.admin.fun;

/**
 * {@link Demo02LoggerLambdaTest}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-22
 */
public class Demo02LoggerLambdaTest {

	public static void main(String[] args) {
		String msgA = "Hello";
		String msgB = "World";
		String msgC = "Java";

		// lambda 延迟加载， MessageBuilder 接口就不会执行
		log(2, () -> {
			System.out.println("Lambda");
			return msgA + msgB + msgC;
		});
	}

	private static void log(int level, MessageBuilder builder) {
		if (level == 1) {
			System.out.println(builder.buildMessage());
		}
	}
}
