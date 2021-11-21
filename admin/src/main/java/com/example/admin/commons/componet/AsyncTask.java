package com.example.admin.commons.componet;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * {@link AsyncTask}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-03-24
 */
@Slf4j
@Component
public class AsyncTask {

    @Async
    public void dealNoReturnTask() {
        log.info(">>>Thread :{} ,deal NO return task start", Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(">>>Thread :{} ,deal NO return task end ,time:{}", Thread.currentThread().getName(),System.currentTimeMillis());
    }

    @Async
    public Future<String> dealHaveReturnTask(){
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("thread",Thread.currentThread().getName());
        result.put("time",System.currentTimeMillis());
        return new  AsyncResult<>(result.toJSONString());

    }
}
