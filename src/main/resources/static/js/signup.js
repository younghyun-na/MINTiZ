// 아이디 중복 체크
function checkId(){
        var id = $('#id').val(); // id값이 "id"인 입력란의 값을 저장
        $.ajax({
            url:'/user/idCheck', // Controller에서 인식할 주소
            type:'post', //POST 방식으로 전달
            data:{id:id},
            success:function(cnt){       //컨트롤러에서 넘어온 cnt값
                if(cnt != 1){      // 사용 가능한 아이디
                    $('.id_ok').css("display","inline-block");
                    $('.id_already').css("display", "none");
                } else {          // cnt == 1 -> 이미 존재하는 아이디
                    $('.id_already').css("display","inline-block");
                    $('.id_ok').css("display", "none");
                }
            },
            error:function(){
                alert("에러입니다");
            }
        });
};

// 유효성 검사
function join_form_check(){
    var set_email = document.getElement("set_email");
    var set_name = document.getElement("set_name");
    var set_id = document.getElement("set_id");
    var set_pw = document.getElement("set_pw");
    var check_pw = document.getElement("check_pw");

    if (set_email.value == ""){
        alert("이메일의 주소를 입력하세요.");
        set_email.focus();
        return false;
    }

    if (set_name.value == ""){
        alert("이름을 입력하세요.");
        set_name.focus();
        return false;
    }

    if (set_id.value == ""){
        alert("아이디를 입력하세요.");
        set_id.focus();
        return false;
    }

    if (set_pw.value == ""){
        alert("비밀번호를 입력하세요.");
        set_pw.focus();
        return false;
    }

    if (check_pw.value !== set_pw.value){
        alert("비밀번호가 일치하지 않습니다.");
        check_pw.focus();
        return false;
    }

    document.join_form.submit();

}
/*
// id 중복체크
function id_check() {
    //window.open("팝업될 문서 경로", "팝업될 문서 이름", "옵션");
    //window.open("idCheckForm.jsp", "idCheck", "width=400, height=200, left=200, top=100");
}
*/
