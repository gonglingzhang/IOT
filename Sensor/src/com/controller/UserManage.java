package com.controller;

import com.dao.UserDao;
import com.pojo.Config;
import com.pojo.UpdateConfigForm;
import com.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.List;
@Controller
public class UserManage {
    //得到用户 设备名:值 的Map
    public Map GetMap(String username, String table, String parameter, int number) {  //用户名 表名 参数名（温度/湿度等）
        UserDao userDao = new UserDao();
        List device_name = userDao.get_device(username, table, number);//获取用户传感器的设备名
        Map device_map = new HashMap();      //创建一个字典存放  设备名：值 键值对。
        for (int i = 0; i < device_name.size(); i++) {    //device_name.get(i).toString(); 设备名
            String temp_temperature_value = userDao.get_data(username, table, device_name.get(i).toString(), parameter,number); //得到的设备名的温度值
            device_map.put(device_name.get(i).toString(), temp_temperature_value); //将设备名和值放到字典里
        }
        return device_map;
    }





    //显示实时数据
    @RequestMapping(value = "/usermanages")
    public String usermanage(ModelMap modelMap, HttpSession session) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        User user = userDao.search(name);  //从数据库中查找表单中的username
        int position_count = user.getCount();
        modelMap.put("address_fixed", "您的地址是");  //显示用户地址
        modelMap.put("address", user.getAddress());  //显示用户地址
        Config config = userDao.get_config(name);

