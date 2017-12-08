package cn.syl.action;

import cn.syl.anno.PostRequest;
import cn.syl.domain.User;
import cn.syl.utils.SysConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;



/**
 * @Description: 登录和退出类
 * @Author: 传智播客 java学院	传智.宋江
 * @Company: http://java.itcast.cn
 * @CreateDate: 2014年10月31日
 */
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @PostRequest(value = false)
    public String showlogin(){
        return "tologin";
    }

    //SSH传统登录方式
    @PostRequest(value = true)
    public String login() throws Exception {

//		if(true){
//			String msg = "登录错误，请重新填写用户名密码!";
//			this.addActionError(msg);
//			throw new Exception(msg);
//		}
//		User user = new User(username, password);
//		User login = userService.login(user);
//		if (login != null) {
//			ActionContext.getContext().getValueStack().push(user);
//			session.put(SysConstant.CURRENT_USER_INFO, login);	//记录session
//			return SUCCESS;
//		}
        if (StringUtils.isEmpty(username)) {

            return "tologin";
        }

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            subject.login(token); //这段代码执行时,会自动跳入AuthRealm中验证的方法
            User user  = (User) subject.getPrincipal();
           session.put(SysConstant.CURRENT_USER_INFO,user);
        } catch (Exception e) {
            e.printStackTrace();
            request.put("errorInfo","用户名,密码错误");
            return "tologin";
        }


        return SUCCESS;
    }


    //退出
    public String logout() {
        session.remove(SysConstant.CURRENT_USER_INFO);        //删除session

        return "logout";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

