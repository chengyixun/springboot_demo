package com.example.admin.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName: OutOfMemoryGCLimitExceed @Author: amy @Description: @Date:
 *             2021/9/28 @Version: 1.0
 */
public class OutOfMemoryGCLimitExceed {

	public static void main(String[] args) {
		//
		addRandomDataToMap();
	}

	public static void addRandomDataToMap() {
		Map<Integer, String> map = new HashMap<>();
		Random r = new Random();
		while (true) {
			map.put(r.nextInt(), String.valueOf(r.nextInt()));
		}
	}
}
