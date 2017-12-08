package com.taotao.search.service;

import com.taotao.common.pojo.SearchResultVo;

public interface SearchService {
    SearchResultVo search(String queryString,int page,int rows) throws Exception;
}
