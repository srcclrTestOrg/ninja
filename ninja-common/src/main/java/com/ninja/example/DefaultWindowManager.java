/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.example;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class DefaultWindowManager implements WindowManager
{
    // are windows allowed to be resized?
    private boolean resizable;
    // do windows have a close button?
    private boolean closable;
    
    // Default size of new windows
    private int defaultWidth;
    private int defaultHeight;
    
    WindowStyleDefinition styleDefinition;

	public boolean isResizable()
	{
		return resizable;
	}

	public void setResizable(boolean resizable)
	{
		this.resizable = resizable;
	}

	public boolean isClosable()
	{
		return closable;
	}

	public void setClosable(boolean closable)
	{
		this.closable = closable;
	}

	public int getDefaultWidth()
	{
		return defaultWidth;
	}

	public void setDefaultWidth(int defaultWidth)
	{
		this.defaultWidth = defaultWidth;
	}

	public int getDefaultHeight()
	{
		return defaultHeight;
	}

	public void setDefaultHeight(int defaultHeight)
	{
		this.defaultHeight = defaultHeight;
	}

	public WindowStyleDefinition getStyleDefinition()
	{
		return styleDefinition;
	}

	public void setStyleDefinition(WindowStyleDefinition styleDefinition)
	{
		this.styleDefinition = styleDefinition;
	}
    
    // getters and setters ommitted, also the WindowManager methods
    
    
}
