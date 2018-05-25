package com.controller;

import com.dao.UserDao;
import com.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResetPassword {
    @RequestMapping(value = "/resetpasswords", method = RequestMethod.POST)
    public String resetpassword(User userForm, ModelMap modelMap) {
        UserDao userDao = new UserDao();
        User user = userDao.search(userForm.getUsername());
        if ("".equals(userForm.getUsername().trim())) {
            modelMap.put("Tip", "用户名不能为空!");
            return "reset_password.jsp";
        } else {
            if (user == null) { //用户不存在
                modelMap.put("Tip", "请正确输入您的用户名!");
                return "reset_password.jsp";
            } else if (userForm.getUsername().equals(user.getUsername()) && userForm.getPhone().equals(user.getPhone()) &&
                    userForm.getMail().equals(user.getMail())) { //数据库中的userForm.getUsername()等于表单中的user.getUsername() ...
                if (userForm.getPassword().trim().length() < 6) {
                    modelMap.put("Tip", "密码不能低于6位数！");
                    return "reset_password.jsp";
                } else if (!userForm.getPassword().equals(userForm.getPassword2())) { //两次输入的密码不一致
                    modelMap.put("Tip", "两次输入的密码不一致！");
                    return "reset_password.jsp";
                } else { //成功修改密码
                    userDao.update_pwd(userForm);
                    modelMap.put("Tip", "成功修改密码！");
                    return "reset_password.jsp";
                }
            } else {//用户存在但是手机号或邮箱错误
                modelMap.put("Tip", "手机或邮箱与预留的不符！");
                return "reset_password.jsp";
            }
        }
    }//resetpassword
    @RequestMapping(value = "/returntopage")
    public String returntopage() {
        return "reset_password.jsp";
    }

}//ResetPassword
