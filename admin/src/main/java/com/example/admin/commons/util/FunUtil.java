package com.example.admin.commons.util;

/**
 * {@link FunUtil}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-22
 */
public class FunUtil {

	private static boolean param = false;

	private FunUtil() {
	}

	public static class Builder<R> {

		private R result;

		public Builder() {

		}

		public Builder(R result) {
			this.result = result;
		}

		public static <R> Builder<R> condition(boolean condition) {
			param = condition;
			return new Builder<>();
		}
	}
}
