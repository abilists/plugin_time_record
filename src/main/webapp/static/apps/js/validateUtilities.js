
/* Count text */
function cntTextByByte(s,b,i,c){
    for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 2 : 1);
    return b;
}

/* how to use */
console.log(cntTextByByte('안녕하세요 ㅋㅋㅋㅋ, test test'));
