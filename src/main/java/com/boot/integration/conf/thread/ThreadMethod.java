package com.boot.integration.conf.thread;

public class ThreadMethod
{
    public static void main(String[] args)
    {
//        join();
//        daemon();
    }

    public static void join()
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < 10; i++)
                {
                    System.out.println(Thread.currentThread().getName() + "运行" + i + "次");
                }
            }
        };

        t.start();

        for (int i = 0; i < 20; i++)
        {
            if (i > 10)
            {
                try
                {
                    t.join();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("Main线程运行 --> " + i);
        }
    }

    public static void daemon(){
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    System.out.println(Thread.currentThread().getName() + "运行中");
                }
            }
        };

        t.setDaemon(true);
        t.start();
    }
}
