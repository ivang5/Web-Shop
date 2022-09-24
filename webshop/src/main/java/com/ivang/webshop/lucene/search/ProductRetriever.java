package com.ivang.webshop.lucene.search;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import com.ivang.webshop.lucene.model.SimpleQuery;
import com.ivang.webshop.lucene.model.shop.ProductEs;
import com.ivang.webshop.lucene.model.shop.dto.ProductRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductRetriever {

	private final ElasticsearchOperations elasticsearchOperations;

    public List<ProductRequestDTO> findBoolean(String name, String text, double from, double to, String operation, boolean fuzzy) {
        log.info("Searching for products with name: '{}', description: '{}' and price from {} to {}", name, text, from, to);
        
        if (to == 0) {
            to = 100000000;
        }
        
        String range = from + "-" + to;
        QueryBuilder nameQuery;
        QueryBuilder descQuery;

        if (fuzzy) {
            nameQuery = SearchQueryGenerator.createFuzzyQueryBuilder(new SimpleQuery("name", name));
            descQuery = SearchQueryGenerator.createFuzzyQueryBuilder(new SimpleQuery("detailedDescription", text));
        } else {
            nameQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQuery("name", name));
            descQuery = SearchQueryGenerator.createMatchQueryBuilder(new SimpleQuery("detailedDescription", text));
        }

        QueryBuilder priceQuery = SearchQueryGenerator.createRangeQueryBuilder(new SimpleQuery("price", range));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (operation.equalsIgnoreCase("AND")) {
            if (!name.equals("")) {
                boolQuery.must(nameQuery);
            }
            if (!text.equals("")) {
                boolQuery.must(descQuery);
            }
            if (to != 100000000 && from != 0) {
                boolQuery.must(priceQuery);
            }
        }else if(operation.equalsIgnoreCase("OR")){
            if (!name.equals("")) {
                boolQuery.should(nameQuery);
            }
            if (!name.equals("")) {
                boolQuery.should(descQuery);
            }
            if (to != 100000000 && from != 0) {
                boolQuery.should(priceQuery);
            }
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(boolQuery)
			.build();

        SearchHits<ProductEs> productHits = elasticsearchOperations
			.search(searchQuery, ProductEs.class, IndexCoordinates.of("products"));

        return ProductRequestDTO.mapDtos(productHits);
    }
}
