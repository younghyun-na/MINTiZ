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

// 데이터 전송 객체
var comment = {
  // 이벤트 등록
  init: function() {
    var _this = this;
    // 생성 버튼 변수화
    const createBtn = document.querySelector('#submit_btn');
    // 생성 버튼 클릭 시, 수행할 메소드 연결!
    createBtn.addEventListener('click', function(){
      _this.create();
    });
    // 수정 버튼 변수화
    const updateBtns = document.querySelectorAll('#modify_btn');
    // 모든 수정 버튼별, 이벤트 등록
    updateBtns.forEach(function(item) {
      item.addEventListener('click', function() { // 클릭 이벤트 발생시,
        var form = this.closest('form'); // 클릭 이벤트가 발생한 버튼에 제일 가까운 폼을 찾고
        _this.update(form); // 해당 폼으로 업데이트 수행
      });
    });
  },
  // 댓글 수정
  update: function(form) {
      var data = {
        id: form.querySelector('#comment-id').value,
        author: form.querySelector('#comment-author').value,
        content: form.querySelector('#comment-content').value,
      };
      // url에서 article의 id를 추출!
      var split = location.pathname.split('/');
      var articleId = split[split.length - 1];
      // 비동기 통신
      fetch('/api/comments/' + data.id, { // 요청을 보냄
        method: 'PUT',
        body: JSON.stringify(data),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(function(response) { // 응답 처리
        if (response.ok) { // 성공
          alert('댓글이 수정되었습니다.');
        } else { // 실패
          alert('댓글 수정 실패..!');
        }
        window.location.reload(true); // 페이지 리로드
      });
    }
  };
