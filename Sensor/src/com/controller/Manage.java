package com.controller;

import com.dao.UserDao;
import com.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class Manage {
    @RequestMapping(value = "/manages_add", method = RequestMethod.POST)
    public String add(User userForm, ModelMap modelMap) {
        UserDao userDao = new UserDao();
        User user = userDao.search(userForm.getUsername());
        System.out.println(userForm.getUsername());
        if ("".equals(userForm.getUsername().trim())) {
            modelMap.put("Tip_add", "用户名不能为空！");
            return "manage.jsp";
        } else {
            if (user == null) {
                if (!userForm.getPassword().equals(userForm.getPassword2())) {
                    modelMap.put("Tip_add", "两次密码不一致！");
                    return "manage.jsp";
                } else if (userForm.getPassword().trim().length() < 6) {
                    modelMap.put("Tip_add", "密码不能低于6位！");
                    return "manage.jsp";
                } else if (userForm.getPhone() == null || "".equals(userForm.getPhone().trim())) {
                    modelMap.put("Tip_add", "手机号不能为空！");
                    return "manage.jsp";
                } else if (userForm.getPhone() == null || "".equals(userForm.getMail().trim())) {
                    modelMap.put("Tip_add", "邮箱不能为空！");
                    return "manage.jsp";
                } else if (userForm.getPhone() == null || "".equals(userForm.getAddress().trim())) {
                    modelMap.put("Tip_add", "地址不能为空！");
                    return "manage.jsp";
                } else {
                    userDao.add(userForm);
                    userDao.add_config(userForm.getUsername());
                    modelMap.put("Tip_add", "添加成功！");
                    return "manage.jsp";
                }
            } else {
                modelMap.put("Tip_add", "用户名已存在！");
                return "manage.jsp";
            }
        }
    }//add
    @RequestMapping(value = "manages_search", method = RequestMethod.POST)
    public String search(User userForm, ModelMap modelMap) {
        System.out.println(123);
        UserDao userDao = new UserDao();
        try {
            User user = userDao.search(userForm.getUsername());
            System.out.println(user.getUsername());
            int position_count = user.getCount();
            if (user == null) {
                modelMap.put("Tip_search", "没有此用户！");
                return "manage.jsp";
            } else {
                if ("".equals(userForm.getUsername().trim())) {
                    modelMap.put("Tip_search", "用户名不能为空！");
                    return "manage.jsp";
                } else {
                    String ret = "";
                    for (int position_nub = 0; position_nub < position_count; position_nub++) {
                        ret += "位置" + position_nub + ":<br>";
                        List temperature_device_list = userDao.get_device(userForm.getUsername(), "temperature", position_nub);
                        List ph_device_list = userDao.get_device(userForm.getUsername(), "ph", position_nub);
                        List humidity_device_list = userDao.get_device(userForm.getUsername(), "humidity", position_nub);
                        List oxygen_device_list = userDao.get_device(userForm.getUsername(), "oxygen_rate", position_nub);
                        if (temperature_device_list.size() != 0 || ph_device_list.size() != 0 || humidity_device_list.size() != 0 || oxygen_device_list.size() != 0) {
                            if (temperature_device_list.size() != 0) {
                                ret += "temperature: ";
                                for (int i = 0; i < temperature_device_list.size(); i++) {
                                    ret += temperature_device_list.get(i).toString() + ";<br>";
                                }
                            }
                            if (ph_device_list.size() != 0) {
                                ret += "ph: ";
                                for (int i = 0; i < ph_device_list.size(); i++) {
                                    ret += ph_device_list.get(i).toString() + ";<br>";
                                }
                            }
                            if (humidity_device_list.size() != 0) {
                                ret += "humidity: ";
                                for (int i = 0; i < humidity_device_list.size(); i++) {
                                    ret += humidity_device_list.get(i).toString() + ";<br>";
                                }
                            }
                            if (oxygen_device_list.size() != 0) {
                                ret += "oxygen_rate: ";
                                for (int i = 0; i < oxygen_device_list.size(); i++) {
                                    ret += oxygen_device_list.get(i).toString() + ";<br>";
                                }
                            }
                            modelMap.put("device_fixed", "传感器");
                            modelMap.put("device", ret);

                        } else {
                            ret += "<br>对不起，此用户位置" + position_nub + "没有任何传感器！<br>";
                        }
                    }
                    modelMap.put("device", ret);
                    modelMap.put("Tip_phone_fixed", "手机号");
                    modelMap.put("Tip_phone", user.getPhone().toString());
                    modelMap.put("Tip_mail_fixed", "邮 箱");
                    modelMap.put("Tip_mail", user.getMail().toString());
                    modelMap.put("Tip_address_fixed", "地 址");
                    modelMap.put("Tip_address", user.getAddress().toString());
                    modelMap.put("Tip_count_fixed", "位置数量");
                    modelMap.put("Tip_count", "" + user.getCount());
                    return "manage.jsp";
                }
            }
        }
        catch (Exception e) {
            modelMap.put("Tip_search", "用户不存在！");
            return "manage.jsp";
        }
    }//search

    @RequestMapping(value = "manages_update", method = RequestMethod.POST)
    public String update(User userForm, ModelMap modelMap) {
        UserDao userDao = new UserDao();
        User user = userDao.search(userForm.getUsername());
        if ("".equals(userForm.getUsername().trim())) {
            modelMap.put("Tip_update", "用户名不能为空！");
            return "manage.jsp";
        } else {
            if (user == null) {
                modelMap.put("Tip_update", "没有此用户！");
                return "manage.jsp";
            } else {
                if (userForm.getPhone() == null || "".equals(userForm.getPhone().trim())) {
                    modelMap.put("Tip_update", "手机号不能为空！");
                    return "manage.jsp";
                } else if (userForm.getPhone() == null || "".equals(userForm.getMail().trim())) {
                    modelMap.put("Tip_update", "邮箱不能为空！");
                    return "manage.jsp";
                } else if (userForm.getPhone() == null || "".equals(userForm.getAddress().trim())) {
                    modelMap.put("Tip_update", "地址不能为空！");
                    return "manage.jsp";
                } else {
                    userDao.update(userForm);
                    modelMap.put("Tip_update", "修改成功！");
                    return "manage.jsp";
                }
            }
        }
    }//update

    @RequestMapping(value = "del_device", method = RequestMethod.POST)
    public String deldevice(User userForm, ModelMap modelMap) {
        UserDao userDao = new UserDao();
        try {
            User user = userDao.search(userForm.getUsername());
            String username = userForm.getUsername();
            String table = userForm.getTable();
            String device = userForm.getDevice();
            int position = userForm.getCount();
            String flag = userDao.get_data(username, table, device, "username", position);
            if ("".equals(userForm.getUsername().trim())) {
                modelMap.put("Tip_del", "用户名不能为空！");
                return "manage.jsp";
            } else {
                if (user == null) {
                    modelMap.put("Tip_del", "没有此用户！");
                    return "manage.jsp";
                } else if ("".equals(flag)) {
                    modelMap.put("Tip_del", "删除失败，请重新输入！");
                    return "manage.jsp";
                } else {
                    userDao.del_device(username, table, device, position);
                    modelMap.put("Tip_del", "删除成功！");
                    return "manage.jsp";
                }
            }
        } catch (Exception e){
            modelMap.put("Tip_del", "用户不存在！");
            return "manage.jsp";
        }
    }//deldevice

}//Manage