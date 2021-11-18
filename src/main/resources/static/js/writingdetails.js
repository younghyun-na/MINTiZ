function warnEmpty(){
    alert("댓글을 입력해주세요");
}

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

