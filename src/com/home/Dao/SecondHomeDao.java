package com.home.Dao;

import com.home.pojo.SecondHome;
import com.home.utils.DBUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SecondHomeDao {

	private QueryRunner runner = null;
	private RowProcessor processor = null;

	public SecondHomeDao(){
		runner = new QueryRunner();
		//开启驼峰式转换
		BeanProcessor bean = new GenerousBeanProcessor();
		processor = new BasicRowProcessor(bean);
	}

	/**
	 * 删除二手房信息
	 * @param id
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException {
		String sql = "delete from second_home where id=?";
		runner.update(DBUtils.getConnection(), sql, id);
	}

	
	public List<SecondHome> getSecondHomes(HttpServletRequest request) throws SQLException {
		
		String sql = "select * from second_home";
		String keyword = request.getParameter("keyword");
		if(null != keyword && !("".equals(keyword))){
			sql += " where region like '%" + keyword + "%'";
		}
		return runner.query(DBUtils.getConnection(), sql, new BeanListHandler<>(SecondHome.class,processor));
	}

	/**
	 * 查询户型视图
	 */
	public List<Map<String,Object>> getDataByShiting() {

		String sql = "select count(1) as count,region,ROUND(avg(average_price+0),0) as avg_price,ROUND(avg(price+0),0) as price from second_home group by region;";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Map<String,Object>> orders = new ArrayList<>();

		try {
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			Map<String,Object> map = null;
			while (rs.next()) {
				map = new HashMap<>();

				Integer count = rs.getInt("count");
				map.put("count",count);

				String region = rs.getString("region");
				map.put("region",region);

				String avg_price = rs.getString("avg_price");
				map.put("avg_price",avg_price);

				String price = rs.getString("price");
				map.put("price",price);

				orders.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.clean(stmt, rs);
		}
		return orders;
	}

	/**
	 * 查询本月设备销售数量
	 *//*
	public List<Map<String,Object>> getOrdersByEquipmentMonth() {

		String sql = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<Map<String,Object>> orders = new ArrayList<>();

		try {
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			Map<String,Object> map = null;
			while (rs.next()) {
				map = new HashMap<>();
				Integer monthNum = rs.getInt("month_num");
				map.put("monthNum",monthNum);
				Integer equipmentId = rs.getInt("equipment_id");

				if(equipmentId != null){

					Equipment equipment = new EquipmentDao().getEquipmentById(equipmentId+"");
					map.put("equipment",equipment);
				}

				orders.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.clean(stmt, rs);
		}
		return orders;
	}*/
}
