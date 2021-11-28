
$("#user_id").text()     // 로그인한 사용자의 id 가져와야함 (jquery) 타임리프 변수명보고 수정하기
$(".writer_name")        // 글 작성자
$(".writer_name")[0].innerHTML

for(i = 0; i < $(".writer_name").length; i++){
    if($("#user_name").text() == $(".writer_name")[i].innerHTML){
        $("#control_id"+i).removeClass("hidden");
    }
}


/*
$('#submit_btn').click(function(){
    createComment($('#post_num'));
});

$('#submit_btn').click(function () {
    $('#form').submit(function(e){
        e.preventDefault();
        createComment(("#post_num").val());
    });
})

/*
$('#form').on("submit", function(){
    createComment(("#post_num").val());
    return false;
});

function createComment(postId) {
    // 데이터
    var data = {
        content : $('#comment-input').val()
        //content: document.querySelector('#comment-input').value,
    };

    // Ajax 통신
    $.ajax({
        url: "/post/" + postId + "/comments",
        type : "POST",
        data : JSON.stringify(data),
        contentType: "application/json; charset=UTF-8",
        async:false,
    }).done(function (fragment){
        $('#commentTable').replaceWith(fragment);
        window.location.reload(true);
    });
}

function updateComment(postId){
    var form = this.closest('form'); // 클릭 이벤트가 발생한 버튼에 제일 가까운 폼을 찾고,
    update(postId, form);
}

function update(postId, form){
    var data = {
        commentId: form.querySelector('#comment-id').value,
        content: form.querySelector('.comment-content').value,
    };

    jQuery.ajax({
        url: "/post/" + postId + "/comments/" + data.commentId + "/update",
        type : "POST",
        data : JSON.stringify(data),
        contentType: "application/json; charset=UTF-8",
        async:false
    }).done(function (fragment){
        $('#commentTable').replaceWith(fragment);
        window.location.reload(true);
    });
}


/*

// 데이터 전송 객체 생성!
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
        const updateBtns = document.querySelectorAll('.comment-update-btn');

        // 모든 수정 버튼별, 이벤트 등록
        updateBtns.forEach(function(item) {
            item.addEventListener('click', function() { // 클릭 이벤트 발생시,
                var form = this.closest('form'); // 클릭 이벤트가 발생한 버튼에 제일 가까운 폼을 찾고,
                _this.update(form); // 해당 폼으로, 업데이트 수행
            });
        });
    },

    // 댓글 등록
    create: function(int postId) {
        // 데이터
        var data = {
            content: document.querySelector('#comment-input').value,
        };

        // Ajax 통신
        jQuery.ajax({
            url: "/post/" + postId + "/comments",
            type : "POST",
            data : JSON.stringify(data),
            contentType: "application/json; charset=UTF-8",
            async:false
        }).done(function (fragment){
            $('#commentTable').replaceWith(fragment);
            window.location.reload(true);
        });


        /*
        fetch('/api/comments/' + articleId, { // 요청을 보냄
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function(response) { // 응답 처리
            if (response.ok) { // 성공
                alert('댓글이 등록되었습니다.');
                window.location.reload(`/articles/${articleId}#comment`);
            } else { // 실패
                alert('댓글 등록 실패..!');
            }
        });


    },

    // 댓글 수정
    update: function(form) {
        // 데이터
        var postId = form.querySelector('#post-id').value;
        var data = {
            commentId: form.querySelector('#comment-id').value,
            content: form.querySelector('.comment-content').value,
        };
        /**
        // url에서 postId 추출?
        var split = location.pathname.split('/');
        var postId = split[split.length - 1];
        // 비동기 통신**/


        /*
        fetch('/post/' + data.postId + '/comments/' + data.commentId + '/update', { // 요청을 보냄
            method: 'POST',
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
// 객체 초기화!
comment.init();
*/

