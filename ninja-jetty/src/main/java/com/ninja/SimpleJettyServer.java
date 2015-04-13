/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * 
 * @author daizuan
 */
public class SimpleJettyServer {

    private final ConcurrentLinkedQueue<Object> _changes_con       = new ConcurrentLinkedQueue<Object>();
    private final ConcurrentLinkedQueue<Object> _changes_req       = new ConcurrentLinkedQueue<Object>();
    private static int                          DEFAULT_BUFFERSIZE = 16;
    private static String                       DEFAULT_CHARSET    = "GBK";
    private ServerSocketChannel                 channel;
    private Selector                            selector;
    private int                                 port;
    private static final String                 EXIT               = "exit";
    private static final String                 FORMAT             = "yyyy-MM-dd HH:mm:ss";

    public SimpleJettyServer(int port) throws IOException{
        this.port = port;
        this.channel = ServerSocketChannel.open();
        this.selector = Selector.open();

    }

    private String getTime() {
        DateFormat df = new SimpleDateFormat(FORMAT);
        return df.format(new Date());

    }

    public void listen() throws IOException { // 服务器开始监听端口，提供服务
        channel.socket().bind(new InetSocketAddress(port)); // 将scoket榜定在制定的端口上
        channel.configureBlocking(true);
        new Thread(new ConnectionHander()).start();
        new Thread(new RequestExecutor()).start();
        new Thread(new RequestHander()).start();

    }

    class ConnectionHander implements Runnable {

        public void run() {
            System.out.println("ConnectionHander:connection Hander start......");
            while (true) {
                // 分发连接事件
                SocketChannel sc = null;
                try {
                    // 这里阻塞监听连接事件
                    sc = channel.accept();
                    sc.configureBlocking(false);
                    _changes_con.add(sc);
                    // 释放selector的锁,以便接收注册信息
                    selector.wakeup();
                    System.out.println("listener:a client in![" + sc.socket().getRemoteSocketAddress() + "]");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @author daizuan 分发请求
     */
    class RequestHander implements Runnable {

        public void run() {
            System.out.println("RequestHander:Request Hander start......");
            while (true) {
                try {
                    // 阻塞，直到有请求进来
                    selector.select();
                    // 因为注册信息需要获取selector的锁，所以需要放在这里处理注册信息
                    int changes = _changes_con.size();
                    Object change = null;
                    while (changes-- > 0 && (change = _changes_con.poll()) != null) {
                        try {
                            if (change instanceof SocketChannel) {
                                SocketChannel sc = (SocketChannel) change;
                                String id = "[" + sc.socket().getRemoteSocketAddress() + "] ";
                                sc.register(selector, SelectionKey.OP_READ, id);
                                write(sc, "hello:" + id + ",please input something!\n");
                                System.out.println("a client connected!" + id);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    // 处理请求信息，扔进请求队列
                    for (SelectionKey key : keys) {
                        if (key.isReadable()) {
                            // 取消注册事件
                            key.interestOps(0);
                            _changes_req.add(key);
                        }
                    }
                    selector.selectedKeys().clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private void close(SocketChannel sc) {
        if (sc != null && sc.socket() != null) {
            try {
                sc.socket().close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (sc != null) {
            try {
                sc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private void write(SocketChannel sc, String str) {
        try {
            sc.write(ByteBuffer.wrap(str.getBytes(DEFAULT_CHARSET)));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 数组扩容
     * 
     * @param src byte[] 源数组数据
     * @param size int 扩容的增加量
     * @return byte[] 扩容后的数组
     */
    private byte[] grow(byte[] src, int size) {
        byte[] tmp = new byte[src.length + size];
        System.arraycopy(src, 0, tmp, 0, src.length);
        return tmp;
    }

    private String parseRequest(SocketChannel sc) throws IOException {
        ByteBuffer bbuffer = ByteBuffer.allocate(DEFAULT_BUFFERSIZE);
        int count = 0;
        int off = 0;
        byte[] data = new byte[DEFAULT_BUFFERSIZE * 10];
        bbuffer.clear();
        // 循环一次性吧所有数据读完，否则可能buffer满了，数据未读完
        while ((count = sc.read(bbuffer)) > 0) {
            bbuffer.flip();
            if ((off + count) > data.length) {
                data = grow(data, DEFAULT_BUFFERSIZE * 10);
            }
            byte[] buf = bbuffer.array();
            System.arraycopy(buf, 0, data, off, count);
            off += count;
        }

        if (count < 0) {
            return null;
        }

        byte[] req = new byte[off];
        System.arraycopy(data, 0, req, 0, off);
        return new String(req, DEFAULT_CHARSET).trim();

    }

    private void interestKey(SelectionKey key, int OP) {
        key.interestOps(OP);
        selector.wakeup();
    }

    private boolean needToCanncel(String request) {
        return EXIT.equals(request);
    }

    /**
     * @author daizuan 处理请求
     */
    class RequestExecutor implements Runnable {

        public void run() {
            System.out.println("RequestExecutor:Request Executor start......");
            while (true) {
                int changes = _changes_req.size();
                Object change = null;
                while (changes-- > 0 && (change = _changes_req.poll()) != null) {
                    try {
                        if (change instanceof SelectionKey) {
                            SelectionKey key = (SelectionKey) change;
                            SocketChannel sc = (SocketChannel) key.channel();
                            // 解析出请求
                            String request = parseRequest(sc);
                            // 客户端退出
                            if (request == null) {
                                close(sc);
                                continue;
                            }
                            String id = (String) key.attachment();
                            System.out.println("read [" + request + "] from " + id);
                            if (needToCanncel(request)) {
                                System.out.println(id + "I am die!");
                                close(sc);
                                continue;
                            }
                            // 向客户端写一些信息
                            write(sc, "[" + getTime() + "] " + request + "\n");
                            // 重新设置key，需要获得锁。所以从阻塞唤醒selector
                            interestKey(key, SelectionKey.OP_READ);
                            // 如果不想继续，再这里关掉吧
                            // sc.socket().close();
                            // sc.close();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }

    public static void main(String[] args) throws IOException {
        // System.out.println("server start.........");
        SimpleJettyServer server = new SimpleJettyServer(6789);
        server.listen(); // 服务器开始监听端口，提供服务
    }

}




