<!DOCTYPE html>
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<%--日期插件--%>
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<%--分页插件--%>
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
		$("#addBtn").click(function () {
			//发送ajax请求获取userList填充所有者 显示name value 为 id
			$.ajax({
				url : "workbench/activity/getUserList.do",
				type : "post",
				data : {
				},
				dataType : "json",
				success : function (data) {
					//填充所有者内容
					var html = "";
					$.each(data,function (index,element) {
						html += "<option value='"+ element.id +"' >" + element.name + " </option>";
					})
					$("#create-owner").html(html);
					$("#create-owner").val("${sessionScope.user.id}");
				}
			})
			//显示模态框
			$("#createActivityModal").modal("show");
		})
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
		pageList(1,2);
		$("#searchBtn").click(function () {
			pageList(1,$("#rows_per_page_activityPage").val());
		})

		$("#qx").click(function () {
			$(":input[name=xc]").prop("checked",this.checked);
		})

		$("#activityBody").on("click",$("input[name=xc]"),function () {
			$("#qx").prop("checked",$("input[name=xc]").length === $("input[name=xc]:checked").length);
		})

		$("#editBtn").click(function () {

			var $xc = $("input[name=xc]:checked");
			if ($xc.length < 1) {
				alert("请选择要修改的记录");
				return;
			} else if ($xc.length > 1) {
				alert("只能选择一条记录修改");
				return;
			}
			$.ajax({
				url : "workbench/activity/getUserListAndActivity.do",
				type : "post",
				data : {"id" : $xc.val()},
				dataType : "json",
				success : function (data) {		//{"userList":"{}","activity":"{}"}
					//填充所有者内容
					var html = "";
					$.each(data.userList,function (index,element) {
						html += "<option value='"+ element.id +"' >" + element.name + " </option>";
					})
					$("#edit-id").val(data.activity.id);
					$("#edit-owner").html(html);
					$("#edit-owner").val(data.activity.owner);
					$("#edit-name").val(data.activity.name);
					$("#edit-startDate").val(data.activity.startDate);
					$("#edit-endDate").val(data.activity.endDate);
					$("#edit-cost").val(data.activity.cost);
					$("#edit-description").val(data.activity.description);
				}
			})
			$("#editActivityModal").modal("show");
		})


		$("#update").click(function () {
			//发送ajax请求修改一条数据
			$.ajax({
				url: "workbench/activity/update.do",
				type: "post",
				data: {
					"id": $.trim($("#edit-id").val()),
					"owner": $.trim($("#edit-owner").val()),
					"name": $.trim($("#edit-name").val()),
					"startDate": $.trim($("#edit-startDate").val()),
					"endDate": $.trim($("#edit-endDate").val()),
					"cost": $.trim($("#edit-cost").val()),
					"description": $.trim($("#edit-description").val())
				},
				dataType: "json",
				success: function (data) {
					if (data.success) {
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						//隐藏模态框
						$("#editActivityModal").modal("hide");
					} else {
						alert("修改市场活动失败");
					}
				}
			});
		})

		$("#delete").click(function () {
			var $xc = $("input[name=xc]:checked");
			if ($xc.length < 1) {
				alert("请选择要删除的记录");
				return;
			}
			if (confirm("是否删除选定的记录条数?")){
				var param = "";
				for (var i = 0; i < $xc.length; i++) {
					param += "id=" + $xc[i].value;
					if (i < $xc.length-1) {
						param += "&";
					}
				}
				$.ajax({
					url : "workbench/activity/delete.do",
					type : "post",
					data : param,
					dataType : "json",
					success : function (data) {		//{"success":false/true}
						if (data.success) {
							//刷新到第一页
							pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						} else {
							alert("删除失败");
						}
					}
				})
			}
		})
	});

	function pageList(pageNo,pageSize) {

		$.ajax({
			url: "workbench/activity/pageList.do",
			type: "post",
			data: {
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $("#search-name").val(),
				"owner" : $("#search-owner").val(),
				"startDate" : $("#search-startDate").val(),
				"endDate" : $("#search-endDate").val(),
			},
			dataType: "json",				//"{\"totalSize\":\"100\",\"dataList\":\"\"}";
			success: function (data) {
				var totalSize = data.totalSize;
				var number = (parseInt(pageNo)-1) * pageSize;
				var html = "";
				$.each(data.dataList,function (index,element) {
					var no = number + (index + 1);
					html += '<tr class="active">';
					html += '	<td><input type="checkbox" name="xc" value="'+ element.id +'"/></td>';
					html += '	<td>'+ no +'</td>';
					html += '	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'detail.html\';">'+ element.name +'</a></td>';
					html += '	<td>'+ element.owner +'</td>';
					html += '	<td>'+ element.startDate +'</td>';
					html += '	<td>'+ element.endDate +'</td>';
					html += '</tr>';
				});
				$("#activityBody").html(html);

				var totalPages = totalSize % pageSize === 0 ? totalSize / pageSize : parseInt(totalSize / pageSize) + 1;

				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: totalSize, // 总记录条数

					visiblePageLinks: 5, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						$("#search-name").val($("#hidden-name").val());
						$("#search-owner").val($("#hidden-owner").val());
						$("#search-startDate").val($("#hidden-startDate").val());
						$("#search-endDate").val($("#hidden-endDate").val());
						$("#rows_per_page_activityPage").val($("#hidden-rows_per_page_activityPage").val());
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

				$("#hidden-name").val($("#search-name").val());
				$("#hidden-owner").val($("#search-owner").val());
				$("#hidden-startDate").val($("#search-startDate").val());
				$("#hidden-endDate").val($("#search-endDate").val());
				$("#hidden-rows_per_page_activityPage").val($("#rows_per_page_activityPage").val());
			}
		});

	}
	function doSave() {
		//发送ajax请求插入一条数据
		$.ajax({
			url: "workbench/activity/save.do",
			type: "post",
			data: {
				"id": $.trim($("#create-id").val()),
				"owner": $.trim($("#create-owner").val()),
				"name": $.trim($("#create-name").val()),
				"startDate": $.trim($("#create-startDate").val()),
				"endDate": $.trim($("#create-endDate").val()),
				"cost": $.trim($("#create-cost").val()),
				"description": $.trim($("#create-description").val())
			},
			dataType: "json",
			success: function (data) {
				if (data.success) {
					pageList(1,$("#rows_per_page_activityPage").val());
				} else {
					alert("添加市场活动失败");
				}
			}
		});
		//清除form中的数据
		//隐藏模态框
		$("#createActivityModal").modal("hide");
	}

</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" id="create-form">
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="doSave()" >保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">

						<input type="hidden" id="edit-id" />
					
						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
								</select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startDate">
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endDate">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="update">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<%--隐藏域,解决点击组件自动把输入框的内容发送ajax--%>
	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startDate">
	<input type="hidden" id="hidden-endDate">
	<input type="hidden" id="hidden-rows_per_page_activityPage">


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="search-name">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="searchBtn" >查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="delete"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>序号</td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
		</div>
		
	</div>
</body>
</html>