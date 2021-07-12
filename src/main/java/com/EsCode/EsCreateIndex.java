package com.EsCode;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建一个索引
 */
public class EsCreateIndex {

    public void createIndex(String name, RestHighLevelClient restHighLevelClient) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("brand", "toyota");
        map.put("type", "jiaoche");
        map.put("message", "this is a toyota crowd");
        IndexRequest indexRequest = new IndexRequest("cars").id("1").source(map);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//            System.out.println(response);
//            System.out.println(response.getIndex());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EsCreateIndex esCreateIndex = new EsCreateIndex();
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder
                (new HttpHost("0.0.0.0",9200,"http")));
        esCreateIndex.createIndex("", client);
    }

}
