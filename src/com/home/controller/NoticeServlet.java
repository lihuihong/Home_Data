package com.home.controller;

import com.alibaba.fastjson.JSON;
import com.home.Dao.NoticeDao;
import com.home.pojo.Result;
import com.home.pojo.Notice;
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


@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {

    private NoticeDao NoticeDao = new NoticeDao();

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
                case "first":
                    first(request, response);
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
     * 查询第一条消息
     */
    public void first(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        Notice notice = NoticeDao.first();

        Result result = new Result();
        result.setCount(1);
        result.setSuccess("成功");
        result.setData(notice);

        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 查询所有消息
     */
    public void all(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        List<Notice> notices = NoticeDao.list();

        String pageStr = request.getParameter("page");
        String limitStr = request.getParameter("limit");


        Result result = new Result();
        result.setCount(notices.size());
        result.setSuccess("成功");

        if(null == pageStr || null == limitStr ||"".equals(pageStr) || "".equals(limitStr)){
            result.setData(notices);
        }else{
            Integer page = Integer.parseInt(pageStr);
            Integer limit = Integer.parseInt(limitStr);
            int firstIndex = (page  - 1) * limit;
            int lastIndex = page * limit;
            if(notices.size() < lastIndex)
                lastIndex = notices.size();
            List<Notice> pageList = notices.subList(firstIndex, lastIndex);
            result.setData(pageList);
        }

        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 添加消息
     */
    public void save(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException, IOException {

        Notice info = new Notice();

        Map<String, String[]> parameterMap = new HashMap<String,String[]>(request.getParameterMap());
        parameterMap.remove("method");

        BeanUtils.populate(info,parameterMap);

        Result result = new Result();
        if(info.getId()==null || info.getId() == 0){
            info.setTime(new Date());
            NoticeDao.add(info);
            result.setSuccess("添加消息成功");
        }else{
            NoticeDao.update(info);
            result.setSuccess("修改消息成功");
        }

        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 删除消息
     */
    public void del(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        Integer id = Integer.valueOf(request.getParameter("id"));
        Result result = new Result();
        NoticeDao.delete(id);
        result.setSuccess("删除成功");
        response.getWriter().write(JSON.toJSONString(result));

    }

}
