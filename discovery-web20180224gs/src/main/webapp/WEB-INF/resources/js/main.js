$(function () {


   var chartVo =$("#chartVo").val();
   var buy_s1_data = $("#buy_s1_list").val();
   buy_s1_data = eval ("(" + buy_s1_data+ ")");
   var buy_p1_data = eval ("(" +  $("#buy_p1_list").val()+ ")");
   var buy_f1_data = eval ("(" +  $("#buy_f1_list").val()+ ")");
   var amounTotal_data = eval ("(" +  $("#amountalllist").val()+ ")");


   var sale_s1_data = $("#sale_s1_list").val();
   sale_s1_data = eval ("(" + sale_s1_data+ ")");
   var sale_p1_data = eval ("(" +  $("#sale_p1_list").val()+ ")");
   var sale_f1_data = eval ("(" +  $("#sale_f1_list").val()+ ")");
   var sale_amountAllList = eval ("(" +  $("#sale_amountAllList").val()+ ")");
   var buy_sale_rate_list = eval ("(" +  $("#buy_sale_rate_list").val()+ ")");
   var xAxis =  $("#xAxis").val();
   xAxis = xAxis.split(",");
// 路径配置
   require.config({
      paths: {
         echarts: "http://echarts.baidu.com/build/dist"
      }
   });

   // buy/sale=rate
   require(
       [
          'echarts',
          'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
       ],
       function aa (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('byTime'));
          option = {
             title : {
                text: '未来一周气温变化',
                subtext: 'amount/Rate'
             },
             tooltip : {
                trigger: 'axis'
             },
             legend: {
                data:['最高气温','最低气温']
             },
             toolbox: {
                show : true,
                feature : {
                   mark : {show: true},
                   dataView : {show: true, readOnly: false},
                   magicType : {show: true, type: ['line', 'bar']},
                   restore : {show: true},
                   saveAsImage : {show: true}
                }
             },
             calculable : true,
             xAxis : [
                {
                   type : 'category',
                   boundaryGap : false,
                   data : ['周一','周二','周三','周四','周五','周六','周日']
                }
             ],
             yAxis : [
                {
                   type : 'value',
                   scale:true,
                   axisLabel : {
                      formatter: '{value}'
                   }
                }, {
                   type : 'value',
                   axisLabel : {
                      formatter: '{value}'
                   },
                   'name':'totalAmount'
                }
             ],
             series : [
                {
                   name:'最高气温',
                   type:'line',
                   data:buy_sale_rate_list,
                   //data:[1.3, 1.5, 1.6,1.7, 1.8, 1.9,1.9],
                   markPoint : {
                      data : [
                         {type : 'max', name: '最大值'},
                         {type : 'min', name: '最小值'}
                      ]
                   },
                   markLine : {
                      data : [
                         {type : 'average', name: '平均值'}
                      ]
                   }
                },
                {
                   name:'最低气温',
                   type:'line',
                   data:[],
                   //data:amounTotal_data,
                   //data:[1, -4, 2, 5, 3, 2, 0],
                   markPoint : {
                      data : [
                         {name : '周最低', value : -4, xAxis: 1, yAxis: -1.5}
                      ]
                   },
                   markLine : {
                      data : [
                         {type : 'average', name : '平均值'}
                      ]
                   }
                }
             ]
          };

          //option.xAxis[0].data = [1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7];
          option.xAxis[0].data = xAxis;
          // 为echarts对象加载数据
          myChart.setOption(option);
       }
   );

   // allbuy_sale
   require(
       [
          'echarts',
          'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
       ],
       function aa (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('peiRate'));
          option = {
             title : {
                text: '未来一周气温变化',
                subtext: 'buy/sale'
             },
             tooltip : {
                trigger: 'axis'
             },
             legend: {
                data:['buy','sale']
             },
             toolbox: {
                show : true,
                feature : {
                   mark : {show: true},
                   dataView : {show: true, readOnly: false},
                   magicType : {show: true, type: ['line', 'bar']},
                   restore : {show: true},
                   saveAsImage : {show: true}
                }
             },
             calculable : true,
             xAxis : [
                {
                   type : 'category',
                   boundaryGap : false,
                   data : ['周一','周二','周三','周四','周五','周六','周日']
                }
             ],
             yAxis : [
                {
                   type : 'value',
                   scale:true,
                   axisLabel : {
                      formatter: '{value}'
                   }
                }, {
                   type : 'value',
                   axisLabel : {
                      formatter: '{value}'
                   },
                   'name':'totalAmount'
                }
             ],
             series : [
                {
                   name:'buy',
                   type:'line',
                   data:buy_s1_data,
                   //data:[1.3, 1.5, 1.6,1.7, 1.8, 1.9,1.9],
                   markPoint : {
                      data : [
                         {type : 'max', name: '最大值'},
                         {type : 'min', name: '最小值'}
                      ]
                   },
                   markLine : {
                      data : [
                         {type : 'average', name: '平均值'}
                      ]
                   }
                },
                {
                   name:'sale',
                   type:'line',
                   data:sale_s1_data,
                   //data:amounTotal_data,
                   //data:[1, -4, 2, 5, 3, 2, 0],
                   markPoint : {
                      data : [
                         {name : 'sale', value : -4, xAxis: 1, yAxis: -1.5}
                      ]
                   },
                   markLine : {
                      data : [
                         {type : 'average', name : '平均值'}
                      ]
                   }
                }
             ]
          };

          //option.xAxis[0].data = [1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7];
          option.xAxis[0].data = xAxis;
          // 为echarts对象加载数据
          myChart.setOption(option);
       }
   );








   // allAmount
   require(
       [
          'echarts',
          'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
       ],
       function aa (ec) {
          // 基于准备好的dom，初始化echarts图表
          var myChart = ec.init(document.getElementById('amount'));
          option = {
             title : {
                text: '未来一周气温变化',
                subtext: 'amount'
             },
             tooltip : {
                trigger: 'axis'
             },
             legend: {
                data:['最高气温','最低气温']
             },
             toolbox: {
                show : true,
                feature : {
                   mark : {show: true},
                   dataView : {show: true, readOnly: false},
                   magicType : {show: true, type: ['line', 'bar']},
                   restore : {show: true},
                   saveAsImage : {show: true}
                }
             },
             calculable : true,
             xAxis : [
                {
                   type : 'category',
                   boundaryGap : false,
                   data : ['周一','周二','周三','周四','周五','周六','周日']
                }
             ],
             yAxis : [
                {
                   type : 'value',
                   scale:true,
                   axisLabel : {
                      formatter: '{value}'
                   }
                }, {
                   type : 'value',
                   axisLabel : {
                      formatter: '{value}'
                   },
                   'name':'totalAmount'
                }
             ],
             series : [
                {
                   name:'最高气温',
                   type:'line',
                   data:amounTotal_data,
                   //data:[1.3, 1.5, 1.6,1.7, 1.8, 1.9,1.9],
                   markPoint : {
                      data : [
                         {type : 'max', name: '最大值'},
                         {type : 'min', name: '最小值'}
                      ]
                   },
                   markLine : {
                      data : [
                         {type : 'average', name: '平均值'}
                      ]
                   }
                },
                {
                   name:'最低气温',
                   type:'line',
                   data:sale_amountAllList,
                   //data:amounTotal_data,
                   //data:[1, -4, 2, 5, 3, 2, 0],
                   markPoint : {
                      data : [
                         {name : '周最低', value : -4, xAxis: 1, yAxis: -1.5}
                      ]
                   },
                   markLine : {
                      data : [
                         {type : 'average', name : '平均值'}
                      ]
                   }
                }
             ]
          };

          //option.xAxis[0].data = [1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7];
          option.xAxis[0].data = xAxis;
          // 为echarts对象加载数据
          myChart.setOption(option);
       }
   );










   // 使用
   //require(
   //    [
   //       'echarts',
   //       'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
   //    ],
   //    function aa (ec) {
   //       // 基于准备好的dom，初始化echarts图表
   //       var myChart = ec.init(document.getElementById('byTime'));
   //       option = {
   //          tooltip : {
   //             trigger: 'axis'
   //          },
   //          legend: {
   //             data:['最高','最低']
   //          },
   //          toolbox: {
   //             show : true,
   //             feature : {
   //                mark : {show: true},
   //                dataZoom : {show: true},
   //                dataView : {show: true},
   //                magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
   //                restore : {show: true},
   //                saveAsImage : {show: true}
   //             }
   //          },
   //          calculable : true,
   //          dataZoom : {
   //             show : true,
   //             realtime : true,
   //             start : 20,
   //             end : 80
   //          },
   //          xAxis : [
   //             {
   //                type : 'category',
   //                boundaryGap : false,
   //                data :  buy_s1_data
   //             }
   //          ],
   //          yAxis : [
   //             {
   //                type : 'value'
   //             }
   //          ],
   //          series : [
   //             {
   //                name:'最高',
   //                type:'line',
   //                data: buy_s1_data
   //             },
   //             {
   //                name:'最低',
   //                type:'line',
   //                data:[]
   //             }
   //          ]
   //       };
   //
   //
   //       //option.xAxis[0].data = [1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7];
   //       option.xAxis[0].data = xAxis;
   //       // 为echarts对象加载数据
   //       myChart.setOption(option);
   //    }
   //);
});