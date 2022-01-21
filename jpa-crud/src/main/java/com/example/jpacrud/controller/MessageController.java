package com.example.jpacrud.controller;

import com.example.jpacrud.commons.enums.ResultEnum;
import com.example.jpacrud.commons.utils.JsonUtils;
import com.example.jpacrud.commons.utils.MessageUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.jpacrud.commons.constant.I18nConstant.Exception.HTTP_PARAM_NOT_EXIST;

/**
 * @ClassName: MessageController @Author: amy @Description:
 *             MessageController @Date: 2021/7/1 @Version: 1.0
 */
@RestController
@RequestMapping("/api/multi")
@Slf4j
public class MessageController {

	@GetMapping("/lang")
	public JsonNode lang() {
		String sign = MessageUtils.get("login.sign");
		String ok = MessageUtils.get("login.ok");
		ObjectNode result = JsonUtils.object();
		result.put("sign", sign);
		result.put("ok", ok);
		return result;
	}

	@GetMapping("/error")
	public void error() {
		log.info(MessageUtils.get(HTTP_PARAM_NOT_EXIST));
	}

	@GetMapping("/enum")
	public void testEnums() {
		log.info("success:{},error:{}", ResultEnum.SUCCESS.getMessage(), ResultEnum.ERROR.getMessage());
	}

}
