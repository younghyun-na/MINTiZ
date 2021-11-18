const main = document.querySelector("#testmain");
const qna = document.querySelector("#qna");
const result = document.querySelector("#result");
const signupBtn = document.getElementById("signup");
const retryBtn = document.getElementById("retry");
const baseUrl = 'http://localhost:8080/';
const endPoint = 7;
const select = [];

function calResult() {
    let correctNum = 0;
    var pointArray = [
        { name: '반민초단', value: 0, key: 0},
        { name: '민초입문자', value: 0, key: 1},
        { name: '민초린이', value: 0, key: 2},
        { name: '민초른이', value: 0, key: 3},
        { name: '민초 러버', value: 0, key: 4},
        { name: '민초 마스터', value: 0, key: 5},
    ]

    for(let i = 0; i< endPoint; i++) {
        var target = qnaList[i].a[select[i]];   //고른거
        if(select[i] === qnaList[i].correct){
            correctNum += 1;
        }
    }
    console.log(correctNum);

    var resultArray = [];
    switch (correctNum) {
        case 0:  resultArray = pointArray[0];
            break;
        case 1:  resultArray = pointArray[1];
            break;
        case 2:  resultArray = pointArray[2];
            break;
        case 3:  resultArray = pointArray[2];
            break;
        case 4:  resultArray = pointArray[3];
            break;
        case 5:  resultArray = pointArray[3];
            break;
        case 6:  resultArray = pointArray[4];
            break;
        case 7:  resultArray = pointArray[5];
            break;
        default:
            break;
    }
    console.log(resultArray);
    let resultword = resultArray.key;
    return resultword;
}

let point;
function setResult() {
    point = calResult();
    const resultName = document.querySelector(".resultname");
    resultName.innerHTML = infoList[point].name;
    resultName.innerHTML.fontcolor("#817171");

    var resultImg = document.createElement("img");
    const imgDiv = document.querySelector("#resultImg");
    var imgURL = "/img/test/testImg"+point+".png";

    resultImg.src = imgURL;
    resultImg.alt = point;
    resultImg.classList.add("img-fluid");
    imgDiv.appendChild(resultImg);

    const resultDesc = document.querySelector(".resultDesc");
    resultDesc.innerHTML = infoList[point].desc;
    resultDesc.innerHTML.fontcolor("#817171");
    if (point == 0 ){
        signupBtn.style.display = "none";
    } else{
        retryBtn.style.display = "none";
    }


}

function goToSignUp() {
    let link = baseUrl + 'user/signup?level=' + point;
    console.log(link);
    location.href = link;
}

function goToRetry() {
    let link = baseUrl + 'test' ;
    console.log(link);
    location.href = link;
}

function goResult() {
    qna.style.WebkitAnimation = "fadeOut 0.5s";
    qna.style.animation = "fadeOut 0.5s";
    setTimeout(() => {
        result.style.WebkitAnimation = "fadeIn 0.5s";
        result.style.animation = "fadeIn 0.5s";
        setTimeout(() => {
            qna.style.display = "none";
            result.style.display = "block";
        }, 200)})
    console.log(select);

    setResult();
    calResult();
}

function addAnswer(answerText, qIdx, idx) {
    var a = document.querySelector(".aBox");
    var answer = document.createElement("Button");
    answer.classList.add("answerList");
    answer.classList.add("fadeIn");

    a.appendChild(answer);
    answer.innerHTML = answerText

    answer.addEventListener("click", function(){
        var children = document.querySelectorAll(".answerList")
        for(let i = 0; i<children.length; i++){
            children[i].disabled = true;
            children[i].style.WebkitAnimation = "fadeOut 0.5s";
            children[i].style.animation = "fadeOut 0.5s";
        }
        setTimeout(() => {
            select[qIdx] = idx;
            for(let i = 0; i < children.length; i++) {
                children[i].style.display = "none";
            }
            goNext(++qIdx);
        }, 450)
    }, false);
}

function goNext(qIdx) {
    if(qIdx === endPoint) {
        goResult();
        return;
    }

    var q = document.querySelector(".qBox");
    q.innerHTML = qnaList[qIdx].q;
    for(let i in qnaList[qIdx].a) {
        addAnswer(qnaList[qIdx].a[i].answer, qIdx, i);
    }
    var status = document.querySelector(".statusBar");
    status.style.width = (100/endPoint)*(qIdx+1)+"%";
}

function begin() {
    main.style.WebkitAnimation = "fadeOut 0.5s";
    main.style.animation = "fadeOut 0.5s";
    setTimeout(() => {
        qna.style.WebkitAnimation = "fadeIn 0.5s";
        qna.style.animation = "fadeIn 0.5s";
        setTimeout(() => {
            main.style.display = "none";
            qna.style.display = "block";
        }, 200)
        let qIdx = 0;
        goNext(qIdx);
    },200);

    //main.style.display = "none";
    //qna.style.display = "block";
}