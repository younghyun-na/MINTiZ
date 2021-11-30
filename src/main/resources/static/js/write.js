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

const input = document.querySelector('input');
const preview = document.querySelector('.preview');

input.addEventListener('change', showImageFile);

function showImageFile() {
    const selectedFiles = input.files;
    const list = document.createElement('ul');
    preview.appendChild(list);

    for(const file of selectedFiles) {
        const listItem = document.createElement('li');
        if(validFileType(file)) {
            const summary = document.createElement('div');
            summary.textContent = `파일명 : ${file.name}`;
            listItem.appendChild(summary);
        } else {
            const message = document.createElement('div');
            message.textContent = `파일명 ${file.name}: .jpg/png 파일을 선택하세요`;
            listItem.appendChild(message);
        }
        list.appendChild(listItem);
    }
}
const fileTypes = [
    'image/png',
    'image/jpeg'
];

function validFileType(file) {
    return fileTypes.includes(file.type);
}