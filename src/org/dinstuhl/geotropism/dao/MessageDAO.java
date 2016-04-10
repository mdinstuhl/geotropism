package org.dinstuhl.geotropism.dao;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import java.util.ArrayList;
import java.net.URI;
import java.net.URLEncoder;
import org.json.*;


import org.dinstuhl.geotropism.domain.GeoMessage;

public class MessageDAO {

	public static ArrayList<GeoMessage> getMessages(String id){
		
		ArrayList<GeoMessage> messages = new ArrayList<GeoMessage>();
		JSONArray jsonArray;
		JSONObject jsonObject;
		HttpClient client;
		HttpGet httpGet;
		HttpContext httpContext;
		HttpResponse response;
		String url;
		HttpEntity entity;
		String responseMsg = null;
		URI uri;
		GeoMessage geoMessage;
		
		try{

			id="blah";
			url = "http://192.168.42.25:8080/geolock-0.1/message/search?value=" + id;
		
			System.out.println(url);
			client = new DefaultHttpClient();
			httpContext = new BasicHttpContext();
			httpGet = new HttpGet(url);
			response = client.execute(httpGet, httpContext);
			entity = response.getEntity();
			responseMsg = getASCIIFromEntity(entity);
			jsonArray = new JSONArray(responseMsg);
			for(int jsonIdx=0; jsonIdx<jsonArray.length(); jsonIdx++){
				jsonObject = jsonArray.getJSONObject(jsonIdx);
				geoMessage = new GeoMessage();
				geoMessage.setBody(jsonObject.getString("msgBody"));
				System.out.println(geoMessage.getBody());
				messages.add(geoMessage);
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return messages;
		
	}
	
	private static String getASCIIFromEntity(HttpEntity entity) throws IOException{
		
		String response = null;
		
		InputStream in = entity.getContent();


		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n>0) {
			byte[] b = new byte[4096];
			n =  in.read(b);


			if (n>0) out.append(new String(b, 0, n));
		}


		response =  out.toString();		
		
		return response;
		
	}
	
}
