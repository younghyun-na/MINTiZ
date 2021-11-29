//이메일 중복 체크
function email_check(){
    var email = $('#email').val(); // id값이 "id"인 입력란의 값을 저장
    var emailVO = new Object();
    emailVO.email = email;
    $.ajax({
        url:'/user/emailCheck', // Controller에서 인식할 주소
        type:'post', //POST 방식으로 전달
        data : JSON.stringify(emailVO),
        contentType: "application/json; charset=UTF-8",
        success:function(cnt){       //컨트롤러에서 넘어온 cnt값
            if(cnt == false){      // 사용 가능한 아이디
                document.getElementById("email_already").style.display = "none";
                document.getElementById("email_ok").style.display = "inline-block";
            } else {          // cnt == 1 -> 이미 존재하는 아이디
                document.getElementById("email_already").style.display = "inline-block";
                document.getElementById("email_ok").style.display = "none";
            }
        },
        error:function(){
            alert("에러입니다");
        }
    });
};

//아이디 중복 체크
function id_check(){
    var loginId = $('#loginId').val(); // id값이 "id"인 입력란의 값을 저장
    var loginIdVO = new Object();
    loginIdVO.loginId= loginId;
    $.ajax({
        url:'/user/loginIdCheck', // Controller에서 인식할 주소
        type:'post', //POST 방식으로 전달
        data : JSON.stringify(loginIdVO),
        contentType: "application/json; charset=UTF-8",
        success:function(cnt){       //컨트롤러에서 넘어온 cnt값
            if(cnt == false){      // 사용 가능한 아이디
                document.getElementById("id_already").style.display = "none";
                document.getElementById("id_ok").style.display = "inline-block";
            } else {          // cnt == 1 -> 이미 존재하는 아이디
                document.getElementById("id_already").style.display = "inline-block";
                document.getElementById("id_ok").style.display = "none";
            }
        },
        error:function(){
            alert("에러입니다");
        }
    });
};

// 유효성 검사
function join_form_check(){
    var set_email = document.getElementById("email");
    var set_name = document.getElementById("name");
    var set_id = document.getElementById("loginId");
    var set_pw = document.getElementById("password");
    var check_pw = document.getElementById("check_password");
    var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if(!check(re2, set_email, "적합하지 않은 이메일 형식입니다.")) {
        return false;
    }
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

function check(re, what, message) {
    if(re.test(what.value)) {
        return true;
    }
    alert(message);
    what.value = "";
    what.focus();
    //return false;
}
const baseUrl = 'http://localhost:8080/';

function signIn(){
    let link = baseUrl + 'user/login';
    console.log(link);
    location.href = link;
}

