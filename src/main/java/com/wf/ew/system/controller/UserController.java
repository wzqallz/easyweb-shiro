package com.wf.ew.system.controller;

import com.wf.ew.common.BaseController;
import com.wf.ew.common.JsonResult;
import com.wf.ew.common.PageResult;
import com.wf.ew.common.shiro.EndecryptUtil;
import com.wf.ew.common.utils.StringUtil;
import com.wf.ew.system.model.Role;
import com.wf.ew.system.model.User;
import com.wf.ew.system.service.RoleService;
import com.wf.ew.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping
    public String user() {
        return "system/user.html";
    }

    @RequestMapping("/addView")
    public String addUser(Model model) {
        List<Role> roles = roleService.list(false);
        model.addAttribute("roles", roles);
        return "system/user_form.html";
    }

    /**
     * 查询用户列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<User> list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        return userService.list(page, limit, true, searchKey, searchValue);
    }

    /**
     * 添加用户
     **/
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(User user, String roleId) {
        List<Role> roleIds = new ArrayList<>();
        String[] split = roleId.split(",");
        for (String t : split) {
            Role role = new Role();
            role.setRoleId(t);
            roleIds.add(role);
        }
        user.setRoles(roleIds);
        user.setPassword("123456");
        if (userService.add(user)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改用户
     **/
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(User user, String roleId) {
        if ("admin".equals(user.getUserId())) {
            return JsonResult.error("演示系统不能操作admin");
        }
        List<Role> roleIds = new ArrayList<>();
        String[] split = roleId.split(",");
        for (String t : split) {
            Role role = new Role();
            role.setRoleId(t);
            roleIds.add(role);
        }
        user.setRoles(roleIds);
        if (userService.update(user)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }

    /**
     * 修改用户状态
     **/
    @ResponseBody
    @RequestMapping("/updateState")
    public JsonResult updateState(String userId, Integer state) {
        if (userService.updateState(userId, state)) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }

    /**
     * 修改自己密码
     **/
    @ResponseBody
    @RequestMapping("/updatePsw")
    public JsonResult updatePsw(String oldPsw, String newPsw) {
        if (false) {
            return JsonResult.error("演示系统关闭该功能");
        }
        String finalSecret = EndecryptUtil.encrytMd5(oldPsw, getLoginUserId(), 3);
        if (!finalSecret.equals(getLoginUser().getPassword())) {
            return JsonResult.error("原密码输入不正确");
        }
        if (userService.updatePsw(getLoginUserId(), newPsw)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }

    /**
     * 重置密码
     **/
    @ResponseBody
    @RequestMapping("/restPsw")
    public JsonResult resetPsw(String userId) {
        if (userService.updatePsw(userId, "123456")) {
            return JsonResult.ok("重置成功");
        } else {
            return JsonResult.error("重置失败");
        }
    }
}
