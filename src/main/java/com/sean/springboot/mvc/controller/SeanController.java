package com.sean.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Sean, CSII
 * @Date: 2019-12-10 14:26
 */
@Controller
public class SeanController {

    @RequestMapping(value = "/list")
    public String list() {
        return "list";
    }
}
