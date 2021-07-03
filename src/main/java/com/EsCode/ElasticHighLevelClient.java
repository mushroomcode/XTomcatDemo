package com.EsCode;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * 方法一：使用连接方式
 */
public class ElasticHighLevelClient {

    public static void main(String[] args) {
        // 与ElasticSearch 建立连接, 导出表结构
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("0.0.0.0", 9200, "http")
        ));
        try {
//            System.out.println("yes");
            GetRequest getRequest = new GetRequest("my_car", "civic");
            GetResponse response = null;
            try {
                response = client.get(getRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null && response.getSource() != null) {
        //            System.out.println(response.("my_car"));
                Map<String, Object> map = response.getSource();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            }
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
