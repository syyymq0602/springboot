$('#getcode').click(function(){
    //发送验证码
    let email = $("#email").val();
    if(email===""){
        return;
    }
    alert("验证码已发送，请及时查看你的邮箱");
    $(".code").toggle();
    //ajax实现后台邮箱的发送
    $.ajax({
        //url路径
        url: "http://localhost:8080/register/sendEmail",
        //data请求数据
        data: {"email": email},
        //dataType json
        type: "post",
        //回调函数
        success: function (data) {
            //回调函数 data 返回流
            if (data === "failure") {
                alert("发送失败");
            }
            else if (data==="false"){
                alert("目标邮箱不存在，请检查你的邮箱是否正确");
            }
        }
    });
    validate()
});

function validate() {
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