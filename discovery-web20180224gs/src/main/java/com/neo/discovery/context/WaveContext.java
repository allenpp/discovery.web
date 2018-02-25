package com.neo.discovery.context;

import com.neo.discovery.util.HttpClientUtils;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by Administrator on 2/11/2018.
 */
public class WaveContext {




    public static String getPrice(String marketId){
        String url = "https://www.betfair.com.au/www/sports/exchange/readonly/v1/bymarket?_ak=nzIFcwyWhrlwYMrh&alt=json&marketIds=1.139185491,1.139493976,1.139185657,1.139496339,1.139751383,1.139493514,1.139493794,1.139496889,1.140014852,1.140031881,1.139849950,1.140044837,1.139849952,1.139857395,1.140049338,1.140049337,1.140049330,1.140049336,1.139790477,1.139790085,1.139790141,1.139790253,1.140042377,1.140041905,1.140042220,1.140042685,1.139591744,1.140037838,1.140037803,1.139591919&types=MARKET_STATE";
        String price = HttpClientUtils.doHttpsGetWithCookie(url,null,"","");

        return "";
    }
}
