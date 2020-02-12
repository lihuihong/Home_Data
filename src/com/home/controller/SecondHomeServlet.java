package com.home.controller;

import com.alibaba.fastjson.JSON;
import com.home.Dao.SecondHomeDao;
import com.home.pojo.SecondHome;
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


@WebServlet("/Secondhome")
public class SecondHomeServlet extends HttpServlet {

    private SecondHomeDao SecondHomeDao = new SecondHomeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String methodName = request.getParameter("method");
        try{
            switch (methodName) {
                case "list":
                    allSecondHome(request, response);
                    break;
                case "del":
                    delRegion(request, response);
                    break;
                case "dataByShiting":
                    getDataByShiting(request,response);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDataByShiting(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Map<String,Object>> datas = SecondHomeDao.getDataByShiting();

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
     * 查询所有二手房
     */
    public void allSecondHome(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer limit = Integer.parseInt(request.getParameter("limit"));

        List<SecondHome> SecondHomes = SecondHomeDao.getSecondHomes(request);

        int firstIndex = (page  - 1) * limit;
        int lastIndex = page * limit;
        if(SecondHomes.size() < lastIndex)
            lastIndex = SecondHomes.size();
        List<SecondHome> pageList = SecondHomes.subList(firstIndex, lastIndex);

        for(SecondHome SecondHome : pageList){
            SecondHome.setMianji(SecondHome.getMianji().replace("??","方"));
            SecondHome.setShiting(SecondHome.getShiting().replace("?",""));
        }


        Result result = new Result();
        result.setData(pageList);
        result.setCount(SecondHomes.size());
        result.setSuccess("成功");

        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 添加/修改 二手房
     *//*
    public void saveRegion(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException, IOException {

        Region region = Second Region();

        Map<String, String[]> parameterMap = Second HashMap<String,String[]>(request.getParameterMap());
        parameterMap.remove("method");

        BeanUtils.populate(region,parameterMap);

        String name = Second UserDao().getUserByAccount(parameterMap.get("worknum")[0]).getName();
        region.setName(name);


        Result result = Second Result();
        if(region.getId()==null || region.getId() == 0){
            RegionDao.add(region);
            result.setSuccess("添加二手房成功");
        }else{
            RegionDao.update(region);
            result.setSuccess("修改二手房成功");
        }

        response.getWriter().write(JSON.toJSONString(result));
    }*/

    /**
     * 删除二手房
     */
    public void delRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        Integer id = Integer.valueOf(request.getParameter("id"));
        Result result = new Result();
        SecondHomeDao.delete(id);
        result.setSuccess("删除成功");
        response.getWriter().write(JSON.toJSONString(result));

    }

}
