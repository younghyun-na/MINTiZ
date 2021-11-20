function addPostScriptTag() {

    var key =document.getElementById("baseTags").value;

    //document.getElementById("postscript-tag").style.display="inline-block";

    if(key==="postscript") {
        document.getElementById("postscript-tag").style.display="inline-block";
    }
    else {
        document.getElementById("postscript-tag").style.display="none";
    }

}