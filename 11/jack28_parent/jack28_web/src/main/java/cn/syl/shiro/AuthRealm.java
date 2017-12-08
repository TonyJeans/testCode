package cn.syl.shiro;

import cn.syl.domain.Module;
import cn.syl.domain.Role;
import cn.syl.domain.User;
import cn.syl.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        System.err.println("授权");
        User user = (User) pc.fromRealm(this.getName()).iterator().next();//根据传入的值选择realm
        System.err.println("当前登录用户:"+user.getUserName());
        Set<Role> roles = user.getRoles();//对象导航
        List<String> permissionList = new ArrayList<>();
        for (Role role : roles) {
            //遍历每个角色
            System.err.println("用户:"+user.getUserName()+"有:"+ role.getName()+"角色");
            Set<Module> modules = role.getModules(); //得到角色下的模块列表(权限)
            for (Module module : modules) {
                System.out.println(role.getName()+"角色的权限有:"+ module.getName());
                permissionList.add(module.getName());
            }
        }


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionList); //添加用户模块,权限
        return info;
    }
//验证

    /**
     *
     * @param token 用户输入的密码,subject.login传入
     * @return  授权标签的时候检查???
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.err.println("验证");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        //调用业务方法,根据用户名查询密码
        String hql = "From User where userName=?";
        List<User> userList = userService.find(hql, User.class, new String[]{upToken.getUsername()});
        if (userList!=null&&userList.size()>0){
            User user = userList.get(0);
            System.err.println("this.getName()"+this.getName());
        AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return info; //返回不为空,就进去密码比较器
        }
        return null;
    }
}
