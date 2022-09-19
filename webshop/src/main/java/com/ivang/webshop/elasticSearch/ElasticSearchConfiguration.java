package com.ivang.webshop.elasticSearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@Configuration
@ComponentScan(basePackages = { "com.ivang.webshop.service" })
public class ElasticSearchConfiguration {
    
    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String hostAndPort;

    @Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(hostAndPort)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}
