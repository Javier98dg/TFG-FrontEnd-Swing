package com.frontswing.client.data.webclientbase;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 *
 * @author jjdg46
 */
public class WebClientBase {
    private static WebClient webClient;
    
    static{
        HttpClient httpClient = HttpClient
            .create();
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 100000)).build();        
        webClient = WebClient.builder()
            .clientConnector(connector)
            .exchangeStrategies(exchangeStrategies)
            .baseUrl("http://localhost:8080")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
    
    public static WebClient getWebClient() throws Exception{
        if(webClient==null){
            throw new Exception("Non autheticate exception");
        }
        return webClient;
    }    
        
}
