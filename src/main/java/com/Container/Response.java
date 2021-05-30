package com.Container;

import java.io.*;

/**
 * 模拟 Response
 */
public class Response {

    private static final int BUFFER_SIZE = 2048;
    private Request request;
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 设置返回的报文
     * @throws IOException
     */
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream inputStream = null;
        try {
//            System.out.println("response uri : " + request.getUri());
            File file = new File(HttpServer.WEB_ROOT, request.getUri());
//            System.out.println(file.getPath());
//            StringBuffer stringBuffer = new StringBuffer();
            if (file.exists()) {
                StringBuffer successResponse = new StringBuffer(
                        "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length:18 \r\n" +
                        "\r\n" +
                        "File Not Found web");
                outputStream.write(successResponse.toString().getBytes());
            }
            else {
                String errorMsg = "HTTP/1.1 404 File is Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                outputStream.write(errorMsg.getBytes());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(inputStream!=null) {
                inputStream.close();
            }
        }
    }


}
