package com.home.controller;

import com.alibaba.fastjson.JSON;
import com.home.Dao.NewHomeDao;
import com.home.pojo.NewHome;
import com.home.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@WebServlet("/newhome")
public class NewHomeServlet extends HttpServlet {

    private NewHomeDao NewHomeDao = new NewHomeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String methodName = request.getParameter("method");
        try{
            switch (methodName) {
                case "list":
                    allNewHome(request, response);
                    break;
                case "del":
                    delRegion(request, response);
                    break;
                case "dataByRegion":
                    getDataByRegion(request,response);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDataByRegion(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Map<String,Object>> datas = NewHomeDao.getDataByRegion();

        String pageStr = request.getParameter("page");
        String limitStr = request.getParameter("limit");


        Result result = new Result();

        result.setCount(datas.size());
        result.setSuccess("成功");

        if(null == pageStr || null == limitStr ||"".equals(pageStr) || "".equals(limitStr)){
            result.setData(datas);
        }else{
            Integer page = Integer.parseInt(pageStr);
            Integer limit = Integer.parseInt(limitStr);
            int firstIndex = (page  - 1) * limit;
            int lastIndex = page * limit;
            if(datas.size() < lastIndex)
                lastIndex = datas.size();
            List<Map<String,Object>> pageList = datas.subList(firstIndex, lastIndex);
            result.setData(pageList);
        }

        response.getWriter().write(JSON.toJSONString(result));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 查询所有新房
     */
    public void allNewHome(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer limit = Integer.parseInt(request.getParameter("limit"));

        List<NewHome> NewHomes = NewHomeDao.getNewHomes(request);

        int firstIndex = (page  - 1) * limit;
        int lastIndex = page * limit;
        if(NewHomes.size() < lastIndex)
            lastIndex = NewHomes.size();
        List<NewHome> pageList = NewHomes.subList(firstIndex, lastIndex);

        for(NewHome NewHome : pageList){
            NewHome.setMianji(NewHome.getMianji().replace("??","方"));
            NewHome.setShiting(NewHome.getShiting().replace("?",""));
        }

        Result result = new Result();
        result.setData(pageList);
        result.setCount(NewHomes.size());
        result.setSuccess("成功");

        response.getWriter().write(JSON.toJSONString(result));
    }


    /**
     * 删除新房
     */
    public void delRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        Integer id = Integer.valueOf(request.getParameter("id"));
        Result result = new Result();
        NewHomeDao.delete(id);
        result.setSuccess("删除成功");
        response.getWriter().write(JSON.toJSONString(result));

    }

}
