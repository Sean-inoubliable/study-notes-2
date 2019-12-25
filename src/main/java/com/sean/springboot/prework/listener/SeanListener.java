package com.sean.springboot.prework.listener;

import org.springframework.stereotype.Service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Description:
 * @Author: Sean, CSII
 * @Date: 2019-12-11 18:00
 */
@Service
@WebListener
public class SeanListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContext) {
        System.out.println("servletContext销毁......");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {
        System.out.println("servletContext初始化......");
    }
}
