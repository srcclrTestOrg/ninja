package com.ninja.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyResponse
{
	private String data;
	private String sign;
	public String getData()
	{
		return data;
	}
	public void setData(String data)
	{
		this.data = data;
	}
	public String getSign()
	{
		return sign;
	}
	public void setSign(String sign)
	{
		this.sign = sign;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		
		MyResponse response  = objectMapper.readValue(new FileInputStream(new File("src/main/resources/config/datas/test.json")), MyResponse.class);
		System.out.println(response.getData());
	}
	

}
