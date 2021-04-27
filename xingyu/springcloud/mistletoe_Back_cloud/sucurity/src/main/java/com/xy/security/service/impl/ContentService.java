//package com.xy.security.service.impl;
//
//import com.xy.model.LoginRecodes;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.TermQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//@Service
//public class ContentService {
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//    //实现搜索功能
//    public List<Map<String, Object>> searchHighlightPage(LoginRecodes loginRecodes) throws IOException {
//
//        //创建搜索请求
//        SearchRequest searchRequest = new SearchRequest("索引名");
//        //构造搜索参数
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		//设置需要精确查询的字段
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("filed", loginRecodes);
//        searchSourceBuilder.query(termQueryBuilder);
//
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        //高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        //设置高亮字段
//        highlightBuilder.field("filed");
//        //如果要多个字段高亮,这项要为false
//        highlightBuilder.requireFieldMatch(true);
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//
//		//下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
//		highlightBuilder.fragmentSize(800000); //最大高亮分片数
//   		highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
//        searchSourceBuilder.highlighter(highlightBuilder);
//
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (SearchHit hit : response.getHits().getHits()) {
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            //解析高亮字段
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            HighlightField field= highlightFields.get("field");
//            if(field!= null){
//                Text[] fragments = field.fragments();
//                String n_field = "";
//                for (Text fragment : fragments) {
//                    n_field += fragment;
//                }
//                //高亮标题覆盖原标题
//                sourceAsMap.put("field",n_field);
//            }
//            list.add(hit.getSourceAsMap());
//        }
//        return list;
//    }
//}