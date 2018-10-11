package com.boot.integration.conf.thread;

public class ThreadDemo
{
    public static void main(String[] args)
    {
        MyThread mt_A = new MyThread("线程A");
        MyThread mt_B = new MyThread("线程B");

        mt_A.start();
        mt_B.start();
    }
}

class MyThread extends Thread
{
    private String name;

    public MyThread(String name)
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
        }
    }
}