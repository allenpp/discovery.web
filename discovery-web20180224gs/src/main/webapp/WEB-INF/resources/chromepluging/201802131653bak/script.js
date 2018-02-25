//alert("I Love Flutter");
 
 var time = 0;
 window.setInterval(function(){
 
 if(time/60==1){
 

   parent.location.reload();
 
 }
 
 time++;
 
 
 var biab_placeBets = $("#biab_placeBets");
         //$(".biab_betslip-holder.js-betslip-holder").click()
		  var eventsData = $.data(biab_placeBets,'events') || $._data(biab_placeBets,'events');
		  var th = $("th.biab_no-bg.biab_text-right.biab_back-all.biab_back-all-column.js-back-all");
		  //$(th).click();
		  //alert($("th.biab_no-bg.biab_text-right.biab_back-all.biab_back-all-column.js-back-all"))
		 //alert( document.getElementsByClassName("biab_tab-content-holder"))
		 //  $(".biab_no-bg.biab_text-right.biab_back-all.biab_back-all-column.js-back-all").trigger('click');
		  //$(".biab_no-bg.biab_text-right.biab_back-all.biab_back-all-column.js-back-all").click();
 
  var str = ""
  var arrPeiLv = new Array();
  var arrAmount = new Array();
 var home = $(".biab_bet.biab_blue-cell.js-blue-cell.biab_bet-back.js-bet-back.biab_back-0.js-back-0").each(function(){
         
		     
		
		 var domObj =$(this);
		 arrPeiLv.push($(this).find("span[class='js-odds']").html() );
		 arrAmount.push($(this).find("span[class='biab_bet-amount']").html().substring(1));
		 str= str+$(this).find("span[class='js-odds']").html()+"  "         
         
    });
	
	
	
	
	  $(".biab_bet.biab_grey-cell.biab_bet-back.js-bet-back.biab_back-1.js-back-1.biab_hide-bet").each(function(){
         
		     
		
		 var domObj =$(this);
		 arrPeiLv.push($(this).find("span[class='js-odds']").html() );
		 arrAmount.push($(this).find("span[class='biab_bet-amount']").html().substring(1));
		 str= str+$(this).find("span[class='js-odds']").html()+"  "         
         
    });
	
	  $(".biab_bet.biab_grey-cell.biab_bet-back.js-bet-back.biab_back-2.js-back-2.biab_hide-bet").each(function(){
         
		     
		
		 var domObj =$(this);
		 arrPeiLv.push($(this).find("span[class='js-odds']").html() );
		 arrAmount.push($(this).find("span[class='biab_bet-amount']").html().substring(1));
		 str= str+$(this).find("span[class='js-odds']").html()+"  "         
         
    });
	
	 
	
	var arrSalePeiLv = new Array();
    var arrSaleAmount = new Array();
	$(".biab_bet.biab_green-cell.js-green-cell.biab_bet-lay.js-bet-lay.biab_lay-0.js-lay-0").each(function(){
         
		     
		
		 var domObj =$(this);
		 arrSalePeiLv.push($(this).find("span[class='js-odds']").html() );
		 arrSaleAmount.push($(this).find("span[class='biab_bet-amount']").html().substring(1));
		 str= str+$(this).find("span[class='js-odds']").html()+"  "         
         
    });
	
	$(".biab_bet.biab_grey-cell.biab_bet-lay.js-bet-lay.biab_lay-1.js-lay-1.biab_hide-bet").each(function(){
         
		     
		
		 var domObj =$(this);
		 arrSalePeiLv.push($(this).find("span[class='js-odds']").html() );
		 arrSaleAmount.push($(this).find("span[class='biab_bet-amount']").html().substring(1));
		 str= str+$(this).find("span[class='js-odds']").html()+"  "         
         
    });
	
	$(".biab_bet.biab_grey-cell.biab_bet-lay.js-bet-lay.biab_lay-2.js-lay-2.biab_hide-bet").each(function(){
         
		     
		
		 var domObj =$(this);
		 arrSalePeiLv.push($(this).find("span[class='js-odds']").html() );
		 arrSaleAmount.push($(this).find("span[class='biab_bet-amount']").html().substring(1));
		 str= str+$(this).find("span[class='js-odds']").html()+"  "         
         
    });
	
	
	
	
	
	var home_away  = $("div.biab_market-title-name").html()
	
	
	$(".biab_bet.biab_green-cell.js-green-cell.biab_bet-lay.js-bet-lay.biab_lay-1.js-lay-1")[0]
	
	
	
	
	 	
	var isTrue = islittle1(arrPeiLv[0],arrPeiLv[1],arrPeiLv[2]);
	
	if(isTrue){
	    alert(  1/arrPeiLv[0] +1/arrPeiLv[1] +1/arrPeiLv[2])
		var payArr = caculateAmount(arrPeiLv[0],arrPeiLv[1],arrPeiLv[2]);
		
		isGuaPaiEnough(arrAmount[0],arrAmount[1],arrAmount[2],payArr[0],payArr[1],payArr[2])
		
		setBuyAmount(payArr,arrPeiLv);
	}
	
	
	var leagueMid = findLeagueNameMatchId();
	
	doInsert(arrPeiLv,arrSalePeiLv,home_away.split(" v "),arrAmount,arrSaleAmount,leagueMid);
	
	
	
	
	

 var away = $("biab_bet biab_blue-cell js-blue-cell biab_bet-back js-bet-back biab_back-0 js-back-0")
	//alert("home:"+home+str)

 var ping = $(".biab_bet.biab_blue-cell.js-blue-cell.biab_bet-back.js-bet-back.biab_back-0.js-back-0").find("span[class='js-odds'] ");
  
  

 },10000); 


 //只有小于0.9 的时候才启动
 function  islittle1(  home,  away,  ping){
 	if(home&&away&&ping){
	    var total = 1/home +1/away +1/ping ;
		if(total<=0.98){
		   return true;
		}else{
			return false;
		}
	    
	}else{
	
	  return false;
	}
 
 }
 
 //挂牌的是否足够大于计算出来的
 function  isGuaPaiEnough(  homeG,  awayG,  pingG,  home,  away,  ping){
 
    if(home<(homeG+1000)&&away<(awayG+1000)&&ping<(pingG+1000)){
		return true;
	}else{
	
		return false;
	}
 
 
 }
 
 
 
 //计算最小的是25的时候  各个是多少
 function caculateAmount(  home,  away,  ping){
 
 //求倒数
   var homeDs = 1/home;
   var awayDs = 1/away;
   var pingDs = 1/ping;
   //保留两位小数
   homeDs = Math.round(homeDs*10000)/100
   awayDs =Math.round(awayDs*10000)/100
   pingDs = Math.round(pingDs*10000)/100
   
   var rate =1;
   if(homeDs<25){
      rate = 25/homeDs;
	  homeDs = 25;
	  awayDs = awayDs*rate;
	  pingDs = pingDs*rate;
   }
    if(awayDs<25){
      rate = 25/awayDs;
	  awayDs = 25;
	   homeDs = homeDs*rate;
	  pingDs = pingDs*rate;
   }
    if(pingDs<25){
      rate = 25/pingDs;
	  pingDs = 25;
	  homeDs = homeDs*rate;
	  awayDs = awayDs*rate;
   }
   
   return new Array( homeDs,awayDs, pingDs ); 
    
 }
 
 
 function setBuyAmount(byAmount,arrPeiLv){
 
  var i = 0;
  //赋值前需要先 显示出来这个框
   var th = $("th.biab_no-bg.biab_text-right.biab_back-all.biab_back-all-column.js-back-all");
		   $(th).click();
 
  
  
  if($("input[class='biab_size js-size']").length==byAmount.length){
  	  $("input[class='biab_size js-size']").each(function(){
			
			var value = byAmount[i];
			  $(this).val(value);
			  var obj = $(this)
			  obj.focus()
			  obj.blur()
			   obj.keyup();
			   obj.keypress();
			    obj.keydown();
			  $(this).trigger("input")
			  $(this).trigger("oninput")
			 $(this).trigger("propertychange")
			 
			  i++;
			 
			 
		});
  }
 
 
 var  j = 1;
 var away = arrPeiLv[1]*byAmount[1]-byAmount[1];
 if($("input[class='biab_custom-number biab_liability js-liability']").length==byAmount.length){
  	  $("input[class='biab_custom-number biab_liability js-liability']").each(function(){
			
			 
			if(2==j){
			  $(this).val(away);
			}
			  var obj = $(this)
			  obj.focus()
			  obj.blur()
			   obj.keyup();
			   obj.keypress();
			    obj.keydown();
			  $(this).trigger("input")
			  $(this).trigger("oninput")
			 $(this).trigger("propertychange")
			 
			  j++;
			 
			 
		});
  }
 }
 
 

 function findLeagueNameMatchId(){
 
 
    var leagueNameMid =new Array();
	var i = 0;
    $("li.biab_event-item.biab_has-arrow.js-event-item").each(function(){
	
	   if(i==0){
	    var temp = $(this);
	     leagueNameMid.push(temp[0].innerText);
	   }else if(i==1){
	     leagueNameMid.push($(this).find("a[class='biab_event-item biab_has-icon']").attr("data-navigation-id"));
	   }
	   
	  i++;
	
	});
 
    return leagueNameMid;
 
 }
 
 
  
   
 function doInsert(buyPeiLv,salePeiLv,homeAway,buyAmout,saleAmout,leagueMid){
 
   var temp = {
   buy_s1:buyPeiLv[0],buy_p1:buyPeiLv[2],buy_f1:buyPeiLv[1],buy_s1_amount:buyAmout[0],buy_p1_amount:buyAmout[2],buy_f1_amount:buyAmout[1],
   sale_s1:salePeiLv[0],sale_p1:salePeiLv[2],sale_f1:salePeiLv[1],sale_s1_amount:saleAmout[0],sale_p1_amount:saleAmout[2],sale_f1_amount:saleAmout[1],
   
    buy_s2:buyPeiLv[3],buy_p2:buyPeiLv[5],buy_f2:buyPeiLv[4],buy_s2_amount:buyAmout[3],buy_p2_amount:buyAmout[5],buy_f2_amount:buyAmout[4],
   sale_s2:salePeiLv[3],sale_p2:salePeiLv[5],sale_f2:salePeiLv[4],sale_s2_amount:saleAmout[3],sale_p2_amount:saleAmout[5],sale_f2_amount:saleAmout[4],
   
    buy_s3:buyPeiLv[6],buy_p3:buyPeiLv[8],buy_f3:buyPeiLv[7],buy_s3_amount:buyAmout[6],buy_p3_amount:buyAmout[8],buy_f3_amount:buyAmout[7],
   sale_s3:salePeiLv[6],sale_p3:salePeiLv[8],sale_f3:salePeiLv[7],sale_s3_amount:saleAmout[6],sale_p3_amount:saleAmout[8],sale_f3_amount:saleAmout[7],
   
   
   home:homeAway[0],away:homeAway[1],
   leagueName: leagueMid[0],matchId:leagueMid[1]
   
   
   }
   var json = JSON.stringify(temp);
 
   $.ajax({
                    url: "http://127.0.0.1/wave/insert",
                    type: "POST",
                    datatype:"JSONP",
					jsonp: "callback",    //跨域请求的参数名，默认是callback
					method: 'POST',
					contentType:"application/json;",
                    data: json ,
                    success: function (data) {
                        console.log("");
                    },
                    error: function () {
                        console.log("提交失败！");
                    }
                });
 
  
 
 
 }
 
 function callback(){
   console.log(" callback success！");
 
 }
 
 
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {

    alert(request.action)
    if (request.action == "getText");
    {
       
    }
});