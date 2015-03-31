/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.AbstractHierarchicalFileConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.resolver.DefaultEntityResolver;
import org.apache.commons.configuration.resolver.EntityRegistry;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class JsonConfiguration  extends AbstractHierarchicalFileConfiguration
implements EntityResolver, EntityRegistry
{
	private ObjectMapper objectMapper = new ObjectMapper();

	private MyResponse response;
	 /**
	 * 
	 */
	private static final long serialVersionUID = 5343951855562047997L;
	/** The EntityResolver to use */
    private EntityResolver entityResolver = new DefaultEntityResolver();
    
    
    
	@Override
	public void load(InputStream in) throws ConfigurationException
	{
		try
		{
			response = objectMapper.readValue(in, MyResponse.class);
			ConfigurationNode root = getRootNode();
			
			ConfigurationNode node = createNode("data");
            node.setValue(response.getData());
            
            ConfigurationNode sign = createNode("sign");
            sign.setValue(response.getSign());
            
			root.addChild(sign);
			root.addChild(node);
		}
		catch (JsonParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void load(Reader in) throws ConfigurationException
	{
			try
			{
				response = objectMapper.readValue(in, MyResponse.class);
				ConfigurationNode root = getRootNode();
				
				ConfigurationNode node = createNode("data");
	            node.setValue(response.getData());
	            
	            ConfigurationNode sign = createNode("sign");
	            node.setValue(response.getSign());
	            
				root.addChild(sign);
				root.addChild(node);
			}
			catch (JsonParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JsonMappingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public void save(Writer out) throws ConfigurationException
	{
		try
		{
			objectMapper.writeValue(out, response);
		}
		catch (JsonGenerationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonMappingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void registerEntityId(String publicId, URL entityURL)
	{
		 if (entityResolver instanceof EntityRegistry)
	        {
	            ((EntityRegistry) entityResolver).registerEntityId(publicId, entityURL);
	        }
	}

	public Map<String, URL> getRegisteredEntities()
	{
		if (entityResolver instanceof EntityRegistry)
        {
            return ((EntityRegistry) entityResolver).getRegisteredEntities();
        }
        return new HashMap<String, URL>();
	}

	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
	{
		try
        {
            return entityResolver.resolveEntity(publicId, systemId);
        }
        catch (IOException e)
        {
            throw new SAXException(e);
        }
	}

	public EntityResolver getEntityResolver()
	{
		return entityResolver;
	}

	public void setEntityResolver(EntityResolver entityResolver)
	{
		this.entityResolver = entityResolver;
	}
	
    @Override
    protected FileConfigurationDelegate createDelegate()
    {
        return new JsonFileConfigurationDelegate();
    }

	

	   /**
     * A special implementation of the {@code FileConfiguration} interface that is
     * used internally to implement the {@code FileConfiguration} methods
     * for {@code XMLConfiguration}, too.
     */
    private class JsonFileConfigurationDelegate extends FileConfigurationDelegate
    {
        @Override
        public void load(InputStream in) throws ConfigurationException
        {
            JsonConfiguration.this.load(in);
        }
    }
}
