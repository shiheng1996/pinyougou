package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {
    @Reference
    private ItemSearchService itemSearchService;
    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody Map searchmap){
        Map<String, Object> search = itemSearchService.search(searchmap);
        System.out.println(search);
        return search;
    };

}
