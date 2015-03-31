/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mozart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class MyHttpsClient
{
	private static final Logger log = LoggerFactory.getLogger(MyHttpsClient.class);
	private static final String METHOD_POST = "POST";
	private static final String DEFAULT_CHARSET = "utf-8";
	
	public static String doPost(String url, String params, String charset, int connectTimeout, int readTimeout) throws Exception {
		String ctype = "application/json;charset=" + charset;
		byte[] content = {};
		if(params != null){
			content = params.getBytes(charset);
		}
		
		return doPost(url, ctype, content, connectTimeout, readTimeout);
	}
	public static String doPost(String url, String ctype, byte[] content,int connectTimeout,int readTimeout) throws Exception {
		HttpsURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try{
				SSLContext ctx = SSLContext.getInstance("TLS");
		        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
		        SSLContext.setDefault(ctx);

				conn = getConnection(new URL(url), METHOD_POST, ctype);	
				conn.setHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
				conn.setConnectTimeout(connectTimeout);
				conn.setReadTimeout(readTimeout);
			}catch(Exception e){
				log.error("GET_CONNECTOIN_ERROR, URL = " + url, e);
				throw e;
			}
			try{
				out = conn.getOutputStream();
				out.write(content);
				rsp = getResponseAsString(conn);
			}catch(IOException e){
				log.error("REQUEST_RESPONSE_ERROR, URL = " + url, e);
				throw e;
			}
			
		}finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		return rsp;
	}

    private static class DefaultTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
	
	private static HttpsURLConnection getConnection(URL url, String method, String ctype)
			throws IOException {
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "stargate");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}

	protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
		String charset = getResponseCharset(conn.getContentType());
		InputStream es = conn.getErrorStream();
		if (es == null) {
			return getStreamAsString(conn.getInputStream(), charset);
		} else {
			String msg = getStreamAsString(es, charset);
			if (StringUtils.isEmpty(msg)) {
				throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
			} else {
				throw new IOException(msg);
			}
		}
	}

	private static String getStreamAsString(InputStream stream, String charset) throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	private static String getResponseCharset(String ctype) {
		String charset = DEFAULT_CHARSET;

		if (!StringUtils.isEmpty(ctype)) {
			String[] params = ctype.split(";");
			for (String param : params) {
				param = param.trim();
				if (param.startsWith("charset")) {
					String[] pair = param.split("=", 2);
					if (pair.length == 2) {
						if (!StringUtils.isEmpty(pair[1])) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

}
