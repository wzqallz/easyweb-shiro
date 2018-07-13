package com.wf.ew.system.controller;

import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.ReflectUtil;
import com.wf.ew.system.model.Authorities;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.service.AuthoritiesService;
import com.wf.ew.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 **/
@Controller
@RequestMapping("/system/authorities")
public class AuthoritiesController extends BaseController {
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private RoleService roleService;

    @RequestMapping()
    public String authorities(Model model) {
        List<Role> roles = roleService.list(false);
        model.addAttribute("roles", roles);
        return "system/authorities.html";
    }

    /**
     * 查询所有权限
     **/
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Map<String, Object>> list(String roleId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Authorities> authorities = authoritiesService.list();
        List<String> roleAuths = authoritiesService.listByRoleId(roleId);
        for (Authorities one : authorities) {
            Map<String, Object> map = ReflectUtil.objectToMap(one);
            map.put("checked", 0);
            for (String roleAuth : roleAuths) {
                if (one.getAuthority().equals(roleAuth)) {
                    map.put("checked", 1);
                    break;
                }
            }
            maps.add(map);
        }
        return new PageResult<>(maps);
    }

    /**
     * 给角色添加权限
     **/
    @ResponseBody
    @RequestMapping("/addRoleAuth")
    public JsonResult addRoleAuth(String roleId, String authId) {
        if (authoritiesService.addRoleAuth(roleId, authId)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    /**
     * 移除角色权限
     **/
    @ResponseBody
    @RequestMapping("/deleteRoleAuth")
    public JsonResult deleteRoleAuth(String roleId, String authId) {
        if (authoritiesService.deleteRoleAuth(roleId, authId)) {
            return JsonResult.ok();
        }
        return JsonResult.error();
    }
}
