function logout(){
    $.ajax({
        type : "POST",
        url : "/user/logout",
        async:false,
        success: function(){
            alert("로그아웃");
            location.href="/user/login";
        },
        error : function () {
            alert('죄송합니다. 잠시 후 다시 시도해주세요.');
            return false;
        }
    });

    console.log("취소");
}