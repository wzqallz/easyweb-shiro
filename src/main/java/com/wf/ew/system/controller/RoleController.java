package com.wf.ew.system.controller;

import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * 角色管理
 **/
@Controller
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequiresPermissions("system:role")
    @RequestMapping()
    public String role() {
        return "system/role.html";
    }

    /**
     * 查询所有角色
     **/
    @RequiresPermissions("system:role:list")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Role> list(String keyword) {
        List<Role> list = roleService.list(false);
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = next.getRoleId().contains(keyword) || next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return new PageResult<>(list);
    }

    /**
     * 添加角色
     **/
    @RequiresPermissions("system:role:add")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Role role) {
        if (roleService.add(role)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改角色
     **/
    @RequiresPermissions("system:role:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Role role) {
        if (roleService.update(role)) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }

    /**
     * 删除角色
     **/
    @RequiresPermissions("system:role:delete")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(String roleId) {
        if (roleService.updateState(roleId, 1)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }
}
