package com.shanhh.google.contacts.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import com.shanhh.google.core.common.Logger;

/**
 * 从文件控制读取配置文件
 * @author dan.shan
 *
 */
public class ServiceConfig {
    
    private static Logger logger = Logger.getLogger(ServiceConfig.class);
    
    private static Properties p = new Properties();
    private static long lastModify = -1;
    private static long expires = 0 ;
    
    // 配置文件
    private static final String configPath = ServiceConfig.class.getResource("/contacts.properties").getPath();
    
    static {
        reload();
    }
    
    /**
     * 重新加载配置文件
     */
    private static void reload() {
        FileInputStream fis = null;
        try {
        	fis = new FileInputStream(configPath);
            p.load(fis);
        } catch (IOException e) {
            logger.error("cannot find file: {0}", e, ServiceConfig.class.getResource("/" + configPath).getPath());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    private static String getProperty(String name){
        long now = (new Date()).getTime();
        if(now > expires){
            File f = new File(ServiceConfig.class.getResource("/").getPath() + configPath);
            if(f.lastModified() != lastModify){
                lastModify = f.lastModified();
                reload();
            }
            expires = now + 6 * 1000;
        }
        
        return p.getProperty(name);
    }
    
    /**
     * getProperty方法的简写
     * @param name
     * @return value
     */
    public static String get(String name) {
        return getProperty(name);
    }
    public static void set (String name ,String value){
        if(p == null){
            return ;
        }
        p.setProperty(name, value);
    }
    /**
     * 获取一个配制项，如果项没有被配制，则返回设置的默认值
     * @param name 配制项名
     * @param defaultValue 默认值
     * @return 配制值
     */
    public static String get(String name, String defaultValue) {
        String ret = getProperty(name);
        return ret == null ? defaultValue : ret;
    }
    
    /**
     * 从配制文件中获取一个整形的配制值，如果没有配制，则返回默认值
     * @param item
     * @param defaultValue
     * @return int value
     */
    public static int getInt(String item, int defaultValue) {
        String value = getProperty(item);
        if (value == null) {
            return defaultValue;
        }
        int ret = defaultValue;
        try {
            ret = Integer.parseInt(value);
        } catch (Exception ignor) {
            
        }
        return ret;
    }
    
}