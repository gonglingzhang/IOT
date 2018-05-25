package com.dao;

import com.pojo.Config;
import com.pojo.User;
import com.dao.MD5;
import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
    MD5 md5 = new MD5();
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver"; //驱动程序名
    private String DB_URL = "jdbc:mysql://localhost:3306/SensorData"; //URL指向要访问的数据库名
    private String USER = "root"; //MySQL用户名
    private String PASS = "root"; //MySQL密码

    public Connection getConn(){  //连接数据库的方法
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public String get_data(String name, String table, String device, String parameter,int position) {
        String res = "";
        Connection conn = getConn();
        try {
            String sql = "select * from " + table + " where (device='" + device + "' and username='" + name + "' and position="+position+") order by time desc limit 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                res = rs.getString(parameter);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
    public List get_device(String name, String table, int number){
        String res = "";
        List list = new ArrayList();
        Connection conn = getConn();
        try {
            String sql = "SELECT DISTINCT device FROM (SELECT device FROM " + table + " WHERE username='" + name + "' AND position= "+number+") AS temp_table";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                res = rs.getString("device");
                list.add(res);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    public List get_alldevice(String name, String table){
        String res = "";
        List list = new ArrayList();
        Connection conn = getConn();
        try {
            String sql = "SELECT DISTINCT device FROM (SELECT device FROM " + table + " WHERE username='" + name + "') AS temp_table";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                res = rs.getString("device");
                list.add(res);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
    //从数据库中获取100条最近的历史数据用于画图
    public List get_dataset(String name, String table, String device, String parameter, int position ) {
        List list = new ArrayList();
        Connection conn = getConn();
        try {
            String sql = "SELECT * FROM (SELECT time," + parameter + " FROM " + table + " WHERE (device='" + device + "' AND username='" + name + "' AND position="+position+") ORDER BY time DESC LIMIT 100) AS temp_table ORDER BY time";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map rowData = new HashMap();
                for (int i=1; i<=columnCount; i++){
                    rowData.put(md.getColumnName(i),rs.getObject(i));
                }
                list.add(rowData);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    //从数据库中获取的历史数据用于画图
    public List get_all_dataset(String name, String table, String device, String parameter, int position ) {
        List list = new ArrayList();
        Connection conn = getConn();
        try {
            String sql = "SELECT * FROM (SELECT time," + parameter + " FROM " + table + " WHERE (device='" + device + "' AND username='" + name + "' AND position="+position+") ORDER BY time DESC) AS temp_table ORDER BY time";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            while (rs.next()) {
                Map rowData = new HashMap();
                for (int i=1; i<=columnCount; i++){
                    rowData.put(md.getColumnName(i),rs.getObject(i));
                }
                list.add(rowData);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public User search(String name) {
        User user = null;
        Connection conn = getConn();
        try {
            String sql = "select * from users where username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setMail(rs.getString("mail"));
                user.setAddress(rs.getString("address"));
                user.setRole(rs.getString("role"));
                user.setCount(rs.getInt("position_count"));
            }
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }
    public void add(User user){
        Connection conn = getConn();
        try {
            String pwd = md5.MD5(user.getPassword());
            String sql = "INSERT INTO users(username,password,phone,mail,address,position_count) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, pwd);
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getMail());
            preparedStatement.setString(5, user.getAddress());
            preparedStatement.setInt(6, user.getCount());
            preparedStatement.execute();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//add
    public void delete(String username){
        Connection conn = getConn();
        try {
            String sql = "DELETE FROM users WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.execute();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//delete
    public void update(User user){
        Connection conn = getConn();
        try {
            String sql = "UPDATE users SET phone=?,mail=?,address=?,position_count=? WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getPhone());
            preparedStatement.setString(2, user.getMail());
            preparedStatement.setString(3, user.getAddress());
            preparedStatement.setInt(4, user.getCount());
            preparedStatement.setString(5, user.getUsername());
            preparedStatement.execute();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//update
    public void update_pwd(User user){
        Connection conn = getConn();
        try {
            String pwd = md5.MD5(user.getPassword());
            String sql = "UPDATE users SET password=? WHERE username=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, pwd);
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.execute();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//update_pwd
    public void del_device(String username, String table, String device,int position){
        Connection conn = getConn();
        try {
            String sql = "DELETE FROM "+table+" WHERE username = ? AND device = ? AND position =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, device);
            preparedStatement.setInt(3, position);
            preparedStatement.execute();
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//del_device
    public Config get_config(String username){
        Config config = new Config();
        Connection conn = getConn();
        try {
            String sql = "SELECT  * FROM config WHERE username = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                config.setUsername(resultSet.getString("username"));
                config.setTemp_min(resultSet.getFloat("temp_min"));
                config.setTemp_max(resultSet.getFloat("temp_max"));
                config.setPh_min(resultSet.getFloat("ph_min"));
                config.setPh_max(resultSet.getFloat("ph_max"));
                config.setHum_min(resultSet.getFloat("hum_min"));
                config.setHum_max(resultSet.getFloat("hum_max"));
                config.setOxy_min(resultSet.getFloat("oxy_min"));
                config.setOxy_max(resultSet.getFloat("oxy_max"));
            }
            return config;
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public void update_config(Config config){
        Connection conn = getConn();
        try {
            String sql = "UPDATE config SET temp_min= "+config.getTemp_min()+",temp_max="+config.getTemp_max()+
                    ",ph_min= "+config.getPh_min()+",ph_max= "+config.getPh_max()+
                    ", hum_min= "+config.getHum_min()+" ,hum_max= "
                    +config.getHum_max()+", oxy_min= "+config.getOxy_min()+
                    ", oxy_max="+config.getOxy_max()+" WHERE username='"
                    +config.getUsername()+"'";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void add_config(String username){
        Connection conn = getConn();
        try {
            String sql = "INSERT INTO config VALUE('"+username+"',-5,35,6.5,9.8,45,90,3.5,7.6)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}//UserDao