package com.controller;

import com.dao.UserDao;
import com.dao.MD5;
import com.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
public class Login {
    @RequestMapping(value = "/logins", method = RequestMethod.POST)
    public String login(User userForm, ModelMap modelMap, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        UserDao userDao = new UserDao();
        User user = userDao.search(userForm.getUsername());  //从数据库中查找表单中的username
        String username = userForm.getUsername();
        MD5 md5 = new MD5();
        String pwd = md5.MD5(userForm.getPassword()); //md5后的密码
        if ("".equals(user.getUsername().trim())) {
            modelMap.put("Tip", "用户不能为空!");
            return "forward:/login.jsp";
        } else {
            if (user == null) {
                modelMap.put("Tip", "用户不存在，请重新输入!");
                return "forward:/login.jsp";
            } else if (username.equals(user.getUsername()) && pwd.equals(user.getPassword())) {
                session.setAttribute("username", userForm.getUsername());
                if ("0".equals(user.getRole())) {
                    return "redirect:/usermanages";
                } else if ("1".equals((user.getRole()))) {
                    return "manage.jsp";
                } else {
                    modelMap.put("Tip", "角色信息错误，请联系管理员。");
                    return "forward:/login.jsp";
                }
            } else {
                modelMap.put("Tip", "密码错误，请重新输入!");
                return "forward:/login.jsp";
            }
        }
    }//login
} //Login
