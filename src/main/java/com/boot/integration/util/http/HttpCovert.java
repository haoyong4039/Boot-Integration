package com.boot.integration.util.http;

import com.boot.integration.exeption.CustomCode;
import com.boot.integration.exeption.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HttpCovert
{
    private static final Logger logger = LoggerFactory.getLogger(HttpCovert.class);

    /**
     * <pre>
     * <一句话功能简述>
     * 将网关请求的数据流转换成字符串
     * <功能详细描述>
     * </pre>
     *
     * @param servletInputStream 网关数据流
     */
    public static String convertStreamToMsg(ServletInputStream servletInputStream) throws CustomException
    {
        // 字节流
        ByteArrayOutputStream arrayOutputStream = null;

        try
        {
            int read = 0;
            byte[] buf = new byte[2048];
            arrayOutputStream = new ByteArrayOutputStream();

            // 获取servlet 的输入流
            while ((read = servletInputStream.read(buf)) > 0)
            {
                arrayOutputStream.write(buf, 0, read);
                arrayOutputStream.flush();
            }

            return new String(arrayOutputStream.toByteArray(), "UTF-8");
        }
        catch (Exception e)
        {
            throw new CustomException(CustomCode.ERROR_JSON_CONVERT_FAIL);
        }
        finally
        {
            // 关闭servlet流
            if (servletInputStream != null)
            {
                try
                {
                    servletInputStream.close();
                }
                catch (IOException e)
                {
                    logger.error(e.getMessage(), e);
                }
            }
            // 关闭字节流
            if (arrayOutputStream != null)
            {
                try
                {
                    arrayOutputStream.close();
                }
                catch (IOException e)
                {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}
