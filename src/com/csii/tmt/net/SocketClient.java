/**
 * @(#)TopBpasOnline
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2012 Huateng Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai HUATENG Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with Huateng.
 * =============================================================================
 *
 */
package com.csii.tmt.net;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * <p><strong>Description: </strong>socket通信工具类</p>
 * @Company 上海华腾软件系统有限公司
 * @author  <a href="mailto: lu_liaoji@huateng.com">lionking</a>
 * @version v1.0
 * @Copyright Copyright 2013, Shanghai Huateng Software Systems Co., Ltd.
 * All right reserved.
 */
public class SocketClient {
    Socket soc = null;
    BufferedOutputStream output = null;
    InputStream input = null;
    private String socketadd;
    private int socketport = 0;
    private int timeLen = 3000;
    private static final int BUF_SIZE = 4096;
    private static final String REQ_ENCODE = "UTF-8"; // 请求报文编码格式
    private static final String RES_ENCODE = "UTF-8"; // 返回报文编码格式
    private static final String IPP_REQ_ENCODE = "GB18030"; // IPP请求报文编码格式
    private static final String IPP_RES_ENCODE = "GB18030"; // IPP返回报文编码格式

    public SocketClient() {

    }

    public SocketClient(String socketadd, int socketport, int timeOutLen) {
        this.setSocketadd(socketadd);
        this.setSocketport(socketport);
        this.setTimeLen(timeOutLen * 1000);
    }

