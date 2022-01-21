package com.example.admin.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName: CollectionsTest
 * @Author: amy
 * @Description: CollectionsTest
 * @Date: 2021/6/24
 * @Version: 1.0
 */
@Slf4j
public class CollectionsTest {

	@Test
	public void test1() {
		List<String> list1 = Lists.newArrayList();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		List<String> list2 = Lists.newArrayList();
		list2.add("a");
		list2.add("b");
		list2.add("d");
		// 2个集合的交集
		Collection retainAll = CollectionUtils.retainAll(list1, list2);
		System.out.println(retainAll);
		// 2个集合的并集
		Collection union = CollectionUtils.union(list1, list2);
		System.out.println(union);
		// 2个集合的差
		Collection subtract = CollectionUtils.subtract(list1, list2);
		System.out.println(subtract);
	}
}
