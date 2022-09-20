package com.ivang.webshop.lucene.indexing;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.ivang.webshop.lucene.model.shop.OrderEs;
import com.ivang.webshop.lucene.model.shop.dto.OrderRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderIndexer {
    
    private final ElasticsearchOperations elasticsearchOperations;

	public String index(OrderEs order) {
		IndexQuery indexQuery = new IndexQueryBuilder()
			.withId(order.getAssociatedId().toString())
			.withObject(order).build();

		String documentId = elasticsearchOperations
			.index(indexQuery, IndexCoordinates.of("orders"));

		return documentId;
	}

	public void indexOrder(OrderRequestDTO orderRequestDTO) {
		OrderEs orderIndexUnit = new OrderEs();
        orderIndexUnit.setAssociatedId(orderRequestDTO.getAssociatedId());
		orderIndexUnit.setComment(orderRequestDTO.getComment());
        orderIndexUnit.setRate(orderRequestDTO.getRate());
		orderIndexUnit.setTime(orderRequestDTO.getTime());
		orderIndexUnit.setPrice(orderRequestDTO.getPrice());
		index(orderIndexUnit);
    }

    public boolean deleteOrder(Long associatedId) {
        String retVal = elasticsearchOperations.delete(associatedId.toString(), IndexCoordinates.of("orders"));
		if(associatedId.equals(Long.parseLong(retVal)))
			return true;
		else
			return false;
    }
}
