package com.EsCode.EsLessons;

import org.apache.logging.log4j.util.BiConsumer;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.Map;

public class GetMenuIndex {
    /**
     * 获取索引的信息
     * @param client
     * @throws IOException
     */
    public void getMenuIndex(RestHighLevelClient client) throws IOException {
        GetRequest getRequest = new GetRequest("posts", "1");
        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        String index = getResponse.getIndex();
        String id = getResponse.getId();
        System.out.println(id);
        if(getResponse.isExists()) {
            Map<String, Object> map = getResponse.getSourceAsMap();
            for(Map.Entry<String, Object> kv : map.entrySet()) {
                System.out.println(kv.getKey());
            }
        }
    }

}
