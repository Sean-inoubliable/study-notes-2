package com.sean.springboot.prework.listener;

import org.springframework.stereotype.Service;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Description: session监听器
 * @Author: Sean, CSII
 * @Date: 2019-12-11 18:03
 */
@Service
@WebListener
public class SeanSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent session) {
        System.out.println("session创建成功......");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent session) {
        System.out.println("session销毁......");
    }
}
