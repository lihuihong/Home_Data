<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/1/21
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <style>
        .layui-table-view{border-radius: 5px;box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);background: #fff;}
        .card{
            border-radius: 5px;box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);background: #fff;
            margin: 10px 0px 10px 20px;
            padding: 20px;
            display: none;
        }
        .layui-form-label{text-align: left;}
        .title{padding-bottom: 10px;color: #1e9fff;margin-bottom: 20px;border-bottom: 1px solid;}
    </style>

</head>
<body>
<div>
        <div class="layui-row">
            <div class="layui-col-lg12" id="mytable">
                <table class="layui-hide" id="tableList" lay-filter="tableList1"></table>
            </div>
        </div>
</div>
</body>
<script src="/layui/layui.js" charset="utf-8"></script>
<script>

    var data;
    function child(d) {
        data = d;
        //alert(data);
    }

    layui.use(["table",'form'],function(){
        var table = layui.table;
        var $ = layui.jquery;

        if("新房" == data.type){
            //加载表格
            table.render({
                elem: '#tableList',
                url: '/newhome?method=list&keyword='+data.keyword,
                method:'post',
                skin: 'nob',
                size:'lg',
                cols: [[
                    {field:'mianji', title: '面积', align:'center'},
                    {field:'price', title: '价格', align:'center'},
                    {field:'region', title: '地区', align:'center'},
                    {field:'shiting', title: '厅室', align:'center'},
                    {field:'street', title: '街道', align:'center'}
                ]],
                page:true
            });
        }else if("租房" == data.type){
            table.render({
                elem: '#tableList',
                url: '/renthome?method=list&keyword='+data.keyword,
                method:'post',
                skin: 'nob',
                size:'lg',
                cols: [[
                    {field:'ceng', title: '楼层', align:'center'},
                    {field:'chaoxiang', title: '朝向', align:'center'},
                    {field:'kanguo', title: '看过',width: '7%',align:'center'},
                    {field:'mianji', title: '面积',width: '7%', align:'center'},
                    {field:'price', title: '价格',width: '7%', align:'center'},
                    {field:'region', title: '地区', align:'center'},
                    {field:'shiting', title: '厅室', align:'center'},
                    {field:'street', title: '街道', align:'center'},
                    {field:'title', title: '介绍', align:'center'},
                    {field:'xiaoqu', title: '小区', align:'center'}
                ]],
                page:true
            });
        }else if("二手房" == data.type){
            table.render({
                elem: '#tableList',
                url: '/Secondhome?method=list&keyword='+data.keyword,
                method:'post',
                skin: 'nob',
                size:'lg',
                cols: [[
                    {field:'xiaoqu', title: '小区', align:'center'},
                    {field:'shiting', title: '厅室', align:'center'},
                    {field:'mianji', title: '面积', align:'center'},
                    {field:'chaoxiang', title: '朝向', align:'center'},
                    {field:'zhuangxiu', title: '装修',align:'center'},
                    {field:'dianti', title: '电梯',align:'center'},
                    {field:'louceng', title: '楼层',align:'center'},
                    {field:'region', title: '地区', align:'center'},
                    {field:'price', title: '总价', align:'center'},
                    {field:'averagePrice', title: '平方价格', align:'center'},
                    {field:'title', title: '介绍', align:'center'}
                ]],
                page:true
            });
        }



    })
</script>

</html>
