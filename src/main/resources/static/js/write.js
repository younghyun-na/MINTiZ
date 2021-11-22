function addPostScriptTag() {

    var key =document.getElementById("tagName").value;

    //document.getElementById("postscript-tag").style.display="inline-block";

    if(key==="후기") {
        document.getElementById("postscript-tag").style.display="inline-block";
    }
    else {
        document.getElementById("postscript-tag").style.display="none";
    }

}