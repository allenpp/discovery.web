//alert("I Love Flutter");
 
 
 window.setInterval(function(){
 
     $(".biab_btn.biab_btn-info.js-place-bets-btn.biab_disabled").click();
	 $("#biab_placeBetsBtn").trigger("onClickPlaceBets");
	  $("#biab_placeBetsBtn").click(function(){console.log(1)});
	   $("#biab_modal").click();
	  var events =  $("#biab_placeBetsBtn").data("events")
	  var evt = document.createEvent("MouseEvents");
	  
	  
	 // doBet();
	  
	  
	
	 
	 // var e = document.createEvent("MouseEvents");
      //e.initEvent("mousedown", true, true);
      //document.getElementById("biab_placeBetsBtn").dispatchEvent(e);
	  
	   
 
 },10000);
 
 
 var time = 0;
 var  hasPlaceBet = 200;
 window.setInterval(function(){
 time++;
 if(time/60==1){
 
   //window.location.reload(true);
   window.location.href=window.location.href+"?id="+10000*Math.random();
 }
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
	
	var matchDate = $(".biab_market-date.biab_hidden-xs").text();
	var matchDateStr = $(".biab_market-date.biab_hidden-xs").html();
	var dufa = $(".biab_market-name.biab_hidden-xs").text();
	if(dufa=='独赢盘'){
		doInsert(arrPeiLv,arrSalePeiLv,home_away.split(" v "),arrAmount,arrSaleAmount,leagueMid,matchDateStr,dufa);
		// doPlaceBet('buy_s',25,2.5)
	}else if(dufa=='双重机会'){
		doInsertShuangChongJiHui(arrPeiLv,arrSalePeiLv,home_away.split(" v "),arrAmount,arrSaleAmount,leagueMid,matchDateStr,dufa);
	}
	
	
	 
	
	
	

 var away = $("biab_bet biab_blue-cell js-blue-cell biab_bet-back js-bet-back biab_back-0 js-back-0")
	//alert("home:"+home+str)

 var ping = $(".biab_bet.biab_blue-cell.js-blue-cell.biab_bet-back.js-bet-back.biab_back-0.js-back-0").find("span[class='js-odds'] ");
  
  

 },10000); 

 
 
 function doBet(){
 
		var buyarr = $(".biab_bet.biab_blue-cell.js-blue-cell.biab_bet-back.js-bet-back.biab_back-0.js-back-0")
		
        var salearr = $(".biab_bet.biab_green-cell.js-green-cell.biab_bet-lay.js-bet-lay.biab_lay-0.js-lay-0")
		 var e = document.createEvent("MouseEvents");
         e.initEvent("mousedown", true, true);
        buyarr[1].dispatchEvent(e);
		 
		//  var e2 = document.createEvent("MouseEvents");
         // e2.initEvent("mousedown", true, true);
        //salearr[2].dispatchEvent(e2);
		
		
 
 }
 
 
 

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
 
 
  
   
 function doInsert(buyPeiLv,salePeiLv,homeAway,buyAmout,saleAmout,leagueMid,matchDateStr,dufa){
 
  var urlId = $("#baseForm").attr("action");
  if(null!=urlId&&urlId.length>0){
	  var startIndex = urlId.lastIndexOf("/")+1;
	    urlId = urlId.slice(startIndex);
  }
   var temp = {
   buy_s1:buyPeiLv[0],buy_p1:buyPeiLv[2],buy_f1:buyPeiLv[1],buy_s1_amount:buyAmout[0],buy_p1_amount:buyAmout[2],buy_f1_amount:buyAmout[1],
   sale_s1:salePeiLv[0],sale_p1:salePeiLv[2],sale_f1:salePeiLv[1],sale_s1_amount:saleAmout[0],sale_p1_amount:saleAmout[2],sale_f1_amount:saleAmout[1],
   
    buy_s2:buyPeiLv[3],buy_p2:buyPeiLv[5],buy_f2:buyPeiLv[4],buy_s2_amount:buyAmout[3],buy_p2_amount:buyAmout[5],buy_f2_amount:buyAmout[4],
   sale_s2:salePeiLv[3],sale_p2:salePeiLv[5],sale_f2:salePeiLv[4],sale_s2_amount:saleAmout[3],sale_p2_amount:saleAmout[5],sale_f2_amount:saleAmout[4],
   
    buy_s3:buyPeiLv[6],buy_p3:buyPeiLv[8],buy_f3:buyPeiLv[7],buy_s3_amount:buyAmout[6],buy_p3_amount:buyAmout[8],buy_f3_amount:buyAmout[7],
   sale_s3:salePeiLv[6],sale_p3:salePeiLv[8],sale_f3:salePeiLv[7],sale_s3_amount:saleAmout[6],sale_p3_amount:saleAmout[8],sale_f3_amount:saleAmout[7],
   
   
   home:homeAway[0],away:homeAway[1],
   leagueName: leagueMid[0],matchId:leagueMid[1],
   matchDateStr:matchDateStr,
   dufa:dufa,
   urlId:urlId
   
   
   }
   var json = JSON.stringify(temp);
 
   $.ajax({
                    url: "http://127.0.0.1/wave/insert",
                    type: "POST",
                    datatype:"JSON",
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
 
  
  
  
  
  var betId = "";	 
  var hedgingId = "";  
 
   $.ajax({
                    url: "http://127.0.0.1/wave/isDoBet",
                    type: "POST",
                    datatype:"JSON",
					method: 'POST',
					contentType:"application/json;",
                    data: json ,
					async : false,
                    success: function (data) {
						var obj = eval('('+data+')')
						if(null!=obj&&null!=obj.optType){
							betId = obj.betId;
							hedgingId = obj.hedgingId;
							if(!canDoBet(leagueMid[1])) {
							   
							}
							if(obj.optType=='confirmStatus'){
								var status = doConfirmBetById(betId);
								if(null!=status){
									 
									updateBet(betId,status,leagueMid[1],hedgingId);
								}
							}else if(obj.optType=='nothing'){
								
							}else if(obj.optType=='buy_s'){
								 dobet(temp,temp.buy_s1,25,'buy_s',hedgingId);
							}else if(obj.optType=='buy_p'){
								dobet(temp,temp.buy_p1,25,'buy_p',hedgingId);
							}else if(obj.optType=='buy_f'){
								dobet(temp,temp.buy_f1,25,'buy_f',hedgingId);
							}else if(obj.optType=='sale_s'){
								dobet(temp,temp.sale_s1,25,'sale_s',hedgingId);
							}else if(obj.optType=='sale_p'){
								dobet(temp,temp.sale_p1,25,'sale_p',hedgingId);
							}else if(obj.optType=='sale_f'){
								dobet(temp,temp.sale_f1,25,'sale_f',hedgingId);
							} 
							
						}
                        console.log("isDobet "+obj);
                    },
                    error: function () {
                        console.log("提交失败！");
                    }
                });
  
  
   // var can =  canDoBet(temp.matchId)
    
 
 }
 
 
 function doConfirmBetById(betId){
	 
	 if(null==betId){
		 
		 console.log("doConfirmBetById but betId is null"); 
	     return "";
	 }
	 //  {"660932":"PLACED"}  表示 挂起
 
    //  {"660932":"CANCELLED"} 表示 成功
	 
	 if(false) return "1";
	 var status = "0";
	  var crstoken =  getCookie('CSRF-TOKEN');
	 var cookie = document.cookie;
	   $.ajax({
                    url: "http://135.84.237.201/Exchange/customer/api/betsStatuses?betIds="+betId,
                    type: "GET",
                    dataType:'json',
					// jsonp: "callbackparam",   
					contentType:"application/json;",
					headers:{'X-CSRF-TOKEN':crstoken,'Cookie':cookie},
                   // data: json ,
					crossDomain: true,
					async :false,
					xhrFields: {
						withCredentials: true
					},
                    success: function (data) {
						if(null!=data ){
							 status = data[betId];
						}
                        console.log(data);
                    },
                   function(XMLHttpRequest, textStatus, errorThrown) {
						console.log(XMLHttpRequest);
						console.log(errorThrown);
						console.log(textStatus);
                    },
					 complete: function(aa,bb,cc) {
						 console.log();
                          //请求完成的处理
                     },
					  error: function(aa,bb,cc) {
						   console.log();
                          //请求完成的处理
                     }
                });
				
		return status;
	 
	 
 }
 
  function updateBet(betId,status,matchId,hedgingId){
	 
	 if(null==betId||null==matchId||""==betId||""==matchId){
		 console.log("updateBet but betId is null");
		 return ;
	 }
	 
	 
	 //  {"660932":"PLACED"}  表示 挂起
 
    //  {"660932":"CANCELLED"} 表示 成功
	//  {"660932":"MATCHED"} 表示 成功
	if(null!=status){
		if(status=='PLACED'){
			status = '0';
		}else if(status=='CANCELLED'){
			status ='3';
		}else if(status=='MATCHED'){
			status = '1';
		}else if(status=='PENDING'){
			status = '0';
		}
		
		
	}
	 
	 var temp = {"betId":betId,"status":status,"matchId":matchId,"hedgingId":hedgingId};
	  var json = JSON.stringify(temp);
	       $.ajax({
                    url: "http://127.0.0.1/wave/updateBet",
                    type: "POST",
                    datatype:"JSON",
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
 
 
 
 
 function  doPlaceBet(optType,size,price,matchId,hedgingId){
 
     var returnArr = new Array();
	 var selectionId = '';
	 var betId = '';
	 var  betStatus = '';
	 if(optType.indexOf('buy')==0){
	     side='BACK'
	 }else if(optType.indexOf('sale')==0){
	     side = 'LAY';
	 }
	 if(optType.indexOf('_s')>0){
	     selectionId=$(".biab_market-selections.js-market-selections").children("div:eq(0)").attr('data-selection-id');
	 }else if(optType.indexOf('_p')>0){
	     selectionId = $(".biab_market-selections.js-market-selections").children("div:eq(2)").attr('data-selection-id');
	 }else if(optType.indexOf('_f')>0){
	     selectionId = $(".biab_market-selections.js-market-selections").children("div:eq(1)").attr('data-selection-id');
	 }
	 
	 var crstoken =  getCookie('CSRF-TOKEN');
	 var cookie = document.cookie;
 
 //1.145175124?id=6005.502125632794
    var urlId = $("#baseForm").attr("action");
	  if(null!=urlId&&urlId.length>0){
		  var startIndex = urlId.lastIndexOf("/")+1;
			urlId = urlId.slice(startIndex)+'';
			var endIndex = urlId.lastIndexOf("?");
			if(endIndex>0){
				urlId = urlId.substr(0,endIndex);
			}
	  }
	  var temp ={};
	  
      temp[urlId] =   [{"selectionId":selectionId,"side":side,"size":size,"price":price,"persistenceType":"LAPSE","handicap":"0","eachWayData":{}}]
	     	   
	 if(true){
		 
	   //var temp =   {"1.145000241":[{"selectionId":1408,"side":"BACK","size":25,"price":1.52,"persistenceType":"LAPSE","handicap":"0","eachWayData":{}}]}
 
	    var json = JSON.stringify(temp);
		
		if(!canDoBet(matchId) &&hedgingId==null) {
			var currentTime  = new Date().getTime();  
			returnArr.push(currentTime);
			returnArr.push(1);
			return null;
		}
 
        $.ajax({
                    url: "http://135.84.237.201/Exchange/customer/api/placeBets",
                    type: "POST",
                    dataType:'json',
					// jsonp: "callbackparam",   
					contentType:"application/json;",
					headers:{'X-CSRF-TOKEN':crstoken,'Cookie':cookie},
                    data: json ,
					crossDomain: true,
					async : false,
					xhrFields: {
						withCredentials: true
					},
                    success: function (data) {
						if(null!=data){
						   var value = data[urlId];
						   if(null!=value&&value.status=='OK'){
						        betId = value.offerIds[0];
								returnArr.push(betId);
						   }
						     
						    
						}
                        console.log(data);
                    },
                   function(XMLHttpRequest, textStatus, errorThrown) {
						console.log(XMLHttpRequest);
						console.log(errorThrown);
						console.log(textStatus);
                    },
					 complete: function(aa,bb,cc) {
						 console.log();
                          //请求完成的处理
                     },
					  error: function(aa,bb,cc) {
						   console.log();
                          //请求完成的处理
                     }
                });
				
			if(null==betId||""==betId){
				console.log("invoke  placeBets  fail ");
				return  null;
			}	
				
		   $.ajax({
                    url: "http://135.84.237.201/Exchange/customer/api/betsStatuses?betIds="+betId,
                    type: "GET",
                    dataType:'json',
					// jsonp: "callbackparam",   
					contentType:"application/json;",
					headers:{'X-CSRF-TOKEN':crstoken,'Cookie':cookie},
                   // data: json ,
					crossDomain: true,
					async :false,
					xhrFields: {
						withCredentials: true
					},
                    success: function (data) {
					
					    betStatus = data[betId];
						
					
                        console.log(data);
                    },
                   function(XMLHttpRequest, textStatus, errorThrown) {
						console.log(XMLHttpRequest);
						console.log(errorThrown);
						console.log(textStatus);
                    },
					 complete: function(aa,bb,cc) {
						 console.log();
                          //请求完成的处理
                     },
					  error: function(aa,bb,cc) {
						   console.log();
                          //请求完成的处理
                     }
                });
				
				returnArr.push(betStatus);
	 
	 }
 
     return  returnArr;
	 
 }
 
 
 function dobet(wave,optPeiLv,optAmount,optType,hedgingId){
	 
	     var  matchId = wave.matchId
	     var betId = '';
		 var status = '0';
	 
	    var arr = doPlaceBet(optType,optAmount,optPeiLv,matchId,hedgingId);
		if(null!=arr&&arr.length==2){
		   betId = arr[0];
		   status = arr[1];
		   if(status=='PLACED'){//待匹配
		       status =0;
		   }else if(status=='CANCELLED'){
		       status =3;
		   }else if(status=='MATCHED'){
		       status =1;
		   }else if(status=='PENDING'){
			  status = 0;
		  }
		}
	 
	    var currentTime  = new Date().getTime();  
		
		var optFlow = {"matchId":wave.matchId,"optPeiLv":optPeiLv,"optAmount":optAmount,"home":wave.home,"away":wave.away,"leagueName":wave.leagueName,
		               "optType":optType,"betId":betId,"status":status,"matchDateStr":wave.matchDateStr,"hedgingId":hedgingId };
					 
	    var json = JSON.stringify(optFlow);
 
     if(null==betId||""==betId) {
		 console.log("invoke insert dobet but betId is null");
		return ; 
	 }
         
   $.ajax({
                    url: "http://127.0.0.1/wave/doBet",
                    type: "POST",
                    datatype:"JSON",
					method: 'POST',
					contentType:"application/json;",
                    data: json ,
					crossDomain: true,
					async : false,
					xhrFields: {
						withCredentials: true
					},
                    success: function (data) {
						if(null!=data){
							return data;
						}
                        console.log("");
                    },
                    error: function () {
                        console.log("提交失败！");
                    }
                });
 
	 
	 
	 
 }
 
 
 function canDoBet(matchId){
	 var canDoBet = false;
	 	var temp = {"matchId":matchId  };
					 
	    var json = JSON.stringify(temp);
		
		   $.ajax({
                    url: "http://127.0.0.1/wave/canDoBet",
                    type: "POST",
                    datatype:"JSON",
					method: 'POST',
					contentType:"application/json;",
                    data: json ,
					async : false,
                    success: function (data) {
                       if(null!=data){
						   var obj = eval('('+data+')')
							if(obj.canDoBet==true)
								canDoBet = true;
							 
						}
                    },
                    error: function () {
                        console.log("提交失败！");
                    }
                });
	 
	 return canDoBet;
	 
 }
 
 
 
 
 function getCookie(key){
	var arr1=document.cookie.split("; ");//由于cookie是通过一个分号+空格的形式串联起来的，所以这里需要先按分号空格截断,变成[name=Jack,pwd=123456,age=22]数组类型；
	for(var i=0;i<arr1.length;i++){
		var arr2=arr1[i].split("=");//通过=截断，把name=Jack截断成[name,Jack]数组；
		if(arr2[0]==key){
			return decodeURI(arr2[1]);
		}
	}

 }
 
 
  function doInsertShuangChongJiHui(buyPeiLv,salePeiLv,homeAway,buyAmout,saleAmout,leagueMid,matchDateStr,dufa){
 
   var temp = {
   buy_s_p:buyPeiLv[0],buy_s_f:buyPeiLv[2],buy_p_f:buyPeiLv[1],buy_s_p_amount:buyAmout[0],buy_s_f_amount:buyAmout[2],buy_p_f_amount:buyAmout[1],
   sale_s_p:salePeiLv[0],sale_s_f:salePeiLv[2],sale_p_f:salePeiLv[1],sale_s_p_amount:saleAmout[0],sale_s_f_amount:saleAmout[2],sale_p_f_amount:saleAmout[1],
   
 
 
   
   
   home:homeAway[0],away:homeAway[1],
   leagueName: leagueMid[0],matchId:leagueMid[1],
   matchDateStr:matchDateStr,
   dufa:dufa
   
   
   }
   var json = JSON.stringify(temp);
 
   $.ajax({
                    url: "http://127.0.0.1/wave/update",
                    type: "POST",
                    datatype:"JSON",
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
 
 
 function fireKeyEvent(el, evtType, keyCode){  
 
         
         
           var evtObj = document.createEvent('UIEvents');  
            Object.defineProperty(evtObj, 'keyCode', {  
                get : function() { return this.keyCodeVal; }  
            });       
            Object.defineProperty(evtObj, 'which', {  
                get : function() { return this.keyCodeVal; }  
            });  
            evtObj.initUIEvent( evtType, true, true, win, 1 );  
            evtObj.keyCodeVal = keyCode;  
            if (evtObj.keyCode !== keyCode) {  
                console.log("keyCode " + evtObj.keyCode + " 和 (" + evtObj.which + ") 不匹配");  
            }  
          
        el.dispatchEvent(evtObj);  
   
}  
 
 
chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {

    //alert(request.action)
    if (request.action == "getText");
    {
       
    }
});


chrome.extension.sendMessage({'txt': '这里是发送的内容'} );
 
 
