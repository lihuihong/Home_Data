package com.home.controller;

import com.alibaba.fastjson.JSON;
import com.home.Dao.LeaveDao;
import com.home.pojo.Leave;
import com.home.pojo.Result;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/leave")
public class LeaveServlet extends HttpServlet {

    private LeaveDao LeaveDao = new LeaveDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String methodName = request.getParameter("method");
        try{
            switch (methodName) {
                case "save":
                    save(request, response);
                    break;
                case "list":
                    all(request, response);
                    break;
                case "del":
                    del(request, response);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }



    /**
     * 查询所有留言
     */
    public void all(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        List<Leave> Leaves = LeaveDao.list();

        String pageStr = request.getParameter("page");
        String limitStr = request.getParameter("limit");
        
        Result result = new Result();
        result.setCount(Leaves.size());
        result.setSuccess("成功");

        if(null == pageStr || null == limitStr ||"".equals(pageStr) || "".equals(limitStr)){
            result.setData(Leaves);
        }else{
            Integer page = Integer.parseInt(pageStr);
            Integer limit = Integer.parseInt(limitStr);
            int firstIndex = (page  - 1) * limit;
            int lastIndex = page * limit;
            if(Leaves.size() < lastIndex)
                lastIndex = Leaves.size();
            List<Leave> pageList = Leaves.subList(firstIndex, lastIndex);
            result.setData(pageList);
        }

        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 添加留言
     */
    public void save(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException, IOException {

        Leave info = new Leave();

        Map<String, String[]> parameterMap = new HashMap<String,String[]>(request.getParameterMap());
        parameterMap.remove("method");

        BeanUtils.populate(info,parameterMap);

        Result result = new Result();
        if(info.getId()==null || info.getId() == 0){
            info.setTime(new Date());
            LeaveDao.add(info);
            result.setSuccess("添加留言成功");
        }else{
            LeaveDao.update(info);
            result.setSuccess("修改留言成功");
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 删除留言
     */
    public void del(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        Integer id = Integer.valueOf(request.getParameter("id"));
        Result result = new Result();
        LeaveDao.delete(id);
        result.setSuccess("删除成功");
        response.getWriter().write(JSON.toJSONString(result));

    }

}