    /**
     * <p><strong>Description: </strong>不带参数建立socket连接</p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @return
     * @throws IOException
     * @throws Exception
     * @update 2013-9-17
     */
    public int makeConnection() throws UnknownHostException, SocketTimeoutException, IOException, Exception {
        int connStatus = 0;
        try {
            soc = new Socket(this.getSocketadd(), this.getSocketport());
            soc.setSoTimeout(this.getTimeLen()); // 超时设置。
            output = new BufferedOutputStream(soc.getOutputStream());
            input = soc.getInputStream();
        } catch (UnknownHostException e) {
            connStatus = -3;
            System.out.println("未知主机连接异常:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        } catch (SocketTimeoutException e) {
            connStatus = -1;
            System.out.println("通信连接超时:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        } catch (IOException e) {
            connStatus = -2;
            System.out.println("连接异常:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        } catch (Exception e) {
            connStatus = -4;
            System.out.println("连接异常:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        }
        System.out.println("connStatus=" + connStatus);
        return connStatus;
    }

    /**
     * <p><strong>Description: </strong>带参数建立socket连接</p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @param socketadd 连接地址
     * @param socketport 连接端口
     * @return connStatus-0连接正常
     * @throws IOException
     * @throws Exception
     * @update 2013-9-17
     */
    public int makeConnection(String socketadd, int socketport)
            throws IOException, Exception {
        int connStatus = 0;
        try {
            soc = new Socket(socketadd, socketport);
            soc.setSoTimeout(this.getTimeLen()); // 超时设置。
            output = new BufferedOutputStream(soc.getOutputStream());
            input = soc.getInputStream();
        } catch (UnknownHostException e) {
            connStatus = -3;
            System.out.println("连接异常:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        } catch (SocketTimeoutException e) {
            connStatus = -1;
            System.out.println("连接异常:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        } catch (IOException e) {
            connStatus = -2;
            System.out.println("连接异常:" + e.fillInStackTrace());
            this.stopClient();
            throw e;
        }
        System.out.println("connStatus=" + connStatus);
        return connStatus;
    }

    /**
     * <p><strong>Description: </strong>发送报文</p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @param sendMsg 发送内容
     * @return recevMsg 接收内容
     * @throws Exception
     * @update 2013-9-17
     */
    public String sendMessage(String sendMsg) throws Exception {
        String recevMsg = null;
        try {
            System.out.println("往Socket发送报文:\n" + sendMsg);
            output.write(sendMsg.getBytes(SocketClient.REQ_ENCODE));
            output.flush();
            // 读取返回的报文
            byte[] resBytes = new byte[SocketClient.BUF_SIZE];
            input.read(resBytes);
            byte[] headLen = new byte[4]; // 报文头长度
            System.arraycopy(resBytes, 0, headLen, 0, 4); // 从resBytes中读取4位到headLen中
            String lenStr = new String(headLen).trim();
            int length = ("".equals(lenStr) ? 0 : Integer.parseInt(lenStr));
            System.out.println("应答报文长度:" + length);
            byte[] bodyBytes = new byte[length]; // 报文体的长度
            System.arraycopy(resBytes, 4, bodyBytes, 0, length);// 读取数据源中的报文体数据
            recevMsg = new String(bodyBytes, SocketClient.RES_ENCODE).trim(); // 构建返回的报文
            System.out.println("Socket返回报文:\n" + recevMsg);
        } catch (NumberFormatException e) {
            System.err.println(e);
        } catch (UnsupportedEncodingException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return recevMsg;
    }

    /**
     * <p><strong>Description: </strong>按照IPP格式向积分系统发送报文</p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @param doc 发送内容
     * @return receiveDoc 接收内容
     * @throws Exception
     * @update 2013-9-17
     */
    public String sendDocument(String doc) throws Exception {
        //		int len = doc.getBytes("UTF-8").length + 1;
        //		String sendMsg = StringUtil.formBlankString(6 - (len + "").length())
        //				+ len + doc;
        String sendMsg = doc;
        String receiveDoc = null;
        try {
            System.out.println("\n报文按如下格式已经发送，等待回应:\n" + sendMsg);
            output.write(sendMsg.getBytes(SocketClient.IPP_REQ_ENCODE));
            output.flush();
            // 读取前4位报文，表示报文的总长度
            byte[] headLen = new byte[6];
            for (int i = 0; i < 6; i++) {
                headLen[i] = (byte) input.read();
            }
            int bodyLen = Integer.valueOf(new String(headLen).trim())
                    .intValue();
            System.out.println("应答的报文长度:" + bodyLen);
            // 读取报文体的内容
            int contentLen = bodyLen - 6;
            byte[] body = new byte[contentLen];
            int maxLength = 200000;
            int totalLen = 0;
            for (int i = 0; i < contentLen && totalLen < maxLength; i++) {
                int len = input.read();
                body[i] = (byte) len;
                totalLen = totalLen + len;
            }

            String bodyStr = new String(body, SocketClient.REQ_ENCODE);
            System.out.println("应答返回的报文为:" + bodyStr);

            receiveDoc = bodyStr;
        } catch (Exception e) {
            throw e;
        } finally {
            this.stopClient();
        }
        return receiveDoc;
    }

    public static String formBlankString(int iCount) {
        String temp = "";
        for (int i = 0; i < iCount; i++) {

            temp = temp + "0";

        }
        return temp;

    }

    /**
     * <p><strong>Description: </strong>按照ECI格式向ECI发送报文 </p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @param doc
     * @return
     * @throws Exception
     * @update 2013-9-17
     */
    public String sendEciDocument(String doc) throws Exception {
        int len = doc.getBytes("UTF-8").length;
        String sendMsg = formBlankString(8 - (len + "").length())
                + len + doc;
        String receiveDoc = null;
        try {
            System.out.println("\n报文按如下格式已经发送，等待回应:\n" + sendMsg);
            output.write(sendMsg.getBytes(SocketClient.REQ_ENCODE));
            output.flush();
            // 读取前4位报文，表示报文的总长度
            byte[] headLen = new byte[8];
            for (int i = 0; i < 8; i++) {
                headLen[i] = (byte) input.read();
            }

            int bodyLen = Integer.valueOf(new String(headLen).trim())
                    .intValue();
            System.out.println("应答的报文长度:" + bodyLen);
            // 读取报文体的内容
            byte[] body = new byte[bodyLen];
            for (int i = 0; i < bodyLen; i++) {
                body[i] = (byte) input.read();
            }
            String[] bodyStr = new String(body, SocketClient.RES_ENCODE)
                    .split("</msg>");
            System.out.println("应答返回的报文为:" + bodyStr[0] + "</msg>");
            receiveDoc = bodyStr[0] + "</msg>";
        } catch (Exception e) {
            throw e;
        } finally {
            this.stopClient();
        }
        return receiveDoc;
    }

    /**
     * <p><strong>Description: </strong>合并两个字节数组</p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @param src 字节数组一
     * @param dst 字节数组二
     * @return
     * @update 2013-9-17
     */
    public static byte[] appendArray(byte[] src, byte[] dst) {

        byte[] newBytes = new byte[src.length + dst.length];

        System.arraycopy(src, 0, newBytes, 0, src.length);

        System.arraycopy(dst, 0, newBytes, src.length, dst.length);

        return newBytes;

    }

    /**
     * <p><strong>Description: </strong>关闭连接</p>
     * @author <a href="mailto: lu_liaoji@huateng.com">lionking</a>
     * @update 2013-9-17
     */
    public void stopClient() {
        try {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
            if (soc != null) {
                soc.close();
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            output = null;
            input = null;
            soc = null;
        }
    }

    /**
     * @return
     */
    public String getSocketadd() {
        return socketadd;
    }

    /**
     * @return
     */
    public int getSocketport() {
        return socketport;
    }

    /**
     * @param string
     */
    public void setSocketadd(String string) {
        socketadd = string;
    }

    /**
     * @param i
     */
    public void setSocketport(int i) {
        socketport = i;
    }

    /**
     * @return
     */
    public int getTimeLen() {
        return timeLen;
    }

    /**
     * @param i
     */
    public void setTimeLen(int i) {
        timeLen = i;
    }

}
