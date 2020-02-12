package com.home.Dao;

import com.home.pojo.UserInfo;
import com.home.utils.DBUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class UserDao {


	private QueryRunner runner = null;
	private RowProcessor processor = null;

	public UserDao(){
		runner = new QueryRunner();
		//开启驼峰式转换
		BeanProcessor bean = new GenerousBeanProcessor();
		processor = new BasicRowProcessor(bean);
	}
	/**
	 *
	 * 根据用户名查询用户
	 */
	public UserInfo getUserByAccount(String worknum) throws SQLException {

		String sql = "select * from userinfo where worknum = ?";

		return runner.query(DBUtils.getConnection(), sql, new BeanHandler<>(UserInfo.class,processor),worknum);
		
	}

	/**
	 *
	 * 获取所有用户信息
	 */
	public List<UserInfo> getUsers() throws SQLException {

		String sql = "select * from userinfo";

		return runner.query(DBUtils.getConnection(), sql, new BeanListHandler<>(UserInfo.class,processor));

	}

	/**
	 * 添加用户
	 * @param userInfo
	 * @throws SQLException
	 */
	public void add(UserInfo userInfo) throws SQLException {

		String sql = "insert into userinfo(worknum,password,name,gender, contact, residentialaddress, ) values(?,?,?,?,?,?)";

		runner.update(DBUtils.getConnection(), sql, userInfo.getWorknum(), userInfo.getPassword(),userInfo.getName(),userInfo.getGender(),
				userInfo.getContact(),userInfo.getResidentialaddress());

	}


	/**
	 * 修改用户
	 * @param userInfo
	 * @throws SQLException
	 */
	public void update(UserInfo userInfo) throws SQLException {
		String sql = "update userinfo set worknum=?,password=?,name=?,gender=?,contact=?,residentialaddress=? where id=?";
		runner.update(DBUtils.getConnection(), sql, userInfo.getWorknum(), userInfo.getPassword(),userInfo.getName(),userInfo.getGender(),
				userInfo.getContact(),userInfo.getResidentialaddress(),userInfo.getId());
	}

	/**
	 * 删除用户
	 * @param id
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException {
		String sql = "delete from userinfo where id=?";
		runner.update(DBUtils.getConnection(), sql, id);
	}

}
