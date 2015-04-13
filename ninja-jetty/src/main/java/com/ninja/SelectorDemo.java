/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class SelectorDemo
{
	public static void main(String[] args) throws Exception
	{
		Selector selector = Selector.open();
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(8086));
		//ssc.configureBlocking(false);
		
		//ssc.register(selector,SelectionKey.OP_ACCEPT);
		
		SocketChannel channel = ssc.accept();
		channel.configureBlocking(false);
		
		channel.register(selector, SelectionKey.OP_READ);
		while (true)
		{
			int readyChannels = selector.select();
			if (readyChannels == 0)
				continue;
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
			while (keyIterator.hasNext())
			{
				SelectionKey key = keyIterator.next();
				if (key.isAcceptable())
				{
					// a connection was accepted by a ServerSocketChannel.
				}
				else if (key.isConnectable())
				{
					// a connection was established with a remote server.
				}
				else if (key.isReadable())
				{
					
					// a channel is ready for reading
					ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
					int reads = channel.read(byteBuffer);
					while (reads > 0)
					{
						byteBuffer.flip();
						while (byteBuffer.hasRemaining())
						{
							System.out.print((char) byteBuffer.get());
						}
						byteBuffer.clear();
						reads = channel.read(byteBuffer);
					}
					SelectionKey sk  = channel.register(selector, SelectionKey.OP_WRITE);
					break;
				}
				else if (key.isWritable())
				{
					SocketChannel sc = (SocketChannel)key.channel();
					sc.write(ByteBuffer.wrap("Hello World".getBytes()));
					sc.socket().close();
					sc.close();
					break;
				}
				keyIterator.remove();
				break;
			}
		}
		
	}
}
