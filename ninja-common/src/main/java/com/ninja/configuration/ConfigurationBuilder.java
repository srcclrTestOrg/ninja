/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.configuration;

import java.io.File;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.DefaultConfigurationBuilder.ConfigurationDeclaration;
import org.apache.commons.configuration.DefaultConfigurationBuilder.FileConfigurationProvider;
import org.apache.commons.configuration.resolver.EntityRegistry;
import org.xml.sax.EntityResolver;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class ConfigurationBuilder extends DefaultConfigurationBuilder
{
	
	 /** Constant for the XML file extension. */
    static final String EXT_JSON = ".json";
    
    static final String EXT_YML = ".yml";
    
	 /** Constant for the provider for json files. */
    private static final ConfigurationProvider JSON_PROVIDER = new JsonConfigurationProvider();
	
	 public ConfigurationBuilder() {
		  super();
		  addConfigurationProvider("json", JSON_PROVIDER);
	 }
	 
   public ConfigurationBuilder(File file)
    {
        this();
        setFile(file);
    }
   
   public ConfigurationBuilder(String fileName)
           throws ConfigurationException
   {
       this();
       setFileName(fileName);
   }
}

class JsonConfigurationProvider extends FileConfigurationProvider {
	/**
     * Creates a new instance of {@code JsonConfigurationProvider}.
     */
    public JsonConfigurationProvider()
    {
        super(JsonConfiguration.class);
    }

    /**
     * Returns a new empty configuration instance. This implementation
     * performs some additional initialization specific to Json
     * configurations.
     *
     * @param decl the configuration declaration
     * @return the new configuration
     * @throws Exception if an error occurs
     */
    @Override
    public AbstractConfiguration getEmptyConfiguration(
            ConfigurationDeclaration decl) throws Exception
    {
    	JsonConfiguration config = (JsonConfiguration) super
                .getEmptyConfiguration(decl);

        DefaultConfigurationBuilder builder = decl
                .getConfigurationBuilder();
        EntityResolver resolver = builder.getEntityResolver();
        if (resolver instanceof EntityRegistry)
        {
            // copy the registered entities
            config.getRegisteredEntities().putAll(
                builder.getRegisteredEntities());
        }
        else
        {
            config.setEntityResolver(resolver);
        }
        return config;
    }
}
