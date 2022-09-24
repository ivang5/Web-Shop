package com.ivang.webshop.lucene.search;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.ivang.webshop.lucene.model.SimpleQuery;
import com.ivang.webshop.lucene.model.shop.OrderEs;
import com.ivang.webshop.lucene.model.shop.dto.OrderRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderRetriever {
    
    private final ElasticsearchOperations elasticsearchOperations;

    public List<OrderRequestDTO> findBoolean(String comment, int fromRate, int toRate, double fromPrice, double toPrice, String operation, boolean fuzzy) {
        log.info("Searching for orders with comment: '{}', rate from: {} to {} and price from {} to {}", comment, fromRate, toRate, fromPrice, toPrice);
        
        if (toRate == 0) {
            toRate = 100000000;
        }

        if (toPrice == 0) {
            toPrice = 100000000;
        }
        
        String rangeRate = fromRate + "-" + toRate;
        String rangePrice = fromPrice + "-" + toPrice;
        QueryBuilder commentQuery;

        if (fuzzy) {
            commentQuery = SearchQueryGenerator.createFuzzyQueryBuilder(new SimpleQuery("comment", comment));
        } else {
            commentQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQuery("comment", comment));
        }

        QueryBuilder rateQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQuery("rate", rangeRate));
        QueryBuilder priceQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQuery("price", rangePrice));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (operation.equalsIgnoreCase("AND")) {
            if (!comment.equals("")) {
                boolQuery.must(commentQuery);
            }
            if (toRate != 100000000 && fromRate != 0) {
                boolQuery.must(rateQuery);
            }
            if (toRate != 100000000 && fromRate != 0) {
                boolQuery.must(priceQuery);
            }
        }else if(operation.equalsIgnoreCase("OR")){
            if (!comment.equals("")) {
                boolQuery.should(commentQuery);
            }
            if (toRate != 100000000 && fromRate != 0) {
                boolQuery.should(rateQuery);
            }
            if (toRate != 100000000 && fromRate != 0) {
                boolQuery.should(priceQuery);
            }
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(boolQuery)
			.build();

        SearchHits<OrderEs> productHits = elasticsearchOperations
			.search(searchQuery, OrderEs.class, IndexCoordinates.of("orders"));

        return OrderRequestDTO.mapDtos(productHits);
    }
}
