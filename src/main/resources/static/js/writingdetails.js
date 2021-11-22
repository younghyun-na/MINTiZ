function warnEmpty(){
    alert("댓글을 입력해주세요");
}

/*
$("#user_id").text()     // 로그인한 사용자의 id 가져와야함 (jquery) 타임리프 변수명보고 수정하기
$(".writer_name")        // 글 작성자
$(".writer_name")[0].innerHTML

for(i = 0; i < $(".writer_name").length; i++){
    if($("#user_name").text() == $(".writer_name")[i].innerHTML){
        $("#control_id"+i).removeClass("hidden");
    }
}
*/

function dateToString(date){
    const dateString = date.toISOString();
    const dateToString = dateString.substring(11,19);
    return dateToString;
}

function submitComment(){
    const newcommentEL = document.getElementById("comment-input");
    const newcomment = newcommentEL.value.trim();

    if(newcomment){
        const dateEL = document.createElement('div');
        dateEL.classList.add("comment-date");
        const dateString = dateToString(new Date());
        dateEL.innerText = dateString;

        const contentEL = document.createElement('div');
        contentEL.classList.add("comment-content");
        contentEL.innerText = newcomment;

        const commentEL = document.createElement('div');
        commentEL.classList.add("comment-row");
        commentEL.appendChild(dateEl);
        commentEL.appendChild(contentEL);

        document.getElementById("comments").appendChild(commentEL);
        newcommentEL.value="";

        const countEL = document.getElementById("comments-count");
        const count = countEL.innerText;
        countEL.innerText = parseInt(count) + 1;
    }
    else warnEmpty();
}

