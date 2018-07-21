package com.wf.ew.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.UUIDUtil;
import com.wf.ew.system.dao.LoginRecordMapper;
import com.wf.ew.system.model.LoginRecord;
import com.wf.ew.system.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {
    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public boolean add(LoginRecord loginRecord) {
        loginRecord.setCreateTime(new Date());
        return loginRecordMapper.insert(loginRecord) > 0;
    }

    @Override
    public PageResult<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String account) {
        Page<LoginRecord> page = new Page<>(pageNum, pageSize);
        List<LoginRecord> records = loginRecordMapper.listFull(page, startDate, endDate, account);
        return new PageResult<>(page.getTotal(), records);
    }
}