        for (int i = 0; i < position_count; i++) {
            Map device_temperature_map = GetMap(user.getUsername(), "temperature", "temperature",i);  //设备名：温度   键值对。
            Map device_ph_map = GetMap(user.getUsername(), "ph", "ph",i);                             //ph
            Map device_humidity_map = GetMap(user.getUsername(), "humidity", "humidity",i);           //湿度
            Map device_oxygen_rate_map = GetMap(user.getUsername(), "oxygen_rate", "oxygen_rate",i);  //溶解氧
        /*
        显示各位置的实时数据
         */

            //控制颜色
            if (device_temperature_map.size() != 0 || device_ph_map.size() != 0 || device_humidity_map.size() != 0 || device_oxygen_rate_map.size() != 0) {
                Set<String> set_temp = device_temperature_map.keySet();
                Set<String> set_hum = device_humidity_map.keySet();
                Set<String> set_ph = device_ph_map.keySet();
                Set<String> set_oxy = device_oxygen_rate_map.keySet();
                String[] value_temp = new String[device_temperature_map.size()];
                String[] value_hum = new String[device_humidity_map.size()];
                String[] value_ph = new String[device_ph_map.size()];
                String[] value_oxy = new String[device_oxygen_rate_map.size()];
                int count = 0;
                for (String s : set_temp) {
                    value_temp[count] = ((String) device_temperature_map.get(s));
                    count++;
                }
                count =0;
                for (String s : set_hum) {
                    value_hum[count] = ((String) device_humidity_map.get(s));
                    count++;
                }
                count = 0;
                for (String s : set_ph) {
                    value_ph[count] = ((String) device_ph_map.get(s));
                    count++;
                }
                count = 0;
                for (String s : set_oxy) {
                    value_oxy[count] = ((String) device_oxygen_rate_map.get(s));
                    count++;
                }
                String color = " ";
                for (int c = 0; c < value_temp.length; c++) {
                    float t = Float.parseFloat(value_temp[c]);
                    if (t < config.getTemp_min()){
                        color="#317da0";
                        String out_temperature = device_temperature_map.toString().substring(1, device_temperature_map.toString().length() - 1);  //{DHT11=32.2, DS18B20=20.2} -> DHT11=32.2, DS18B20=20.2
                        out_temperature = out_temperature.replace(",", "℃ ");
                        out_temperature += "℃ ";
                        modelMap.put("temperature"+i, out_temperature+" 过低！");
                    } else if (t > config.getTemp_max()){
                        color="#ff6166";
                        String out_temperature = device_temperature_map.toString().substring(1, device_temperature_map.toString().length() - 1);  //{DHT11=32.2, DS18B20=20.2} -> DHT11=32.2, DS18B20=20.2
                        out_temperature = out_temperature.replace(",", "℃ ");
                        out_temperature += "℃ ";
                        modelMap.put("temperature"+i, out_temperature+" 过高！");
                    }else {
                        String out_temperature = device_temperature_map.toString().substring(1, device_temperature_map.toString().length() - 1);  //{DHT11=32.2, DS18B20=20.2} -> DHT11=32.2, DS18B20=20.2
                        out_temperature = out_temperature.replace(",", "℃ ");
                        out_temperature += "℃ ";
                        modelMap.put("temperature"+i, out_temperature);
                    }
                }
                for (int c = 0; c < value_hum.length; c++) {
                    float t = Float.parseFloat(value_hum[c]);
                    if (t < config.getHum_min()){
                        color="#317da0";
                        String out_humidity = device_humidity_map.toString().substring(1, device_humidity_map.toString().length() - 1);
                        out_humidity = out_humidity.replace(",", "% ");
                        out_humidity += "%";
                        modelMap.put("humidity"+i, out_humidity+" 过低！");

                    } else if (t > config.getHum_max()){
                        color="#ff6166";
                        String out_humidity = device_humidity_map.toString().substring(1, device_humidity_map.toString().length() - 1);
                        out_humidity = out_humidity.replace(",", "% ");
                        out_humidity += "%";
                        modelMap.put("humidity"+i, out_humidity+" 过高！");
                    }else {
                        String out_humidity = device_humidity_map.toString().substring(1, device_humidity_map.toString().length() - 1);
                        out_humidity = out_humidity.replace(",", "% ");
                        out_humidity += "%";
                        modelMap.put("humidity"+i, out_humidity);
                    }
                }
                for (int c = 0; c < value_ph.length; c++) {
                    float t = Float.parseFloat(value_ph[c]);
                    if (t < config.getPh_min()){
                        color="#317da0";
                        String out_ph = device_ph_map.toString().substring(1, device_ph_map.toString().length() - 1);
                        out_ph = out_ph.replace(",", " ");
                        modelMap.put("ph"+i, out_ph+" 过低！");
                    } else if (t > config.getPh_max()){
                        color="#ff6166";
                        String out_ph = device_ph_map.toString().substring(1, device_ph_map.toString().length() - 1);
                        out_ph = out_ph.replace(",", " ");
                        modelMap.put("ph"+i, out_ph +" 过高！");
                    }else {
                        String out_ph = device_ph_map.toString().substring(1, device_ph_map.toString().length() - 1);
                        out_ph = out_ph.replace(",", " ");
                        modelMap.put("ph"+i, out_ph);
                    }
                }
                for (int c = 0; c < value_oxy.length; c++) {
                    float t = Float.parseFloat(value_oxy[c]);
                    if (t < config.getOxy_min()){
                        color="#317da0";
                        String out_oxygen_rate = device_oxygen_rate_map.toString().substring(1, device_oxygen_rate_map.toString().length() - 1);
                        out_oxygen_rate = out_oxygen_rate.replace(",", "mg/L ");
                        out_oxygen_rate += "mg/L";
                        modelMap.put("oxygen_rate"+i, out_oxygen_rate+" 过低！");
                    } else if (t > config.getOxy_max()){
                        color="#ff6166";
                        String out_oxygen_rate = device_oxygen_rate_map.toString().substring(1, device_oxygen_rate_map.toString().length() - 1);
                        out_oxygen_rate = out_oxygen_rate.replace(",", "mg/L ");
                        out_oxygen_rate += "mg/L";
                        modelMap.put("oxygen_rate"+i, out_oxygen_rate+" 过高！");
                    }else {
                        String out_oxygen_rate = device_oxygen_rate_map.toString().substring(1, device_oxygen_rate_map.toString().length() - 1);
                        out_oxygen_rate = out_oxygen_rate.replace(",", "mg/L ");
                        out_oxygen_rate += "mg/L";
                        modelMap.put("oxygen_rate"+i, out_oxygen_rate);
                    }
                }
                modelMap.put("color"+i, color);
            }
        }
        return "user_manage.jsp";
    } //usermanage






    //画温度图
    @RequestMapping(value = "/showtemperatureplot", method = RequestMethod.GET)
    public String ShowTemperaturePlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "temperature",position);  //获取位置为position的所有温度传感器名
        List device_list = new ArrayList();
        List data_list = new ArrayList();  // [[DHT11的数据],[DS18B20的数据],[...]]
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_dataset(name, "temperature", device.get(i).toString(), "temperature", position); //获取位置为position的所有传感器的数据
            List temperature = new ArrayList();  //获取温度的数值，放到list中  [DHT11的数据],[DS18B20的数据],[...]
            List<String> time = new ArrayList(); //获取时间的数值，放到list中
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("temperature");
                temperature.add(temp_temperature);  //将温度添加到列表中
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");//将日期添加到列表中
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "temperature_history.jsp";
    }

    @RequestMapping(value = "/showtemperatureplot_all", method = RequestMethod.GET)
    public String ShowAllTemperaturePlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "temperature",position);  //获取位置为position的所有温度传感器名
        List device_list = new ArrayList();
        List data_list = new ArrayList();  // [[DHT11的数据],[DS18B20的数据],[...]]
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_all_dataset(name, "temperature", device.get(i).toString(), "temperature", position); //获取位置为position的所有传感器的数据
            List temperature = new ArrayList();  //获取温度的数值，放到list中  [DHT11的数据],[DS18B20的数据],[...]
            List<String> time = new ArrayList(); //获取时间的数值，放到list中
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("temperature");
                temperature.add(temp_temperature);  //将温度添加到列表中
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");//将日期添加到列表中
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "temperature_history.jsp";
    }

    //画ph图
    @RequestMapping(value = "/showphplot", method = RequestMethod.GET)
    public String ShowPHPlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "ph",position);
        List device_list = new ArrayList();
        List data_list = new ArrayList();
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_dataset(name, "ph", device.get(i).toString(), "ph", position);
            List temperature = new ArrayList();
            List<String> time = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("ph");
                temperature.add(temp_temperature);
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "ph_history.jsp";
    }


    @RequestMapping(value = "/showphplot_all", method = RequestMethod.GET)
    public String ShowAllPHPlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "ph",position);
        List device_list = new ArrayList();
        List data_list = new ArrayList();
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_all_dataset(name, "ph", device.get(i).toString(), "ph", position);
            List temperature = new ArrayList();
            List<String> time = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("ph");
                temperature.add(temp_temperature);
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "ph_history.jsp";
    }

    //画湿度图
    @RequestMapping(value = "/showhumidityplot", method = RequestMethod.GET)
    public String ShowHumidityPlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "humidity",position);
        List device_list = new ArrayList();
        List data_list = new ArrayList();
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_dataset(name, "humidity", device.get(i).toString(), "humidity", position);
            List temperature = new ArrayList();
            List<String> time = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("humidity");
                temperature.add(temp_temperature);
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "humidity_history.jsp";
    }

    @RequestMapping(value = "/showhumidityplot_all", method = RequestMethod.GET)
    public String ShowAllHumidityPlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "humidity",position);
        List device_list = new ArrayList();
        List data_list = new ArrayList();
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_all_dataset(name, "humidity", device.get(i).toString(), "humidity", position);
            List temperature = new ArrayList();
            List<String> time = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("humidity");
                temperature.add(temp_temperature);
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "humidity_history.jsp";
    }

    //画溶解氧图
    @RequestMapping(value = "/showoxygenrateplot", method = RequestMethod.GET)
    public String ShowOxygenRatePlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "oxygen_rate",position);
        List device_list = new ArrayList();
        List data_list = new ArrayList();
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_dataset(name, "oxygen_rate", device.get(i).toString(), "oxygen_rate", position);
            List temperature = new ArrayList();
            List<String> time = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("oxygen_rate");
                temperature.add(temp_temperature);
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "oxygenrate_history.jsp";
    }
    @RequestMapping(value = "/showoxygenrateplot_all", method = RequestMethod.GET)
    public String ShowAllOxygenRatePlot(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        UserDao userDao = new UserDao();
        List device = userDao.get_device(name, "oxygen_rate",position);
        List device_list = new ArrayList();
        List data_list = new ArrayList();
        List time_list = new ArrayList();
        for (int i = 0; i < device.size(); i++) {
            List list = userDao.get_all_dataset(name, "oxygen_rate", device.get(i).toString(), "oxygen_rate", position);
            List temperature = new ArrayList();
            List<String> time = new ArrayList();
            for (int j = 0; j < list.size(); j++) {
                Map map = (Map) list.get(j);
                float temp_temperature = (float) map.get("oxygen_rate");
                temperature.add(temp_temperature);
                Date temp_time = (Date) map.get("time");
                time.add("'" + temp_time.toString() + "'");
            }
            data_list.add(temperature);
            time_list.add(time);
            device_list.add("'" + device.get(i).toString() + "'");
        }
        modelMap.put("device", device_list);
        modelMap.put("data_list", data_list);
        modelMap.put("time_list", time_list);
        return "oxygenrate_history.jsp";
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String Config(ModelMap modelMap, HttpSession session,Integer position) {
        String name = (String) session.getAttribute("username");
        Config config   = new UserDao().get_config(name);
        modelMap.put("config",config);
        return "config.jsp";
    }

    @RequestMapping(value = "/updateconfig", method = RequestMethod.POST)
    public String UpdateConfig(UpdateConfigForm updateConfigForm,HttpSession session) {
        String name = (String) session.getAttribute("username");
        Config config = new Config();
        config.setUsername(name);
        BeanUtils.copyProperties(updateConfigForm,config);
        new UserDao().update_config(config);
        return "redirect:/config";
    }
}

//    UserDao userDao = new UserDao();
//
//        modelMap.put("config_tip", time_list);