package com.EsCode.EsLessons;

import com.EsCode.EsCreateIndex;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class Starter {

    public static void main(String[] args) throws IOException {

//        EsCreateIndex esCreateIndex = new EsCreateIndex();
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder
                (new HttpHost("0.0.0.0",9200,"http")));
//        client.close();

        CreateMenuIndex index = new CreateMenuIndex();
        index.createMenuIndex(client);

        GetMenuIndex getMenuIndex = new GetMenuIndex();
        getMenuIndex.getMenuIndex(client);
        client.close();

    }

}
