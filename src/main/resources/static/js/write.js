function addPostScriptTag() {
    var key = baseTags.value;

    if(key==="postscript") {
        document.getElementById("postscript-tag").style.display="inline-block";
    }
    else {
        document.getElementById("postscript-tag").style.display="none";
    }
}