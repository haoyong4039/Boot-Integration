package com.boot.integration.util.plat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * <pre>
 * <一句话功能简述>
 * Spring bean工厂类
 * <功能详细描述>
 * </pre>
 *
 * @author haoyong
 */
public class BeanUtils
{
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private static ApplicationContext factory;

    /**
     * 获取 factory
     *
     * @return 返回 factory
     */
    public static ApplicationContext getFactory()
    {
        return factory;
    }

    /**
     * 设置 factory
     */
    public static void setFactory(ApplicationContext factory)
    {
        BeanUtils.factory = factory;
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 初始化spring 配置文件
     * <功能详细描述>
     * </pre>
     */
    public static void initSpringConfig(String springConfigPath)
    {
        // 初始化spring配置文件
        factory = new FileSystemXmlApplicationContext(springConfigPath);
    }

    /**
     * <pre>
     * <一句话功能简述>
     * 通过Class的名称获取实例
     * <功能详细描述>
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObjectBean(Class<T> clazz)
    {
        T obj = null;
        try
        {
            String className = clazz.getSimpleName();
            String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
            obj = (T)factory.getBean(beanName);
        }
        catch (Exception e)
        {
            logger.error("getObjectBean", e);
        }
        return obj;

    }

    /**
     * <pre>
     * <一句话功能简述>
     * 通过bean的名称获取实例
     * <功能详细描述>
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObjectBean(String beanName)
    {
        T obj = null;
        try
        {
            obj = (T)factory.getBean(beanName);
        }
        catch (Exception e)
        {
            logger.error("[beanName:{}]", beanName, e);

        }
        return obj;

    }
}

