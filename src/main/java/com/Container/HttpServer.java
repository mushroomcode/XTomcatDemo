package com.Container;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    // File.separator 用来进行分割 '/' , 原因是 win 和 linux 中会存在不同类的分割符号,
    // 为了避免切换系统后找不到文件路径导致无法使用, 建议使用 File.separator 代替 '/'
    public static final String WEB_ROOT = System.getProperty("user.dir") +
            File.separator + "/src/main/webroot";

    // 关闭的命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // 是否关闭了 Server
    private boolean shutdown = false;

    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1,
                    InetAddress.getByName("127.0.0.1"));
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutdown) {
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                // 将 input 封装到 request, 并且处理请求
                Request request = new Request(inputStream);
                request.parse();

                // 封装 output 到 response
                Response response = new Response(outputStream);
                response.setRequest(request);
                response.sendStaticResource();

                // 关闭 socket
                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        System.out.println(WEB_ROOT);
        server.await();
    }

}
