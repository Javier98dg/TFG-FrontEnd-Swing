package com.frontswing.client.data.service;

import com.frontswing.client.data.webclientbase.WebClientBase;
import com.frontswing.client.data.entity.SicatpersoEntity;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author jjdg46
 */
public class TercerosRC {
    private static WebClient webClient;
    private static final String url="/api/sicatperso";

    static{
        try {
            webClient=WebClientBase.getWebClient();
        } catch (Exception ex) {
            Logger.getLogger(TercerosRC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    public static SicatpersoEntity getOneId(String id){
        try{
            Mono<SicatpersoEntity> res = webClient.get()
                    .uri(url+"/"+id)
                    .retrieve()
                    .bodyToMono(SicatpersoEntity.class);
            return res.block();
        
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }    
    
    public static SicatpersoEntity[] getAll(){
        Mono<SicatpersoEntity[]> res ;
        
        res=webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(SicatpersoEntity[].class);
        
        return res.block();
    }
    
    public static void create(SicatpersoEntity sicatperso) throws Exception{
        webClient.post()
                .uri(url)
                .body(Mono.just(sicatperso), SicatpersoEntity.class)
                .retrieve()
                .bodyToMono(SicatpersoEntity.class).block();      
    } 
    
    public static void update(SicatpersoEntity sicatperso){
        webClient.put()
                .uri(url)
                .body(Mono.just(sicatperso), SicatpersoEntity.class)
                .retrieve()
                .bodyToMono(SicatpersoEntity.class).block();      
    } 
    
    public static void delete(SicatpersoEntity sicatperso){
        webClient.delete()
                .uri(url+"/"+sicatperso.getId())
                .retrieve()
                .bodyToMono(SicatpersoEntity.class).block();      
    }
    
    public static SicatpersoEntity searchPersoById(String id){
        try {
            id = URLEncoder.encode(id, StandardCharsets.UTF_8.toString());
        } catch (Exception ex) {
            Logger.getLogger(TercerosRC.class.getName()).log(Level.SEVERE, null, ex);
        }          
        Flux<SicatpersoEntity> res = webClient.get()
                .uri(url+"/"+id)
                .retrieve()
                .bodyToFlux(SicatpersoEntity.class);
        
        return res.blockFirst();
    } 
}
