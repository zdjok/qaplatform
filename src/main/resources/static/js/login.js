$(function(){
	$("#email").on("blur", function(){
		validateEmail();
	});
	
	$("#password").on("blur", function(){
		validatePassword();
	});
	
	$("#regemail").on("blur", function(){
		validateRegEmail();
	});
	
	$("#regpassword").on("blur", function(){
		validateRegPassword();
	});
	
	$("#regpassword2").on("blur", function(){
		validateRegPassword2();
	});
	
	$("#rstemail").on("blur", function(){
		validateRstEmail();
	});
	
	$("#loginBtn").on("click", function(){
		return validateEmail() && validatePassword();
	});
	
	$("#registBtn").on("click", function(){
		return validateRegEmail() && validateRegPassword() && validateRegPassword2();
	});
	
	$("#rstBtn").on("click", function(){
		return validateRstEmail();
	});
});


function validateEmail(){
	return validateEmpty("email");
}

function validatePassword(){
	return validateEmpty("password");
}

function validateRegEmail(){
	return validateEmpty("regemail");
}

function validateRegPassword(){
	return validateEmpty("regpassword");
}

function validateRegPassword2(){
	return validateEmpty("regpassword2");
}

function validateRstEmail(){
	return validateEmpty("rstemail");
}


/**
 * 验证输入数据是否为空，如果为空则提示错误信息
 * @param eleId
 */
function validateEmpty(eleId){
	if($("#" + eleId).val() == ""){
		$("#" + eleId + "Div").attr("class","form-group has-error");
		$("#" + eleId + "Span").html("<span class='text-danger'>该字段的内容不能为空！</span>");
		return false;
	} else{
		$("#" + eleId + "Div").attr("class","form-group has-success");
		$("#" + eleId + "Span").html("<span class='text-success'></span>");
		return true;
	}
}
