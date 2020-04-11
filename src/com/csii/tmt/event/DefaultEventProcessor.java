package com.csii.tmt.event;

import com.csii.tmt.net.SocketClient;
import com.csii.tmt.utils.AssertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static com.csii.tmt.utils.I18nUtils.getMessage;

public class DefaultEventProcessor extends EventProcessor {

    private Log log = LogFactory.getLog(this.getClass());

    public void procEvent() {
        log.info("=======开始事件处理!========");
        openBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                File file = new File("./msg");
                fileChooser.setCurrentDirectory(file);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    log.info("filePath=>" + filePath);
                    msgLocationArea.setText(filePath);
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
                        StringBuffer buffer = new StringBuffer();
                        String tmpStr;
                        while ((tmpStr = reader.readLine()) != null) {
                            String str = tmpStr.trim();
                            // FIXME
                            buffer.append(str);
                            buffer.append("\r\n");
                        }
                        log.info(buffer);
                        sendMsgContentArea.setText(buffer.toString());
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }

            }
        });

        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                final String msgContent = sendMsgContentArea.getText();
                if (AssertUtils.isEmpty(msgContent, getMessage("message.assert.sendmsg.mustnot.empty"))) {
                    return;
                }
                final String port = portField.getText();
                final String ip = ipField.getText();
                if (AssertUtils.isEmpty(port, getMessage("message.assert.port.mustnot.empty"))) {
                    return;
                }
                if (AssertUtils.isEmpty(ip, getMessage("message.assert.ip.mustnot.empty"))) {
                    return;
                }
                try {

                    // new SockClient(ip, Integer.parseInt(port), msgContent);
                    new Thread(
                            new Runnable() {
                                @Override
                                public void run() {

                                    SocketClient sc = new SocketClient(ip, Integer.parseInt(port), 60);

                                    try {
                                        sc.makeConnection();
                                        String reciev = sc.sendDocument(msgContent);
                                        receiveMsgContentArea.setText(reciev);
                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent actionevent) {
                System.exit(0);
            }
        });
    }

}
