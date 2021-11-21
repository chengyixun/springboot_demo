package com.example.es;

import com.example.es.entity.Article;
import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @Author: wangyu
 * @Date: Created 2021-01-07 16:56
 * @Description:
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EsAutoConfiguration.class)
@Slf4j
public class ArticleTest {

    @Autowired
    private JestClient jestClient;

    @Test
    public void test() {
        Article article = Article.builder()
                .id(2L)
                .author("xxx")
                .content("swduieuef")
                .summary("sum")
                .pv(100L)
                .build();
        Index index = new Index.Builder(article).index("blog").type("article").build();
        try {
            DocumentResult execute = jestClient.execute(index);
            String id = execute.getId();
            System.out.println("id:" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testSearch(){

        String json = "{\n" + "    \"query\" : {\n" + "        \"match\" : {\n"
                + "            \"content\" : \"spring\"\n" + "        }\n"
                + "    }\n" + "}";

        //构建搜索
        Search search = new Search.Builder(json).addIndex("blog").addType("article").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }




}
