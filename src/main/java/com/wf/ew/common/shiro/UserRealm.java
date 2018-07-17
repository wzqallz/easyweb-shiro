package com.wf.ew.common.shiro;

import com.alibaba.fastjson.JSON;
import com.wf.ew.system.model.Authorities;
import com.wf.ew.system.model.User;
import com.wf.ew.system.service.AuthoritiesService;
import com.wf.ew.system.service.RoleService;
import com.wf.ew.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Shiro认证和授权
 * Created by wangfan on 2018-02-22 上午 11:29
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthoritiesService authoritiesService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String[] userRoles = roleService.getRoleIds(user.getUserId());
        Set<String> roles = new HashSet<>();
        for (int i = 0; i < userRoles.length; i++) {
            roles.add(userRoles[i]);
        }
        authorizationInfo.setRoles(roles);
        List<Authorities> authorities = authoritiesService.listByUserId(user.getUserId());
        Set<String> permissions = new HashSet<>();
        for (int i = 0; i < authorities.size(); i++) {
            permissions.add(authorities.get(i).getAuthority());
        }
        System.out.printf(JSON.toJSONString(permissions));
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.getByUsername(username);
        if (user == null) {
            throw new UnknownAccountException(); // 账号不存在
        }
        if (user.getState() != 0) {
            throw new LockedAccountException();  // 账号被锁定
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUserId()), getName());
        return authenticationInfo;
    }
}
