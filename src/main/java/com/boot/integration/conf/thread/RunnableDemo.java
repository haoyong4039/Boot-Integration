package com.boot.integration.conf.thread;

public class RunnableDemo
{
    public static void main(String[] args)
    {
        MyRunnable mr_A = new MyRunnable("线程A");
        MyRunnable mr_B = new MyRunnable("线程B");
        MyRunnable mr_C = new MyRunnable("线程C");
        Thread t_A = new Thread(mr_A);
        Thread t_B = new Thread(mr_B);
        Thread t_C = new Thread(mr_C);

        //优先级
//        t_A.setPriority(Thread.MIN_PRIORITY);
//        t_B.setPriority(Thread.MAX_PRIORITY);
//        t_C.setPriority(Thread.NORM_PRIORITY);

        t_A.start();
        t_B.start();
        t_C.start();
    }
}

class MyRunnable implements Runnable
{

    private String name;

    public MyRunnable(String name)
    {
        this.name = name;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 10; i++)
        {
            System.out.println(name + "执行第" + i + "次");

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            if (i== 5)
            {
                System.out.print("线程礼让：");
                Thread.currentThread().yield();
            }
        }
    }
}