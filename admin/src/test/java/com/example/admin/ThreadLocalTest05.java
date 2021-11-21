package com.example.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link ThreadLocalTest05}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-19
 */
public class ThreadLocalTest05 {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            int j = i;
            executorService.execute(() -> {
                String date = dataToStr(j * 1000);
                // 从结果中可以看出是线程安全的，时间没有重复的。
                System.out.println(date);
            });

        }
        executorService.shutdown();

    }

    public static String dataToStr(int millisSeconds) {
        Date date = new Date(millisSeconds);
        SimpleDateFormat sdf = ThreadSafeFormatter.dateFormatThreadLocal.get();
        return sdf.format(date);
    }


}
