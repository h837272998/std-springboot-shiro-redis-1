package com.hjh.springbootshiro2.realm;

import com.hjh.springbootshiro2.pojo.User;
import com.hjh.springbootshiro2.service.PermissionService;
import com.hjh.springbootshiro2.service.RoleService;
import com.hjh.springbootshiro2.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * @Description: 一般使用AuthorizingRealm继承了AUthenticatingReaml身份验证。间接继承CachingRealm
 *
 * @Author: HJH
 * @Date: 2019-07-16 16:17
 */
public class DatabaseRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("调用授权");
        // 能进入到这里，表示账号已经通过验证了
        String userName = (String) principals.getPrimaryPrincipal();
        // 通过service获取角色和权限
        Set<String> permissions = permissionService.listPermissions(userName);
        Set<String> roles = roleService.listRoleNames(userName);
        // 授权对象
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        // 把通过service获取到的角色和权限放进去
        s.setStringPermissions(permissions);
        s.setRoles(roles);
        return s;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取账号密码
        System.out.println("调用验证");
        UsernamePasswordToken t = (UsernamePasswordToken) token;
        String userName = token.getPrincipal().toString();
        // 获取数据库中的密码
        User user = userService.getByName(userName);
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        // 认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        // 认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        // 盐也放进去
        // 这样通过applicationContext-shiro.xml里配置的 HashedCredentialsMatcher 进行自动校验
        SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt),
                getName());
        return a;
    }
}
