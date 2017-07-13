package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.FlowDataEntity;
import com.cetc.security.ddos.persistence.dao.ESFlowDataDao;
//import com.cetc.security.ddos.persistence.dao.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;



//import java.util.ArrayList;
import java.util.List;


@Service
public class FlowDataService {

	ESFlowDataDao producer;
	
	public FlowDataService() throws IOException 
	{
		Properties props = PropertiesLoaderUtils.loadAllProperties("config.properties");
		String esip = props.get("elasticsearch.ip").toString();
		int esport = Integer.valueOf(props.get("elasticsearch.port").toString());;
		producer = new ESFlowDataDao(esip,esport);
		//producer = new ESFlowDataDao("127.0.0.1",9300);
	}
	
	public FlowDataService(String address, int port) 
	{
		producer = new ESFlowDataDao(address,port);
	}
	
	String buildFlowDataJson(String POName, int flowIndex, long timeStamp, double pktRate, double byteRate)
	{
		
		String jsonStr = "xxxx";
		ObjectMapper fasterxmlObjMapper;
		
		FlowDataEntity flowDataEntity = new FlowDataEntity(timeStamp, pktRate, byteRate);
		
		fasterxmlObjMapper  = new ObjectMapper();
		
		fasterxmlObjMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		fasterxmlObjMapper.setSerializationInclusion(Include.NON_NULL);

		try {
			jsonStr = fasterxmlObjMapper.writeValueAsString(flowDataEntity);
			System.out.println("json is " + jsonStr);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonStr;
	}
	
	public void addFlowData(String POName, int flowIndex, String type, long timeStamp, double pktRate, double byteRate)
	{
		String jsonStr;
		
		String index = POName + "-" + flowIndex;
		
		jsonStr = buildFlowDataJson(POName, flowIndex, timeStamp, pktRate, byteRate);
		
		//ESBaseDao producer = new ESBaseDao("10.111.121.27",9300);
		
		producer.send(index, type, jsonStr);
		
		//producer.close();
		
		
	}
	
	public void searchxxx(String POName, int flowIndex, String type)
	{
		String xxx;
		
		//ESBaseDao producer = new ESFlowDataDao("10.111.121.27",9300);
		
		xxx = producer.get("flowdata", type);
		
		System.out.println("xxx is " + xxx);
		
		//producer.close();
		
		
	}
	
	public String getAllFlowData(String POName, int flowIndex, String type)
	{
		List<String> pJson = null;
		String OneJson = "[";
		String index = POName + "-" + flowIndex;
		//ESFlowDataDao producer = new ESFlowDataDao("10.111.121.27",9300);
		
		pJson = producer.searchAll(index, type);
		
		if (null != pJson)
		{
			int i = 0;	
			
			for (String tmp : pJson) {
				if (i != 0)
				{
					OneJson += ",";
				}
				else
				{
					i = 1;
				}
				
				OneJson += tmp;
	        }
			
			OneJson += "]";
			
			System.out.println("xxx is " + 	OneJson);
			
			return OneJson;
		}
		else
		{
			return null;
			
		}
	}
	
	
	public String getDataByTime(String POName, int flowIndex, String type, long  start, long end)
	{
		List<String> pJson = null;
		String index = POName + "-" + flowIndex;
		String OneJson = "[";
		//ESFlowDataDao producer = new ESFlowDataDao("10.111.121.27",9300);
		
		pJson = producer.SearchByTimeStamp(index, type, start, end);
		
		if (null != pJson)
		{
			int i = 0;	
			
			for (String tmp : pJson) {
				if (i != 0)
				{
					OneJson += ",";
				}
				else
				{
					i = 1;
                    OneJson += "{\"typePara\":\"" + type+"\"},";//把type类型传给前端
				}
				
				OneJson += tmp;
	        }
			
			OneJson += "]";
			
			System.out.println("xxx is " + 	OneJson);
			
			return OneJson;
		}
		else
		{
			return null;
			
		}
		
		//System.out.println("xxx is " + xxx);
		
		//producer.close();
		
	}
	
	
	void deleteall(String POName, int flowIndex, String type)
	{
		List<String> pID = null;
		String index = POName + "-" + flowIndex;
		
		pID = producer.searchAllID(index, type);
	}
	
	void deletealltest(String index, String type)
	{
		List<String> pID = null;
				
		pID = producer.searchAllID(index, type);
		
		if (null == pID)
		{
			return;
			
		}
		
		for (String tmp : pID) {
			producer.delete(index, type, tmp);
        }
	}
	
}
