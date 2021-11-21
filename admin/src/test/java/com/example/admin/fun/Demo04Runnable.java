package com.example.admin.fun;

/**
 * {@link Demo04Runnable}
 *
 * @author <a href="mailto:liyaohui.wang@yunlsp.com">Liyaohui wang</a>
 * @version ${project.version} - 2021-04-22
 */
public class Demo04Runnable {

  public static void main(String[] args) {
    //
    startThread(() -> System.out.println("线程1"));
  }

  private static void startThread(Runnable runnable) {
    new Thread(runnable).start();
  }
}
