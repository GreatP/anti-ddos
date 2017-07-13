package com.cetc.security.ddos.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.index.query.FilterBuilders;
//import org.elasticsearch.index.query.QueryBuilders.*;
public class ESFlowDataDao extends ESBaseDao {

	public ESFlowDataDao(String address, int port) {
		super(address, port);		
	}
	
	public ESFlowDataDao(String cluster, String address, int port) {
		super(cluster, address, port);		
	}
	
	
	
	public List<String> SearchByTimeStamp(String indexprefix, String type, long start, long end)
	{
		List<String> pJson = new ArrayList<String>();
		
		SearchResponse response = super.client.prepareSearch(indexprefix)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setPostFilter(FilterBuilders.rangeFilter("timestamp").from(start).to(end))
				.addSort("timestamp", SortOrder.ASC)
				.setFrom(0).setSize(2147483647).setExplain(true)
				.execute()
				.actionGet();
		
		SearchHits hits = response.getHits();
		
			
		if (0 == hits.getHits().length)
		{
			return null;			
		}
		
		for (int i = 0; i < hits.getHits().length; i++) {
             System.out.println(hits.getHits()[i].getSourceAsString());
             pJson.add(hits.getHits()[i].getSourceAsString());
        }
		
		return pJson;
		
	}

}
