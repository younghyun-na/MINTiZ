// 유효성 검사
function joinform_check(){
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

    document.joinform_submit();
}

// id 중복체크
function id_check() {
    //window.open("팝업될 문서 경로", "팝업될 문서 이름", "옵션");
    //window.open("idCheckForm.jsp", "idCheck", "width=400, height=200, left=200, top=100");
}