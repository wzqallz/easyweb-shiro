package com.wf.ew.system.controller;

import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.utils.JSONUtil;
import com.wf.ew.common.utils.StringUtil;
import com.wf.ew.system.model.Authorities;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.service.AuthoritiesService;
import com.wf.ew.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 角色管理
 **/
@Controller
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthoritiesService authoritiesService;

    @RequiresPermissions("role:view")
    @RequestMapping()
    public String role() {
        return "system/role.html";
    }

    @RequestMapping("/auth")
    public String roleAuth(String roleId, Model model) {
        model.addAttribute("roleId", roleId);
        return "system/role_auth.html";
    }

    /**
     * 查询所有角色
     **/
    @RequiresPermissions("role:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Role> list(String keyword) {
        List<Role> list = roleService.list(false);
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = String.valueOf(next.getRoleId()).contains(keyword) || next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
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
    @RequiresPermissions("role:add")
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
    @RequiresPermissions("role:edit")
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
    @RequiresPermissions("role:delete")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer roleId) {
        if (roleService.updateState(roleId, 1)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 角色权限树
     */
    @ResponseBody
    @GetMapping("/authTree")
    public List<Map<String, Object>> authTree(Integer roleId) {
        List<Authorities> roleAuths = authoritiesService.listByRoleId(roleId);
        List<Authorities> allAuths = authoritiesService.list();
        List<Map<String, Object>> authTrees = new ArrayList<>();
        for (Authorities one : allAuths) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id", one.getAuthorityId());
            authTree.put("name", one.getAuthorityName() + " " + StringUtil.getStr(one.getAuthority()));
            authTree.put("pId", one.getParentId());
            authTree.put("open", true);
            authTree.put("checked", false);
            for (Authorities temp : roleAuths) {
                if (temp.getAuthorityId().equals(one.getAuthorityId())) {
                    authTree.put("checked", true);
                    break;
                }
            }
            authTrees.add(authTree);
        }
        return authTrees;
    }

    /**
     * 修改角色权限
     */
    @RequiresPermissions("role:auth")
    @ResponseBody
    @PostMapping("/updateRoleAuth")
    public JsonResult updateRoleAuth(Integer roleId, String authIds) {
        if (authoritiesService.updateRoleAuth(roleId, JSONUtil.parseArray(authIds, Integer.class))) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }
}
