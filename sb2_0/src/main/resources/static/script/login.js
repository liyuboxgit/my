$(function() {
	$('<div>').appendTo($(document.body)).load('./public/model.html');	
	$('button[name=submit]').click(function() {
		var username = $('input[name=username]').val();
		var password = $('input[name=password]').val();
		$.ajax({
			url : '../../login',
			data : {
				username : username,
				password : password
			},
			type : 'post',
			success : function(ret) {
				if (ret.suc) {
					location.href = "./main.html";
				} else {
					$(".modal-title", "#myModal").text(ret.msg);
					$(".modal-body", "#myModal").text(ret.data);
					$("#alertbtn").click();
				}
			}
		});
	});
})