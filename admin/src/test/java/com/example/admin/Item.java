package com.example.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-23 11:37
 * @Description:
 * @Modified By:
 */
@Data
@AllArgsConstructor
public class Item {
	private String name;

	private Integer num;

	private BigDecimal price;

	public Item(Integer num, BigDecimal price) {
		this.num = num;
		this.price = price;
	}

	public static void main(String[] args) {
		List<Item> items = Arrays.asList(new Item(10, new BigDecimal("9.99")),
				new Item("banana", 20, new BigDecimal("19.99")), new Item("orang", 10, new BigDecimal("29.99")),
				new Item("watermelon", 10, new BigDecimal("29.99")), new Item("papaya", 20, new BigDecimal("9.99")),
				new Item("apple", 10, new BigDecimal("9.99")), new Item("banana", 10, new BigDecimal("19.99")),
				new Item("apple", 20, new BigDecimal("9.99")));

		// Map<BigDecimal, List<Item>> collect =
		// items.stream().collect(Collectors.groupingBy(Item::getPrice));
		// System.out.println(collect);

	}

}
