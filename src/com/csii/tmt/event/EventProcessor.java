package com.csii.tmt.event;

import javax.swing.*;

public abstract class EventProcessor {

    protected JButton sendBtn;

    protected JButton openBtn;

    protected JTextArea sendMsgContentArea;

    protected JTextArea receiveMsgContentArea;

    protected JTextField ipField;

    protected JTextField portField;

    protected JTextField charsetField;

    protected JTextArea msgLocationArea;

    protected JMenuItem exitItem;

    protected JFileChooser fileChooser = new JFileChooser();

    public void setSendBtn(JButton sendBtn) {
        this.sendBtn = sendBtn;
    }

    public void setOpenBtn(JButton openBtn) {
        this.openBtn = openBtn;
    }

    public void setSendMsgContentArea(JTextArea sendMsgContentArea) {
        this.sendMsgContentArea = sendMsgContentArea;
    }

    public void setReceiveMsgContentArea(JTextArea receiveMsgContentArea) {
        this.receiveMsgContentArea = receiveMsgContentArea;
    }

    public void setIpField(JTextField ipField) {
        this.ipField = ipField;
    }

    public void setPortField(JTextField portField) {
        this.portField = portField;
    }

    public void setCharsetField(JTextField charsetField) {
        this.charsetField = charsetField;
    }

    public void setMsgLocationArea(JTextArea msgLocationArea) {
        this.msgLocationArea = msgLocationArea;
    }

    public void setExitItem(JMenuItem exitItem) {
        this.exitItem = exitItem;
    }

    public abstract void procEvent();
}
