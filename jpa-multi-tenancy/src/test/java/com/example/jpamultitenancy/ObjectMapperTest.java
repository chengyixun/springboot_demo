package com.example.jpamultitenancy;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.example.jpamultitenancy.tenant.entity.Blog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: ObjectMapperTest @Author: amy @Description: ObjectMapperTest @Date:
 * 2021/6/18 @Version: 1.0
 */
@Slf4j
public class ObjectMapperTest {

  @Test
  public void testWriteValueAsString() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    Blog blog = Blog.builder().id(1234L).content("context_1234").title("title_1234").build();
    String str = objectMapper.writeValueAsString(blog);
    // JsonUtil.format()
    log.info("str:{}", str);
  }

  @Test
  public void testReadValue() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    String token ="";
    LoginResult loginResult = objectMapper.readValue(token, LoginResult.class);

    System.out.println(loginResult);
  }

  @Test
  public void testValueToTree() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    String token ="";
    JsonNode jsonNode = objectMapper.valueToTree(token);

    System.out.println(jsonNode);
  }

  @Test
  public void test2() throws JsonProcessingException {
    String json = "{\"field1\":\"value1\",\"field2\":123,\"field3\":999.999}";
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(json);
    // objectMapper.readValue()
    JsonNode field1 = jsonNode.get("field1"); // get方法返回JsonNode类型表示字段。
    JsonNode field2 = jsonNode.get("field2");
    int field2Int = jsonNode.get("field2").asInt(); // 转换字段值至其他数据类型
  }

  @Test
  public void test3() {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode parentNode = objectMapper.createObjectNode();
    // put设置属性值为原始数据类型
    parentNode.put("field1", "value1");
    parentNode.put("field2", 123);
    parentNode.put("field3", 999.999);
    log.info("parentNode:{}", parentNode);
    ObjectNode childNode = objectMapper.createObjectNode();
    // set设置设置ObjectNode对象属性
    parentNode.set("child1", childNode);
    log.info("parentNode:{}", parentNode);
    log.info("childNode:{}", childNode);
  }

  @Test
  public void test4() {

    // 编码
    String s = "王禹";
    String base64String = Base64.encodeBase64String(s.getBytes());
    System.out.println("base64String 编码:" + base64String);

    // 解码
    byte[] bytes = Base64.decodeBase64(base64String);
    String str = null;
    try {
      str = new String(bytes, CharsetUtil.UTF_8);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    System.out.println("str 解密：" + str);
  }
}
