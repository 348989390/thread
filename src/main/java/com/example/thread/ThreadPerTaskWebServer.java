package com.example.thread;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class ThreadPerTaskWebServer {

    /**
     * 线程池
     */
    private final ExecutorService pool;

    /**
     * 声明Socket对象
     */
    private Socket socket = null;

    private ServerSocket serverSocket;

    public ThreadPerTaskWebServer(ExecutorService pool) {
        this.pool = pool;
    }

    public ThreadPerTaskWebServer(int port, int poolSize) {
        // 初始化线程池
        pool = Executors.newFixedThreadPool(poolSize);
        try {
            serverSocket = new ServerSocket(8080);
            if (log.isInfoEnabled()) {
                log.info("银企对账电子服务器已启动!主机地址是:" + InetAddress.getLocalHost()
                        + ",端口号:" + serverSocket.getLocalPort());
            }
            // 启动主线程
            new Thread(new MainThread()).start();
        } catch (Exception e) {
            log.error("服务器启动失败!", e);
            System.exit(1);
        }
    }

    /**
     * 主线程，监视课户端的连接请求
     *
     * @author fsg
     */
    class MainThread implements Runnable {

        public void run() {
            while (true) {
                try {
                    socket = serverSocket.accept();
                    socket.setSoTimeout(20);
                    pool.execute(new TraceClient(socket));
                } catch (Throwable e) {
                    log.error("服务器意外关闭!请重新启动服务器!", e);
                }
            }
        }
    }
}


class TraceClient implements Runnable {

    /**
     * Socket连接对象
     */
    private byte[] b;

    /**
     * Socket连接对象
     */
    private Socket socket;

    /**
     * 构造方法.
     *
     * @param socket
     */
    public TraceClient(Socket socket) {
        this.socket = socket;
    }

    public void run() {
//        com.xwj.outside.esbbank.socket.EbankDistribute ed = new com.xwj.outside.esbbank.socket.EbankDistribute();
//        ed.dispose(socket);
    }
}


