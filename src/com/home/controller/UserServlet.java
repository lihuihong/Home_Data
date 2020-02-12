package com.home.controller;

import com.alibaba.fastjson.JSON;
import com.home.Dao.UserDao;
import com.home.pojo.Result;
import com.home.pojo.UserInfo;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String methodName = request.getParameter("method");
        try{
            switch (methodName) {
                case "save":
                    saveUser(request, response);
                    break;
                case "list":
                    allUser(request, response);
                    break;
                case "getUser":
                    getUserInfo(request, response);
                    break;
                case "del":
                    delUser(request, response);
                    break;
                case "login":
                    getUser(request, response);
                    break;
                case "logout":
                    request.getSession().invalidate();
                    Result result = new Result();
                    result.setSuccess("退出成功");
                    response.getWriter().write(JSON.toJSONString(result));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询个人信息
     */
    private void getUserInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        UserInfo userByAccount = userDao.getUserByAccount(userInfo.getWorknum());

        Result result = new Result();

        result.setData(userByAccount);
        result.setSuccess("成功");

        response.getWriter().write(JSON.toJSONString(result));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 查询用户
     */
    public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        String worknum = request.getParameter("worknum");
        String password = request.getParameter("password");

        UserInfo userByAccount = userDao.getUserByAccount(worknum);

        Result result = new Result();

        if(userByAccount == null){
            result.setError("用户不存在");
        }else{
            if(password.equals(userByAccount.getPassword())){
                result.setData(userByAccount);
                request.getSession().setAttribute("userInfo",userByAccount);
                result.setSuccess("成功");
            }else{
                result.setError("密码错误");
            }
        }
        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 查询所有用户
     */
    public void allUser(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        List<UserInfo> userInfos = userDao.getUsers();

        Result result = new Result();
        result.setData(userInfos);
        result.setCount(userInfos.size());
        result.setSuccess("成功");

        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 添加用户
     */
    public void saveUser(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException, IOException {

        UserInfo info = new UserInfo();

        Map<String, String[]> parameterMap = new HashMap<String,String[]>(request.getParameterMap());
        parameterMap.remove("method");

        BeanUtils.populate(info,parameterMap);

        Result result = new Result();
        if(info.getId()==null || info.getId() == 0){
            userDao.add(info);
            result.setSuccess("添加员工成功");
        }else{
            userDao.update(info);
            result.setSuccess("修改员工成功");
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 删除用户
     */
    public void delUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {


        Integer id = Integer.valueOf(request.getParameter("id"));

        Result result = new Result();

        userDao.delete(id);

        result.setSuccess("删除成功");

        response.getWriter().write(JSON.toJSONString(result));

    }

}
