<%@ page  session="false"  contentType="text/html;charset=UTF-8" language="java" %>
<%@ page  session="false"  import="java.net.URLDecoder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    String basePath = request.getContextPath();
%>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="peiRate" style="height:400px"></div>
    <div id="amount" style="height:400px"></div>
    <div id="byTime" style="height:400px"></div>
    <!-- ECharts单文件引入 -->
    <script src="http://localhost/resources/js/jquery-3.2.1.min.js"></script>
    <script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
    <script src="${basePath}/resources/js/main.js"></script>
    <script src="http://localhost/resources/js/echarts.min.js"></script>
    <script type="text/javascript">
            function addBei(id){
                var bei = parseInt($("#bei"+id).text()) +1;
                $("#bei"+id).html(bei);

                var fangcheng = $("#fangcheng"+id).text();
                var jieguo = eval(fangcheng)*bei;
                $("#jieguo"+id).html(jieguo);

                var total =parseInt($("#total").val())+2;
                $("#total").val(total);
            }
            function jian(id){
                if(parseInt($("#bei"+id).text())==0){
                    return;
                }
                var bei = parseInt($("#bei"+id).text()) -1;
                $("#bei"+id).html(bei);

                var fangcheng = $("#fangcheng"+id).text();
                var jieguo = eval(fangcheng)*bei;
                $("#jieguo"+id).html(jieguo);

                var total =parseInt($("#total").val())-2;
                $("#total").val(total);
            }

       function create ( id){

            if(null!=$("#win")&&null!=$("#eq")&&null!=$("#fail")){

                $("tr[id=tt]").remove();


                var win1 = $("#win1").val();
                var win2 = $("#win2").val();
                var eq1 = $("#eq1").val();
                var eq2 = $("#eq2").val();
                var fail1 = $("#fail1").val();
                var fail2 = $("#fail2").val();

                var array1 = new Array();
                var array2 = new Array();
                array1[0]=win1;
                array1[1]=eq1;
                array1[2]=fail1;

                array2[0]=win2;
                array2[1]=eq2;
                array2[2]=fail2;

                var total = 0;
                for(var i=0;i<array1.length;i++){
                    for(var j=0;j<array2.length;j++){
                        total+=2;
                        var theId = "_"+i+"_"+j;
                      $("#result").prepend("<tr  id=\"tt\" ><td id=\"fangcheng"+theId+"\">("+array1[i]+"*"+array2[j]+")</td><td>*</td><td id=\"bei"+theId+"\">1</td><td>=</td><td id=\"jieguo"+theId+"\">"+array1[i]*array2[j]+"</td>  <td> <input id=\"add\" name=\"加\" type=\"button\"   onclick=\"addBei('"+theId+"')\" />   <input id=\"jian\" name=\"减\" type=\"button\"   onclick=\"jian('"+theId+"')\" /></td> </tr><br>");

                    }
                }
              $("#total").val(total);
            }
        }

    </script>

</head>


<body>

 <%--<tr id="race1" >win <input   id="win1"  value="1" type="text"  />   eq :<input    id="eq1" value="3" type="text"   />  fail: <input    id="fail1" value="4" type="text"   />     </tr><br>--%>
 <%--<tr id="race2" >win <input   id="win2" value="2" type="text"  />   eq :<input    id="eq2"  value="3" type="text"   />  fail: <input    id="fail2" value="5"  type="text"   />   create<input id="create"  type="button"   onclick="create()" /> </tr>--%>
 <%--<tr><td>total:<input id="total" type="text" ></td></tr>--%>

 <table id="result" >
     <form action="http://127.0.0.1/main/showZheXian"  id="myForm" method="get">
     <try>
         <td>
     <select id="matchIds" name="matchId" cssClass="input4">
         <c:forEach var="value" items="${matchIds}">
             <option value="${value.matchId}">
                     ${value.home}
             </option>
         </c:forEach>
     </select>
         </td>
         <td>

             <input type="hidden" id="matchId" value="" />
             <input type="submit" id="submit" onclick="checkForm();">
         </td>
     </try>




     <input id="chartVo" type="hidden" value="  ${chartVo}" />
     <input id="buy_s1_list" type="hidden" value="  ${buy_s1_list}" />
     <input id="buy_p1_list" type="hidden" value="  ${buy_p1_list}" />
     <input id="buy_f1_list" type="hidden" value="  ${buy_f1_list}" />
     <input id="amountalllist" type="hidden" value="  ${amountAllList}" />

     <input id="sale_s1_list" type="hidden" value="  ${sale_s1_list}" />
     <input id="sale_p1_list" type="hidden" value="  ${sale_p1_list}" />
     <input id="sale_f1_list" type="hidden" value="  ${sale_f1_list}" />
     <input id="sale_amountAllList" type="hidden" value="  ${sale_amountAllList}" />
     <input id="buy_sale_rate_list" type="hidden" value="  ${buy_sale_rate_list}" />


     <input id="xAxis" type="hidden" value="  ${xAxis}" />
     </form>
 </table>
 <script type="text/javascript">
     function checkForm() {
         var matchIds = document.getElementById('matchIds').value;
         document.getElementById('matchId').value =matchIds;
         var  url = "http://127.0.0.1/main/showZheXian?matchId="+matchIds
         window.location.href = url;
         return true;
     }
 </script>
</body>