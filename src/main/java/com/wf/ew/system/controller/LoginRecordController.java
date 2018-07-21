package com.wf.ew.system.controller;

import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wf.ew.system.model.LoginRecord;
import com.wf.ew.system.service.LoginRecordService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录日志管理
 **/
@Controller
@RequestMapping("/system/loginRecord")
public class LoginRecordController {
    @Autowired
    private LoginRecordService loginRecordService;

    @RequiresPermissions("loginRecord:view")
    @RequestMapping()
    public String loginRecord() {
        return "system/login_record.html";
    }

    /**
     * 查询所有登录日志
     **/
    @RequiresPermissions("loginRecord:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<LoginRecord> list(Integer page, Integer limit, String startDate, String endDate, String account) {
        if (StringUtil.isBlank(startDate)) {
            startDate = null;
        } else {
            startDate += " 00:00:00";
        }
        if (StringUtil.isBlank(endDate)) {
            endDate = null;
        } else {
            endDate += " 23:59:59";
        }
        if (StringUtil.isBlank(account)) {
            account = null;
        }
        return loginRecordService.list(page, limit, startDate, endDate, account);
    }

}
