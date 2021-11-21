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

    String token =
        "{\"result\":true,\"errorCode\":\"Gaia_Sso_Success001\",\"errorMsg\":\"get token success\",\"data\":{\"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnTURwaGJ1d0NfRTNaQnZHR0FhTFhwUkY3TW5NVE5NdHVYbE14a1NJMEZNIn0.eyJqdGkiOiIxNzA1ZTg5Yi05ZGJmLTQzZmUtYTBmYy1kNjM3NjVjYjQ5YzMiLCJleHAiOjE2MjYwNzc5MjcsIm5iZiI6MCwiaWF0IjoxNjI0MzQ5OTI3LCJpc3MiOiJodHRwczovL3Nzby1xYS5nYWlhd29ya2ZvcmNlLmNvbS9hdXRoL3JlYWxtcy90ZXN0MSIsImF1ZCI6IndmbTQiLCJzdWIiOiI1MDNlZDJjZC00NjBiLTQ2ZTEtOGVjZi04Y2JhZDIwMzBhY2MiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ3Zm00IiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYzBkMjZlMTYtMDcyZi00YjAyLTljNTktZjEzZjY1MWJlYzQ2IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnt9LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJkZWZhdWx0TGFuZ3VhZ2UiOiJaSC1DTiIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0MV9zYSIsInRlbmFudCI6InRlc3QxIiwicGVyc29uX2lkIjoidGVzdDFfc2EifQ.FtJlb8x97hQt6TH1hXM19MCC6h9lAYHGpc_2OOAPvSibzZ57imeN7m8vohIfw6oTPRFn_eR285SX8FhBAICUvAzqxj-QllTg6vQwMgk7j3vIA4XjByEfZxYDSDfW9bBTzGh81YKwgM52Pyt4WUwYvMSU_ASbyMmMVj9dU4lXfkI\",\"expires_in\":1728000,\"refresh_expires_in\":2592000,\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnTURwaGJ1d0NfRTNaQnZHR0FhTFhwUkY3TW5NVE5NdHVYbE14a1NJMEZNIn0.eyJqdGkiOiJmZDRhNDM1Mi1mYTc5LTQ4N2YtYTc2ZS04NDQwYTBlYTc2NTIiLCJleHAiOjE2MjY5NDE5MjcsIm5iZiI6MCwiaWF0IjoxNjI0MzQ5OTI3LCJpc3MiOiJodHRwczovL3Nzby1xYS5nYWlhd29ya2ZvcmNlLmNvbS9hdXRoL3JlYWxtcy90ZXN0MSIsImF1ZCI6IndmbTQiLCJzdWIiOiI1MDNlZDJjZC00NjBiLTQ2ZTEtOGVjZi04Y2JhZDIwMzBhY2MiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoid2ZtNCIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImMwZDI2ZTE2LTA3MmYtNGIwMi05YzU5LWYxM2Y2NTFiZWM0NiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnt9LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUifQ.hvvD6oiOxhPAxPu25LneFr3o3WmKivxCoGqk3RxweuOJQ2Mrg16PWH9X8Lmfbj5ECc22i-pn8oixRIPbrXlbXbiqYv-ZSNOck5c82hUJ6R0fwgp78Q1YR7vzENbbHtyQU5yB5O9d9-Y0pmNNW0hdS0EWTLXAqFc3scFAG-ezjaQ\",\"token_type\":\"bearer\",\"not-before-policy\":0,\"session_state\":\"c0d26e16-072f-4b02-9c59-f13f651bec46\",\"scope\":\"email profile\"}}";

    LoginResult loginResult = objectMapper.readValue(token, LoginResult.class);

    System.out.println(loginResult);
  }

  @Test
  public void testValueToTree() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    String token =
        "{\"result\":true,\"errorCode\":\"Gaia_Sso_Success001\",\"errorMsg\":\"get token success\",\"data\":{\"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnTURwaGJ1d0NfRTNaQnZHR0FhTFhwUkY3TW5NVE5NdHVYbE14a1NJMEZNIn0.eyJqdGkiOiIxNzA1ZTg5Yi05ZGJmLTQzZmUtYTBmYy1kNjM3NjVjYjQ5YzMiLCJleHAiOjE2MjYwNzc5MjcsIm5iZiI6MCwiaWF0IjoxNjI0MzQ5OTI3LCJpc3MiOiJodHRwczovL3Nzby1xYS5nYWlhd29ya2ZvcmNlLmNvbS9hdXRoL3JlYWxtcy90ZXN0MSIsImF1ZCI6IndmbTQiLCJzdWIiOiI1MDNlZDJjZC00NjBiLTQ2ZTEtOGVjZi04Y2JhZDIwMzBhY2MiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJ3Zm00IiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYzBkMjZlMTYtMDcyZi00YjAyLTljNTktZjEzZjY1MWJlYzQ2IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnt9LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJkZWZhdWx0TGFuZ3VhZ2UiOiJaSC1DTiIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0MV9zYSIsInRlbmFudCI6InRlc3QxIiwicGVyc29uX2lkIjoidGVzdDFfc2EifQ.FtJlb8x97hQt6TH1hXM19MCC6h9lAYHGpc_2OOAPvSibzZ57imeN7m8vohIfw6oTPRFn_eR285SX8FhBAICUvAzqxj-QllTg6vQwMgk7j3vIA4XjByEfZxYDSDfW9bBTzGh81YKwgM52Pyt4WUwYvMSU_ASbyMmMVj9dU4lXfkI\",\"expires_in\":1728000,\"refresh_expires_in\":2592000,\"refresh_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJnTURwaGJ1d0NfRTNaQnZHR0FhTFhwUkY3TW5NVE5NdHVYbE14a1NJMEZNIn0.eyJqdGkiOiJmZDRhNDM1Mi1mYTc5LTQ4N2YtYTc2ZS04NDQwYTBlYTc2NTIiLCJleHAiOjE2MjY5NDE5MjcsIm5iZiI6MCwiaWF0IjoxNjI0MzQ5OTI3LCJpc3MiOiJodHRwczovL3Nzby1xYS5nYWlhd29ya2ZvcmNlLmNvbS9hdXRoL3JlYWxtcy90ZXN0MSIsImF1ZCI6IndmbTQiLCJzdWIiOiI1MDNlZDJjZC00NjBiLTQ2ZTEtOGVjZi04Y2JhZDIwMzBhY2MiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoid2ZtNCIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6ImMwZDI2ZTE2LTA3MmYtNGIwMi05YzU5LWYxM2Y2NTFiZWM0NiIsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnt9LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUifQ.hvvD6oiOxhPAxPu25LneFr3o3WmKivxCoGqk3RxweuOJQ2Mrg16PWH9X8Lmfbj5ECc22i-pn8oixRIPbrXlbXbiqYv-ZSNOck5c82hUJ6R0fwgp78Q1YR7vzENbbHtyQU5yB5O9d9-Y0pmNNW0hdS0EWTLXAqFc3scFAG-ezjaQ\",\"token_type\":\"bearer\",\"not-before-policy\":0,\"session_state\":\"c0d26e16-072f-4b02-9c59-f13f651bec46\",\"scope\":\"email profile\"}}";
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
