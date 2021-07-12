package com.EsCode.EsLessons;

import com.EsCode.EsCreateIndex;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateMenuIndex {

    public void createMenuIndex(RestHighLevelClient client) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", "kimchy");
        map.put("postDate", new Date());
        map.put("msg", "rying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1").source(map);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

}
