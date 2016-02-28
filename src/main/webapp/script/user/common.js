jQuery( document ).ready(function( $ ) {

    // initialization
    var thisTag = $("#this-tag").html();
    $("#" + thisTag).addClass("chosen");

    $(".unsupported-section").click(function(event){
        event.preventDefault();
        presentSuccessModal("抱歉！", "本功能仍然处于开发状态，敬请期待！");
    });

    $(".detail-image-slider .banner-slider ul li").show();

    $('.lr-toggle').on('click', function() {
        $('.lr-toggle').removeClass("shake-crazy");
        $('.lr-container').stop().addClass('active');
    });

    $('.lr-close').on('click', function() {
        $('.lr-toggle').addClass("shake-crazy");
        $('.lr-container').stop().removeClass('active');
    });

    $('.register-btn').on('click', function() {
        $("#animatedModal1").foundation("open");
        setTimeout(function(){$('.lr-toggle').trigger("click");}, 500);
    });

    $("#login-confirm").on('click', function() {
        event.preventDefault();
        $.ajax({
            url: "/auth/login-check",
            type: "POST",
            data: $("#login-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    $("#animatedModal1").foundation("close");
                    $("#username-section").html(data.userInfo.username + ", " + data.helloMsg);
                    $("#login-decide-content").html("登出");
                    $("#login-decide-url").attr("href", "/auth/logout").removeClass("register-btn");
                } else {
                    presentFailModal("抱歉！", "用户名和密码的组合不正确，请重新输入。");
                }
            }
        });
    });

    $("#register-confirm").on('click', function() {
        event.preventDefault();
        $.ajax({
            url: "/auth/register",
            type: "POST",
            data: $("#register-form").serialize(),
            dataType: 'json',
            success: function(data) {
                if (data.success == true || data.success == "true") {
                    presentSuccessModal("恭喜您！", "欢迎您加入桃源大家庭！请点击登陆。");
                } else {
                    presentFailModal("抱歉！", data.msg);
                }
            }
        });
    });

});
