package com.example.admin.thread;

/**
 * @ClassName: GlobExceptionHandler @Author: amy @Description: GlobExceptionHandler @Date:
 * 2021/9/28 @Version: 1.0
 */
public class GlobExceptionHandler {

  private Object org;

  public static void main(String[] args) {
    //
    Thread thread = new Thread(new TestThread());
    thread.setName("Thread-xx");
    thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    thread.start();
  }
}

class TestThread implements Runnable {

  @Override
  public void run() {
    try {
      Thread.sleep(2000);
      System.out.println("start ... Exception");
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    throw new NullPointerException(); // 直接exception
  }
}
