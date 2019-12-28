$(function() {
//    var shopId = 1;
    var productName = '';
    getProductSellDailyList();
    getList();
    function getList() {
    		// 获取用户购买信息的URL
        var listUrl = '/o2o/shopadmin/listuserproductmapsbyshop?pageIndex=1&pageSize=9999&productName='
        			+ productName;
        // 访问后台，获取该店铺的购买信息列表
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userProductMapList = data.userProductMapList;
                var tempHtml = '';
                userProductMapList.map(function (item, index) {
                    tempHtml += '' + '<div class="row row-productbuycheck">'
                         + '<div class="col-10">'+ item.product.productName
                         +'</div>'
                         + '<div class="col-40 productbuycheck-time">'
                         + new Date(item.createTime).Format("yyyy-MM-dd hh:mm:ss")
                         +'</div>' + '<div class="col-20">'
                         + item.user.name + '</div>'
                         + '<div class="col-10">'+ item.point + '</div>'
                         + '<div class="col-20">'+ item.operator.name 
                         + '</div>' + '</div>';
                });
                $('.productbuycheck-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.productbuycheck-wrap').empty();
        getList();
    });

//    getList();
    
    function getProductSellDailyList() {
    		var listProductSellDailyUrl = '/o2o/shopadmin/listproductselldailyinfobyshop';
    		$.getJSON(listProductSellDailyUrl, function (data) {
    			if (data.success) {
    				var myChart = echarts.init(document.getElementById('chart'));
    				var option = generateStaticEchartPart();
    				option.legend.data = data.legendData;
    				option.xAxis = data.xAxis;
    				option.series = data.series;
    				myChart.setOption(option);
    			}
    		 });
    }

    

    /** echarts逻辑部分 **/
//    var myChart = echarts.init(document.getElementById('chart'));
//
//    var option = {
//        tooltip : {
//            trigger: 'axis',
//            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
//                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
//            }
//        },
//        legend: {
//            data:['茉香奶茶','绿茶拿铁','冰雪奇缘']
//        },
//        grid: {
//            left: '3%',
//            right: '4%',
//            bottom: '3%',
//            containLabel: true
//        },
//        xAxis : [
//            {
//                type : 'category',
//                data : ['周一','周二','周三','周四','周五','周六','周日']
//            }
//        ],
//        yAxis : [
//            {
//                type : 'value'
//            }
//        ],
//        series : [
//            {
//                name:'茉香奶茶',
//                type:'bar',
//                data:[120, 132, 101, 134, 290, 230, 220]
//            },
//            {
//                name:'绿茶拿铁',
//                type:'bar',
//                data:[60, 72, 71, 74, 190, 130, 110]
//            },
//            {
//                name:'冰雪奇缘',
//                type:'bar',
//                data:[62, 82, 91, 84, 109, 110, 120]
//            }
//        ]
//    };
//
//    myChart.setOption(option);


    function generateStaticEchartPart() {
        var option = {
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
//                    data:['茉香奶茶','绿茶拿铁','冰雪奇缘']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
//                        type : 'category',
//                        data : ['周一','周二','周三','周四','周五','周六','周日']
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
//                        name:'茉香奶茶',
//                        type:'bar',
//                        data:[120, 132, 101, 134, 290, 230, 220]
//                    },
//                    {
//                        name:'绿茶拿铁',
//                        type:'bar',
//                        data:[60, 72, 71, 74, 190, 130, 110]
//                    },
//                    {
//                        name:'冰雪奇缘',
//                        type:'bar',
//                        data:[62, 82, 91, 84, 109, 110, 120]
                    }
                ]
            };
        return option;
    }



});