package com.home.controller;

import com.alibaba.fastjson.JSON;
import com.home.Dao.RentHomeDao;
import com.home.pojo.RentHome;
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


@WebServlet("/renthome")
public class RentHomeServlet extends HttpServlet {

    private RentHomeDao rentHomeDao = new RentHomeDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String methodName = request.getParameter("method");
        try{
            switch (methodName) {
                case "save":
//                    saveRegion(request, response);
                    break;
                case "list":
                    allRentHome(request, response);
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

        List<Map<String,Object>> datas = rentHomeDao.getDataByRegion();

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
     * 查询所有租房
     */
    public void allRentHome(HttpServletRequest request, HttpServletResponse response) throws  SQLException, IOException {

        Integer page = Integer.parseInt(request.getParameter("page"));
        Integer limit = Integer.parseInt(request.getParameter("limit"));

        List<RentHome> rentHomes = rentHomeDao.getRentHomes(request);

        int firstIndex = (page  - 1) * limit;
        int lastIndex = page * limit;

        if(rentHomes.size() < lastIndex)
            lastIndex = rentHomes.size();
        List<RentHome> pageList = rentHomes.subList(firstIndex, lastIndex);

        for(RentHome rentHome : pageList){
            rentHome.setMianji(rentHome.getMianji().replace("??","方"));
            rentHome.setShiting(rentHome.getShiting().replace("?",""));
        }


        Result result = new Result();
        result.setData(pageList);
        result.setCount(rentHomes.size());
        result.setSuccess("成功");

        response.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 添加/修改 租房
     *//*
    public void saveRegion(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException, IOException {

        Region region = new Region();

        Map<String, String[]> parameterMap = new HashMap<String,String[]>(request.getParameterMap());
        parameterMap.remove("method");

        BeanUtils.populate(region,parameterMap);

        String name = new UserDao().getUserByAccount(parameterMap.get("worknum")[0]).getName();
        region.setName(name);


        Result result = new Result();
        if(region.getId()==null || region.getId() == 0){
            RegionDao.add(region);
            result.setSuccess("添加租房成功");
        }else{
            RegionDao.update(region);
            result.setSuccess("修改租房成功");
        }

        response.getWriter().write(JSON.toJSONString(result));
    }*/

    /**
     * 删除租房
     */
    public void delRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        Integer id = Integer.valueOf(request.getParameter("id"));
        Result result = new Result();
        rentHomeDao.delete(id);
        result.setSuccess("删除成功");
        response.getWriter().write(JSON.toJSONString(result));

    }

}
