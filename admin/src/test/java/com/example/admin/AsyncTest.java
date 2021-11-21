package com.example.admin;

import com.example.admin.commons.componet.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.concurrent.Future;

/**
 * {@link AsyncTest}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AdminApplication.class)
@Slf4j
public class AsyncTest {

    @Autowired
    private AsyncTask asyncTask;

    @Test
    public void test1() {
        asyncTask.dealNoReturnTask();
        try {
            log.info(">>>begin to deal other Task!");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap  hashMap = new HashMap();
        hashMap.put("q","q");
    }

    @Test
    public void test2() throws Exception {
        Future<String> future = asyncTask.dealHaveReturnTask();
        log.info("begin to deal other Task!");
        while (true){
            if(future.isCancelled()){
                log.info("deal async task is Cancelled");
                break;
            }
            if(future.isDone()){
                log.info("deal async task is Done");
                log.info("return result is " + future.get());
                break;
            }
            log.info("wait async task to end ...");
            Thread.sleep(1000);
        }

    }
    @Test
    public void test(){


    }
}
