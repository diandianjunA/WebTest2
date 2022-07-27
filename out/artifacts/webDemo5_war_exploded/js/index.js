function delFruit(fruitName){
    if(confirm('是否确认删除')){
        window.location.href='fruit.do?fname='+fruitName+'&operator=delete';
    }
}
function page(pageNo){
    window.location.href='fruit.do?pageNo='+pageNo;
}
// function page(pageNo,pageCount){
//     if(pageNo>0&&pageNo<=pageCount){
//         window.location.href='index?pageNo='+pageNo;
//     }
// }