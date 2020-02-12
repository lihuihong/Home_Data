<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@page language="java" import="java.util.*" errorPage="jsps/error.jsp"%>--%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="description" content="">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="/layui/css/layui.css" media="all">
	<link rel="stylesheet" href="/css/main.css" media="all">
</head>
<body>
<div class="layui-layout">
	<!-- 头部区域 -->
	<div class="layui-header">
		<ul class="box">
			<li class="header-left">
				<div class="layui-logo" lay-href="/">
					<span style="padding-left: 50px;font-size: 20px;letter-spacing: 5px;">房源信息管理系统</span>

				</div>
			</li>
			<li class="header-right">
				<a class="btnHeader refresh" title="刷新">
					<i class="layui-icon layui-icon-refresh-3" style="font-size: 14px"> 刷新</i>
				</a>
				<a class="btnHeader logout">安全退出</a>

				<span class="navHeader" style="display: block;text-align: center;color: red;">
					<cite>欢迎您：${sessionScope.userInfo.name}</cite>
				</span>

			</li>
		</ul>
	</div>
	<!-- 侧边菜单 -->
	<div class="layui-side">
		<div class="layui-side-scroll">
			<div class="layui-logo" lay-href="/page/main" style="padding: 20px;text-align: center;font-size: 15px;">
				<span>控 制 台</span>
			</div>
			<ul class="layui-nav layui-nav-tree" lay-filter="nav" id="menu">
					<li data-name="get" class="layui-nav-item  layui-this">
						<a href="javascript:;" lay-href="user/list.jsp" class="nav-tab" lay-id="user" data-type="tabChange" lay-tips="用户管理">
							<i class="layui-icon layui-icon-username"></i><label>用户管理</label>
						</a>
					</li>
					<li data-name="get" class="layui-nav-item">
						<a href="javascript:;" lay-href="newhome/list.jsp" class="nav-tab" lay-id="role" data-type="tabAdd" lay-tips="新房管理">
							<i class="layui-icon layui-icon-home"></i><label>新房管理</label>
						</a>
					</li>
					<li data-name="get" class="layui-nav-item">
						<a href="javascript:;" lay-href="renthome/list.jsp" class="nav-tab" lay-id="vcation" data-type="tabAdd" lay-tips="租房管理">
							<i class="layui-icon layui-icon-face-smile-b"></i><label>租房管理</label>
						</a>
					</li>
					<li data-name="get" class="layui-nav-item">
						<a href="javascript:;" lay-href="secondhome/list.jsp" class="nav-tab" lay-id="check" data-type="tabAdd" lay-tips="二手房管理">
							<i class="layui-icon layui-icon-flag"></i><label>二手房管理</label>
						</a>
					</li>
					<li data-name="get" class="layui-nav-item">
						<a href="javascript:;" lay-href="notice/list.jsp" class="nav-tab" lay-id="notice" data-type="tabAdd" lay-tips="二手房管理">
							<i class="layui-icon layui-icon-notice"></i><label>公告管理</label>
						</a>
					</li>
					<li data-name="get" class="layui-nav-item">
						<a href="javascript:;" lay-href="leave/list.jsp" class="nav-tab" lay-id="leave" data-type="tabAdd" lay-tips="二手房管理">
							<i class="layui-icon layui-icon-fire"></i><label>留言查看</label>
						</a>
					</li>
				</ul>
		</div>
	</div>
	<!-- 主体内容 -->
	<div class="layui-bodier">
		<div class="layui-tab" lay-filter="tab" lay-allowClose="true">
			<ul class="layui-tab-title">
				<li class="firstTab layui-this" lay-id="user">用户管理</li>
			</ul>
			<div class="layui-tab-content">
				<div class="layui-tab-item layui-show">
					<iframe src="user/list.jsp" frameborder="0" name="tabFrame" class="tabFrame"></iframe>
				</div>
			</div>
		</div>
	</div>
	<!-- 版权信息 -->
	<div class="layui-footer">
		<span>Copyright 房源信息管理系统</span>
	</div>
</div>
</body>
<script src="/layui/layui.js" charset="utf-8"></script>
<script src="/js/main.js" charset="utf-8"></script>
<script type="text/javascript">

	layui.use(['element'],function(){
		var $ = layui.jquery;

        //安全退出
        $('.logout').on('click',function(){
            layer.confirm('是否确定退出系统？', {icon: 3, title:'系统信息'}, function(index){
                $.ajax({
                    url:'/user?method=logout',
                    type:'post',
                    dataType:"json",
                    success:function(data){//do something
                        if(data.code===0){
                            layer.msg('安全退出！',{icon:1});
                            window.location.href = '/';
                        } else {
                            layer.alert(data.msg,{icon:2});
                        }
                    },
                    error:function(data){//do something
                        layer.msg('与服务器连接失败',{icon: 2});
                    }
                });
                layer.close(index);
            });
        });
	})
//个人资料
</script>
</html>