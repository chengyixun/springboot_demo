package com.example.admin;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @Author: wangyu
 * @Date: Created 2020-12-14 15:02
 * @Description:
 * @Modified By:
 */
@Data
public class Device {
	private String id;

	private String name;

	private String code;

	Device(String name) {
		this.name = name;
	}

	public static void main(String[] args) throws JsonProcessingException {
		Device device = new Device("Mpxxxx");
		device.setId("111");
		device.setCode("333");

		String string = device.toString();
		System.out.println(string);

		// @JsonProperty 搭配jackson @JSONField 搭配fastJson
//        Device device1 = JSONObject.parseObject(string, Device.class);
//        System.out.println(device1);

		ObjectMapper objectMapper = new ObjectMapper();
		String value = objectMapper.writeValueAsString(device);
		System.out.println("对象转为字符串：" + value);

		// device没有无参构造器，通过 定义 抽象类 DeviceMaxIn
		objectMapper.addMixIn(Device.class, DeviceMaxIn.class);
		Device readValue = objectMapper.readValue(value, Device.class);
		System.out.println("JSON字符串转为对象：" + readValue);

	}
}
