$(function() {
	$('#tb_orgs').bootstrapTable({
		url : '../../../org/findPage',
		method : 'get',
		toolbar : '#toolbar',
		striped : true,
		cache : false,
		pagination : true,
		sortable : true,
		sortOrder : "asc",
		queryParams : function (params) {
			return {   
	            limit: params.limit,  
	            offset: params.offset
            };
		},
		sidePagination : "server",
		pageList : [ 10, 50, 100 ],
		search : false,
		strictSearch : true,
		showColumns : false,
		toolbarAlign: 'right',
		showRefresh : false,
		minimumCountColumns : 2,
		clickToSelect : true,
		height : 500,
		uniqueId : "id",
		showToggle : false,
		cardView : false,
		detailView : false,
		onLoadError:function(status){
			alert(status);
		},
		totalField : 'totalCount',
		dataField : 'elements',
		responseHandler:function(resa){
			if(resa && resa.suc){
				return resa.data;
			}
		},
		columns : [ {
			checkbox : true
		}, {
			field : 'org_name',
			title : '部门名称'
		}, {
			field : 'pid',
			title : '上级部门'
		}, {
			field : 'sn',
			title : '部门标识'
		} ]
	});
});