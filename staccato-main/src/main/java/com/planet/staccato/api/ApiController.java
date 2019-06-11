package com.planet.staccato.api;

import com.planet.staccato.SearchRequestUtils;
import com.planet.staccato.dto.api.SearchRequest;
import com.planet.staccato.model.Item;
import com.planet.staccato.model.ItemCollection;
import com.planet.staccato.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 *  Defines the controller interface for the STAC API specification
 *  @see <a href="https://github.com/radiantearth/stac-spec/tree/master/api-spec">api-spec</a>
 *
 * @author joshfix
 * Created on 11/29/17
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stac")
public class ApiController implements ApiApi {

    private final ApiService service;

    @Override
    public Mono<Item> getItem(@PathVariable("id") String id) {
        return service.getItem(id, null).name("getItem");
    }


    @Override
    public Mono<ItemCollection> getItems(double[] bbox, String time, String query, Integer limit, Integer page,
                                         String[] ids, String[] collections, String[] fields, Object intersects) {
        SearchRequest searchRequest = SearchRequestUtils.generateSearchRequest(bbox, time, query, limit, page, fields, ids, collections, intersects);
        return service.getItems(searchRequest).name("getItems");
    }

    @Override
    public Mono<ItemCollection> getItemsPost(SearchRequest searchRequest) {
        return service.getItems(searchRequest).name("getItemsPost");
    }

    @Override
    public Flux<Item> getItemsStream(double[] bbox, String time, String query, Integer limit, Integer page,
                                     String[] ids, String[] collections, String[] fields, Object intersects) {
        SearchRequest searchRequest = SearchRequestUtils.generateSearchRequest(bbox, time, query, limit, page, fields, ids, collections, intersects);
        return service.getItemsFlux(searchRequest);
    }

    @Override
    public Flux<Item> getItemsPostStream(SearchRequest searchRequest) {
        return service.getItemsFlux(searchRequest);
    }

}
