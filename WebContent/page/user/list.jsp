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
    <form class="layui-form">
        <div class="layui-form-item layui-elem-quote">
            <button type="button" class="layui-btn layui-btn-normal btnAdd">+ 新增用户</button>
        </div>
    </form>
        <div class="layui-row">
            <div class="layui-col-lg12" id="mytable">
                <table class="layui-hide" id="tableList" lay-filter="tableList1"></table>
            </div>
        </div>
</div>
</body>
<script src="/layui/layui.js" charset="utf-8"></script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
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
            url: '/user?method=list',
            method:'post',
            skin: 'nob',
            size:'lg',
            cols: [[
                {field:'worknum', title: '用户名', align:'center'},
                {field:'password', title: '密码', align:'center'},
                {field:'name', title: '姓名', align:'center'},
                {field:'gender', title: '性别', align:'center',templet:function (item) {
                        if(item.gender === 0){
                            return '男';
                        }else{
                            return '女';
                        }
                    }},
                {field:'contact', title: '联系方式', align:'center'},
                {field:'residentialaddress', title: '居住地址', align:'center'},
                {fixed: 'right', width:180, title: '操作', align:'center', toolbar: '#barDemo'}
            ]],
            page:false
        });

        //监听工具条
        table.on('tool(tableList1)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('账号：'+data.username, {icon: 3, title:'是否确定删除?'}, function(index){
                    $.ajax({
                        url:'/user',
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
                    title: '修改员工',
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
                title: '新增员工',
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
