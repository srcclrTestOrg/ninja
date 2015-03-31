package com.mozart;

import java.io.IOException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class NinjaHttpGet
{
	public static void main(String[] args) throws ClientProtocolException, IOException
	{
		CloseableHttpResponse response =  null;
		
		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

		    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
		        // Honor 'keep-alive' header
		        HeaderElementIterator it = new BasicHeaderElementIterator(
		                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
		        while (it.hasNext()) {
		            HeaderElement he = it.nextElement();
		            String param = he.getName();
		            String value = he.getValue();
		            if (value != null && param.equalsIgnoreCase("timeout")) {
		                try {
		                    return Long.parseLong(value) * 1000;
		                } catch(NumberFormatException ignore) {
		                }
		            }
		        }
		        HttpHost target = (HttpHost) context.getAttribute(
		                HttpClientContext.HTTP_TARGET_HOST);
		        if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
		            // Keep alive for 5 seconds only
		            return 5 * 1000;
		        } else {
		            // otherwise keep alive for 30 seconds
		            return 30 * 1000;
		        }
		    }
		};
		    
		try {
			CloseableHttpClient httpclient = HttpClients.custom().setKeepAliveStrategy(myStrategy).build();
			HttpGet httpget = new HttpGet(
				     "http://www.baidu.com");
			response = httpclient.execute(httpget);
			System.out.println(response);
		} finally {
		    response.close();
		}
	}
}
