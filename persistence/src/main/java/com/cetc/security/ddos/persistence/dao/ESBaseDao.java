package com.cetc.security.ddos.persistence.dao;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


public class ESBaseDao {
	
	//private static Logger logger = AntiLogger.getLogger(ESBaseDao.class);
	private SimpleDateFormat formatter = new SimpleDateFormat("-yyyy.MM.dd");
	protected Client client;
	private String clustername = "elasticsearch";
	
	public ESBaseDao(String address, int port) {
		init(this.clustername, address, port);
	}

	public ESBaseDao(String cluster, String address, int port) {
		init(cluster, address, port);
	}
	
	
	private void init(String clustername, String address, int port) {
		this.clustername = clustername;
		ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();
		builder.put("cluster.name", clustername);
		builder.put("client.transport.sniff", true);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		Settings settings = builder.build();
		TransportClient client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(address, port));
		this.client = client;
	}

	/*
	private String genIndexbyPrefix(String prefix) {
		String index = prefix + formatter.format(new Date());
		return index;
	}
	*/
	
	public void send(String indexprefix, String type, String entry) {
		//String index = genIndexbyPrefix(indexprefix);
		try {
			client.prepareIndex(indexprefix, type).setSource(entry).execute();
		} catch (Exception e) {
			//logger.error("Failed to index Tuple: {}", entry, e);
		}
	}
	
	/***********************start*/
	
	public String get(String indexprefix, String type)
	{
		String xxx;
		
		GetResponse response = client.prepareGet(indexprefix, type, "1")
		        .execute()
		        .actionGet();
		
		xxx =  response.getSourceAsString();
		
		
		return xxx;
		
	}
	
	public List<String> searchAll(String indexprefix, String type)
	{	
		List<String> pJson = new ArrayList<String>();
		
		SearchResponse response = client.prepareSearch(indexprefix)
				.setTypes(type)
				.setSearchType(SearchType.QUERY_AND_FETCH)
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
	
	public List<String> searchAllID(String indexprefix, String type)
	{	
		List<String> pID = new ArrayList<String>();
		
		SearchResponse response = client.prepareSearch(indexprefix)
				.setTypes(type)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
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
             System.out.println(hits.getHits()[i].getId());
             pID.add(hits.getHits()[i].getId());
        }	
		
		return pID;
	}
	
	
	
	public void delete (String indexprefix, String type, String id)
	{
		DeleteResponse response = client.prepareDelete(indexprefix, type, id)
		        .execute()
		        .actionGet();		
		
	}
	
	/***********************end*/
	
	public void sendWithIndex(String index, String type, String entry) {
		try {
			client.prepareIndex(index, type).setSource(entry).execute();
		} catch (Exception e) {
			//logger.error("Failed to index Tuple: {}", entry, e);
		}
	}
	
	public void send(String indexprefix, String type, List<String> entry) {
		//String index = genIndexbyPrefix(indexprefix);
		try {
			BulkRequestBuilder request = client.prepareBulk();
			for (String ent : entry) {
				request.add(client.prepareIndex(indexprefix, type).setSource(ent));
			}
			request.execute();
		} catch (Exception e) {
			//logger.error("Failed to index Tuple: " + entry);
		}
	}

	public void upsert(String indexprefix, String type, String id, String newValue) {
		//String index = genIndexbyPrefix(indexprefix);
		try {
			IndexRequest indexRequest = new IndexRequest(indexprefix, type, id)
					.source(newValue);
			UpdateRequest updateRequest = new UpdateRequest(indexprefix, type, id)
					.doc(newValue).upsert(indexRequest);

			client.update(updateRequest);
		} catch (Exception e) {
			//logger.error("Failed to update [{}] : ", id, e);
		}
	}

	public void close() {
		client.close();
	}

}
