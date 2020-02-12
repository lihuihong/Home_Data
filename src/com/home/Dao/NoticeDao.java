package com.home.Dao;

import com.home.pojo.Notice;
import com.home.utils.DBUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;


public class NoticeDao {


	private QueryRunner runner = null;
	private RowProcessor processor = null;

	public NoticeDao(){
		runner = new QueryRunner();
		//开启驼峰式转换
		BeanProcessor bean = new GenerousBeanProcessor();
		processor = new BasicRowProcessor(bean);
	}

	/**
	 *
	 * 获取所有通知信息
	 */
	public List<Notice> list() throws SQLException {

		String sql = "select * from notice";

		return runner.query(DBUtils.getConnection(), sql, new BeanListHandler<>(Notice.class,processor));

	}

	/**
	 * 添加新闻
	 * @param Notice
	 * @throws SQLException
	 */
	public void add(Notice Notice) throws SQLException {

		String sql = "insert into notice(content,time) values(?,?)";

		runner.update(DBUtils.getConnection(), sql, Notice.getContent(), Notice.getTime());

	}


	/**
	 * 修改新闻
	 * @param Notice
	 * @throws SQLException
	 */
	public void update(Notice Notice) throws SQLException {
		String sql = "update notice set content=? where id=?";
		runner.update(DBUtils.getConnection(), sql, Notice.getContent(), Notice.getId());
	}

	/**
	 * 删除新闻
	 * @param id
	 * @throws SQLException
	 */
	public void delete(int id) throws SQLException {
		String sql = "delete from notice where id=?";
		runner.update(DBUtils.getConnection(), sql, id);
	}

    public Notice first() throws SQLException {

		String sql = "select * from notice order by time desc limit 1";

		return  runner.query(DBUtils.getConnection(), sql, new BeanHandler<>(Notice.class));
    }
}
