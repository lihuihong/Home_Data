package com.home.Dao;

import com.home.pojo.NewHome;
import com.home.utils.DBUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewHomeDao {

	private QueryRunner runner = null;
	private RowProcessor processor = null;

	public NewHomeDao(){
		runner = new QueryRunner();
		//开启驼峰式转换
		BeanProcessor bean = new GenerousBeanProcessor();
		processor = new BasicRowProcessor(bean);
	}

	/**
	 * 添加新房
	 * @param rent
	 * @throws SQLException
	 */
	/*public void add(NewHome rent) throws SQLException {

		String sql = "insert into rent_home(worknum,name,resaar) values(?,?,?)";
		runner.update(DBUtils.getConnection(), sql, region.getWorknum(), region.getName(),region.getResaar());

	}*/

	/**
	 * 修改新房
	 * @param region
	 * @throws SQLException
	 */
//	public void update(NewHome region) throws SQLException {
//		String sql = "update rent_home set worknum=?,name=?,resaar=? where id=?";
//		runner.update(DBUtils.getConnection(), sql, region.getWorknum(), region.getName(),region.getResaar(),region.getId());
//	}

	/**
	 * 删除新房
	 * @param id
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException {
		String sql = "delete from new_home where id=?";
		runner.update(DBUtils.getConnection(), sql, id);
	}

	
	public List<NewHome> getNewHomes(HttpServletRequest request) throws SQLException {

		String sql = "select * from new_home";
		String keyword = request.getParameter("keyword");
		if(null != keyword && !("".equals(keyword))){
			sql += " where region like '%" + keyword + "%'";
		}

		return runner.query(DBUtils.getConnection(), sql, new BeanListHandler<>(NewHome.class,processor));

	}

    public List<Map<String,Object>> getDataByRegion() {

		String sql = "select region,count(1) as count,ROUND(avg(price+0),0) as price from new_home group by region;";
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
}
