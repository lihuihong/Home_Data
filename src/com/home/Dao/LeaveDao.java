package com.home.Dao;

import com.home.pojo.Leave;
import com.home.utils.DBUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;


public class LeaveDao {


	private QueryRunner runner = null;
	private RowProcessor processor = null;

	public LeaveDao(){
		runner = new QueryRunner();
		//开启驼峰式转换
		BeanProcessor bean = new GenerousBeanProcessor();
		processor = new BasicRowProcessor(bean);
	}

	/**
	 *
	 * 获取所有通知信息
	 */
	public List<Leave> list() throws SQLException {

		String sql = "select * from `leave`";

		return runner.query(DBUtils.getConnection(), sql, new BeanListHandler<>(Leave.class,processor));

	}

	/**
	 * 添加留言
	 * @param leave
	 * @throws SQLException
	 */
	public void add(Leave leave) throws SQLException {

		String sql = "insert into `leave`(name, phone, content, time) values(?,?,?,?)";

		runner.update(DBUtils.getConnection(), sql,leave.getName(), leave.getPhone(), leave.getContent(), leave.getTime());

	}


	/**
	 * 修改留言
	 * @param leave
	 * @throws SQLException
	 */
	public void update(Leave leave) throws SQLException {
		String sql = "update `leave` set name=?, phone=?, content=? where id=?";
		runner.update(DBUtils.getConnection(), sql,leave.getName(), leave.getPhone(), leave.getContent(), leave.getId());
	}

	/**
	 * 删除留言
	 * @param id
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException {
		String sql = "delete from `leave` where id=?";
		runner.update(DBUtils.getConnection(), sql, id);
	}

}
