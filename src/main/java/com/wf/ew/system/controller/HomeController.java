package com.wf.ew.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018-07-13 上午 11:21.
 */
@Controller()
@RequestMapping("/home")
public class HomeController {

    /**
     * 控制台
     */
    @RequiresPermissions("home:console")
    @RequestMapping("/console")
    public String console() {
        return "console.html";
    }

    /**
     * 消息弹窗
     */
    @RequestMapping("/message")
    public String message() {
        return "tpl/message.html";
    }

    /**
     * 修改密码弹窗
     */
    @RequestMapping("/password")
    public String password() {
        return "tpl/password.html";
    }

    /**
     * 主题设置弹窗
     */
    @RequestMapping("/theme")
    public String theme() {
        return "tpl/theme.html";
    }
}
