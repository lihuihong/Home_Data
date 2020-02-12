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
<script type="text/html" id="barDemo">
    <%--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--%>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
<script>

    layui.use(["table",'form'],function(){
        var table = layui.table;
        var $ = layui.jquery;
        var form = layui.form;

        //加载表格
        table.render({
            elem: '#tableList',
            url: '/Secondhome?method=list',
            method:'post',
            skin: 'nob',
            size:'lg',
            cols: [[
                {field:'xiaoqu', title: '小区', align:'center'},
                {field:'shiting', title: '厅室', align:'center'},
                {field:'mianji', title: '面积', align:'center'},
                {field:'chaoxiang', title: '朝向',width:60,align:'center'},
                {field:'zhuangxiu', title: '装修',width:60,align:'center'},
                {field:'dianti', title: '电梯',align:'center'},
                {field:'louceng', title: '楼层',align:'center'},
                {field:'region', title: '地区', align:'center'},
                {field:'price', title: '总价',width:60, align:'center'},
                {field:'averagePrice', title: '平方价格', align:'center'},
                {field:'title', title: '介绍', align:'center'},
                {fixed: 'right', width:60, title: '操作', align:'center', toolbar: '#barDemo'}
            ]],
            page:true
        });

        //监听工具条
        table.on('tool(tableList1)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('编号：'+data.id, {icon: 3, title:'是否确定删除?'}, function(index){
                    $.ajax({
                        url:'/second',
                        type:'post',
                        data:{'id':data.id,"method":"del"},
                        dataType:"json",
                        success:function(data){//do something
                            if(data.code===0){
                                layer.msg('恭喜，删除成功！',{icon:1});
                            } else {
                                layer.alert(data.msg,{icon:2});
                            }
                            layer.close(index);
                            layui.table.reload('tableList');
                        },
                        error:function(data){//do something
                            layer.msg('与服务器连接失败',{icon: 2});
                        }
                    });
                });
            } else if(obj.event === 'edit'){
                layer.open({
                    title: '修改订单信息',
                    type: 2,
                    shade: false,
                    area: ['750px', '460px'],
                    maxmin: true,
                    btnAlign: 'c',
                    anim: 0,
                    shade: [0.5, 'rgb(0,0,0)'],
                    content: 'edit.jsp',
                    zIndex: layer.zIndex, //重点1
                    success: function(layero,index){
                        // 获取子页面的iframe
                        var iframe = window['layui-layer-iframe' + index];
                        // 向子页面的全局函数child传参
                        iframe.child(data);
                    }
                });
            }
        });
        $('.btnAdd').on('click',function(){
            layer.open({
                title: '新增订单信息',
                type: 2,
                shade: false,
                area: ['750px', '460px'],
                maxmin: true,
                btnAlign: 'c',
                anim: 0,
                shade: [0.5, 'rgb(0,0,0)'],
                content: 'edit.jsp',
                zIndex: layer.zIndex, //重点1
                success: function(layero, index){
                    //layer.setTop(layero); //顶置窗口
                },
            });
        });
    })
</script>

</html>
