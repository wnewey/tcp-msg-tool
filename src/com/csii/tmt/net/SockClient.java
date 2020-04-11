package com.csii.tmt.net;

import com.csii.tmt.exception.UIException;
import com.csii.tmt.view.SendMsgFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.SocketFactory;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SockClient implements Runnable {

    private String ip;

    private int port;

    private String message;

    private int receiveBufferSize = 8192;

    private int sendBufferSize = 8192;

    private int soTimeout = 70000;

    private Log log = LogFactory.getLog(this.getClass());

    public SockClient(String ip, int port, String message) {
        this.ip = ip;
        this.port = port;
        this.message = message;
    }

    public void sendMessage() throws UIException {
        SocketFactory socketFactory = SocketFactory.getDefault();
        Socket socket;
        byte[] bytes = message.getBytes();
        byte[] bXMLResult = new byte[bytes.length + 8];
        String strLen = String.valueOf(bytes.length);
        System.arraycopy(strLen.getBytes(), 0, bXMLResult, 8 - strLen.getBytes().length, strLen.getBytes().length);
        System.arraycopy(bytes, 0, bXMLResult, 8, bytes.length);

        try {
            socket = socketFactory.createSocket(ip, port);
            socket.setReceiveBufferSize(receiveBufferSize);
            socket.setSendBufferSize(sendBufferSize);
            socket.setSoTimeout(soTimeout);
            socket.setKeepAlive(false);
            socket.setTcpNoDelay(false);
            socket.getOutputStream().write(bXMLResult);
            socket.getOutputStream().flush();

            //返回数据处理
            InputStream stream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "8859_1"));
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();

            Matcher matcher;
            String endTagName = "Message";
            Pattern pattern = Pattern.compile("</ *" + endTagName + " *>");
            do {
                String s = bufferedReader.readLine();
                if (s == null)
                    break;
                baos2.write(s.getBytes("8859_1"));
                baos2.write("\r\n".getBytes());
                matcher = pattern.matcher(s);
            } while (!matcher.find());
            log.info("Recieve from MServer:\r\n");
            log.info(baos2.toString());
            new SendMsgFrame(baos2.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new UIException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new UIException(e.getMessage());
        }
    }

    public void run() {
        try {
            sendMessage();
        } catch (UIException e) {
            e.printStackTrace();
        }
    }
}
