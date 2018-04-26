$(function() {
	$('#tb_orgs').bootstrapTable({
		url : '../../../user/findPage',
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
			field : 'username',
			title : '账号'
		}, {
			field : 'cnname',
			title : '姓名'
		}, {
			field : 'mobile',
			title : '手机号'
		} ]
	});
});