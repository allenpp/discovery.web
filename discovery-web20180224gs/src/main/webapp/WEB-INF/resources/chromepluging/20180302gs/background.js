//alert("I Love Flutter");
 
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {

    alert(request.action)
    if (request.action == "getText");
    {
       
    }
});
  
chrome.extension.onMessage.addListener(function(objRequest, _, sendResponse){
    var strText = objRequest.txt;
    // 将信息能过Ajax发送到服务器
    $.ajax({
        url: 'http://www.jgb.cn/',
        type: 'POST',
        data: {'txt': strText},
        dataType: 'json',
    }).then(function(){
        // 将正确信息返回content_script
        sendResponse({'status': 200});
    }, function(){
        // 将错误信息返回content_script
        sendResponse({'status': 500});
    });
});
 
chrome.webRequest.onBeforeRequest.addListener (  
  
    function(details) {  
      
        chrome.tabs.query({active:true},function(tab){  
            // 当前页面的url  
            var pageUrl = tab[0].url;  
            // 在这可以写判断逻辑，将请求cancel掉，或者将请求打印出来  
            console.log("current url -> " + pageUrl);  
        });  
  
    },  
       
    {urls:["*://*/*"]},  //监听页面请求,你也可以通过*来匹配。  
    ["blocking"]   
); 


 

chrome.webRequest.onCompleted.addListener(
function(details){
	// 请求完毕，返回的相关数据，都在details中
	// 拿到数据后，可以通过chrome.extension.sendMessage({msg:"getNetworkResource", data:details});将数据通知popup.html
},
{urls: 
["<all_urls>"]}
  //["responseHeaders"]
);