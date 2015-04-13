/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class ByteBufferDemo
{
	public static void main(String[] args) throws Exception
	{
		    RandomAccessFile aFile = new RandomAccessFile("src/main/resources/nio-data.txt", "rw");
		    FileChannel inChannel = aFile.getChannel();

		    ByteBuffer buf = ByteBuffer.allocate(48);

		    int bytesRead = inChannel.read(buf);
		    while (bytesRead != -1) {

		      System.out.println("Read " + bytesRead);
		      buf.flip();

		      while(buf.hasRemaining()){
		          System.out.print((char) buf.get());
		      }

		      buf.clear();
		      bytesRead = inChannel.read(buf);
		    }
		    aFile.close();
	}
}
