$('#getcode').click(function(){
    //发送验证码
    let email = $("#email").val();
    let username = $("#username").val();
    if(email===""){
        return;
    }
    alert("验证码已发送，请及时查看你的邮箱");
    $(".code").toggle();
    //ajax实现后台邮箱的发送
    $.ajax({
        //url路径
        url: "http://localhost:8080/validate/forget",
        //data请求数据
        data: {
            "username": username,
            "email": email
        },
        //dataType json
        type: "post",
        //回调函数
        success: function (data) {
            //回调函数 data 返回流
            if (data === "failure") {
                alert("发送失败");
            }
        }
    });
    validated()
});

function validated(){
    let btn = $('#getcode');
    let count = 60;
    let resend = setInterval(function(){
        count--;
        if (count > 0){
            btn.val(count+"秒后可重新获取");
        }else {
            clearInterval(resend);
            btn.val("获取验证码").removeAttr('disabled style');
        }
    }, 1000);
    btn.attr('disabled',true).css('cursor','not-allowed');
}