function bookmark(postId, userId){ //채운 별일때 클릭 -> 북마크 되어 있을 때
    var star = "star"+postId
    var clickedStar = "clickedStar" +postId
    var bookmarkVO = new Object()
    document.getElementById(clickedStar).style.display = "none";
    document.getElementById(star).style.display = "inline-block";

    bookmarkVO.userId = userId
    bookmarkVO.postId = postId
    jQuery.ajax({
        type : "POST",
        url : "/bookmark/delete",
        data : JSON.stringify(bookmarkVO),
        contentType: "application/json; charset=UTF-8",
        async:false,
        success: function(data){
        },
        error : function (data) {
            alert('죄송합니다. 잠시 후 다시 시도해주세요.');
            return false;
        }
    });

    console.log("취소");



}

function noneBookmark(postId, userId){ //빈 별일때 클릭 -> 북마크 되어있지 않을 때
    var star = "star"+postId
    var clickedStar = "clickedStar" + postId
    var bookmarkVO = new Object()
    document.getElementById(clickedStar).style.display = "inline-block";
    document.getElementById(star).style.display = "none";

    bookmarkVO.userId = userId
    bookmarkVO.postId = postId
    jQuery.ajax({
        type : "POST",
        url : "/bookmark/add",
        data : JSON.stringify(bookmarkVO),
        contentType: "application/json; charset=UTF-8",
        async:false,
        success: function(data){
        },
        error : function (data) {
            alert('죄송합니다. 잠시 후 다시 시도해주세요.');
            return false;
        }
    });


};

