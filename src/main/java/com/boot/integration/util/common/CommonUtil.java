package com.boot.integration.util.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.UUID;

public class CommonUtil
{
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 获取用户真实IP地址
     * 不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址
     * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    public static String getRemoteIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null)
        {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip))
            {
                int index = ip.indexOf(",");
                if (index != -1)
                {
                    return ip.substring(0, index);
                }
                else
                {
                    return ip;
                }
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null)
        {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip))
            {
                return ip;
            }
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null)
        {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip))
            {
                return ip;
            }
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (ip != null)
        {
            if (!ip.isEmpty() && !"unKnown".equalsIgnoreCase(ip))
            {
                return ip;
            }
        }
        ip = request.getRemoteAddr();

        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    // 获取mac地址
    public static String getMacAddress()
    {
        try
        {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements())
            {
                NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint()
                    || !netInterface.isUp())
                {
                    continue;
                }
                else
                {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null)
                    {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++)
                        {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0)
                        {
                            return sb.toString();
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("MAC地址获取失败", e);
        }
        return "";
    }

    // 获取ip地址
    public static String getIpAddress()
    {
        try
        {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements())
            {
                NetworkInterface netInterface = (NetworkInterface)allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint()
                    || !netInterface.isUp())
                {
                    continue;
                }
                else
                {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements())
                    {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address)
                        {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("IP地址获取失败", e);
        }
        return "";
    }
}
