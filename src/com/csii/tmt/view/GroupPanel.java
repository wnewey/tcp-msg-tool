package com.csii.tmt.view;

import com.csii.tmt.domain.AppConfig;

import javax.swing.*;
import java.awt.Color;

import static com.csii.tmt.utils.I18nUtils.getMessage;

public class GroupPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel ipLabel = new JLabel(getMessage("message.label.ipLabel"));

    private JTextField ipField = new JTextField();

    private JLabel portLabel = new JLabel(getMessage("message.label.portLabel"));

    private JTextField portField = new JTextField();

    private JLabel charsetLabel = new JLabel(getMessage("message.label.charsetLabel"));

    private JTextField charsetField = new JTextField();

    private JTextArea msgLocationArea = new JTextArea();

    private JButton openBtn = new JButton(getMessage("message.button.openBtn"));

    public JButton getOpenBtn() {
        return openBtn;
    }

    public JTextField getIpField() {
        return ipField;
    }

    public JTextField getPortField() {
        return portField;
    }

    public JTextField getCharsetField() {
        return charsetField;
    }

    public JTextArea getMsgLocationArea() {
        return msgLocationArea;
    }

    public GroupPanel() {

        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setLayout(null);

        /**IP地址输入项**/
        ipLabel.setBounds(20, 0, 200, 30);
        this.add(ipLabel);

        ipField.setBounds(20, 30, 200, 30);
        ipField.setText(AppConfig.getInstance().getDefaultIp());
        this.add(ipField);

        /**端口输入项**/
        portLabel.setBounds(20, 60, 200, 30);
        this.add(portLabel);

        portField.setBounds(20, 90, 200, 30);
        portField.setText(AppConfig.getInstance().getDefaultPort());
        this.add(portField);

        /**编码输入框**/
        charsetLabel.setBounds(20, 120, 200, 30);
        this.add(charsetLabel);

        charsetField.setBounds(20, 150, 200, 30);
        charsetField.setText(AppConfig.getInstance().getDefaultCharset());
        this.add(charsetField);

        openBtn.setBounds(20, 200, 200, 30);
        this.add(openBtn);

        msgLocationArea.setBounds(20, 230, 200, 60);
        msgLocationArea.setEditable(false);
        msgLocationArea.setLineWrap(true);
        this.add(msgLocationArea);
        //
        // sendBtn.setBounds(20, 290, 200, 30);
        // this.add(sendBtn);
        this.setVisible(true);
    }
}
