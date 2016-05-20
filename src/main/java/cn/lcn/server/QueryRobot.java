package cn.lcn.server;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 查询机器人，支持天气，股票，车票价格的查询
 * 此处采用三个方法模拟
 * Created by LCN on 2016/5/19.
 */
public class QueryRobot {

    public final static Cache<ServicePair, String> cache = CacheBuilder.newBuilder().
            expireAfterAccess(2, TimeUnit.HOURS).build();


    public static String query(String serviceName, String serviceParam) throws ExecutionException {
        String data = null;
        if (serviceName.equals("\\weather")) {
            data = queryWeather(serviceParam) + "\r\n";
        } else if (serviceName.equals("\\stock")) {
            data = queryStock(serviceParam) + "\r\n";
        } else if (serviceName.equals("\\ticket")) {
            data = queryTicket(serviceParam) + "\r\n";
        } else {
            data = "Unsupported service\r\n";
        }
        return data;
    }


    private static String queryWeather(String city) {
        //实际调用相应的查询接口
        String data = null;
        if (city.equals("Beijing")) {
            data = "Sun";
        } else if (city.equals("Nanjing")) {
            data = "Rainy";
        } else {
            data = "";
        }
        return data;
    }

    private static String queryStock(final String stock) throws ExecutionException {
        return cache.get(new ServicePair("stock", stock), new Callable<String>() {
            @Override
            public String call() throws Exception {
                //实际调用相应的查询接口
                String data = null;
                if (stock.equals("Microsoft")) {
                    data = "65536";
                } else if (stock.equals("Apple")) {
                    data = "65536";
                } else {
                    data = "can't query it !";
                }
                return data;
            }
        });
    }


    private static String queryTicket(final String ticket) throws ExecutionException {
        return cache.get(new ServicePair("ticket", ticket), new Callable<String>() {
            @Override
            public String call() throws Exception {
                //实际调用相应的查询接口
                String data = null;
                if (ticket.equals("Beijing")) {
                    data = "400 RMB";
                } else if (ticket.equals("Nanjing")) {
                    data = "200 RMB";
                } else {
                    data = "can't query it !";
                }
                return data;
            }
        });
    }

}
