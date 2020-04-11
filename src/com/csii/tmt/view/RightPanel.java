package com.csii.tmt.view;

import com.csii.tmt.event.EventProcessor;

import javax.swing.*;
import javax.swing.plaf.metal.MetalBorders;

import static com.csii.tmt.utils.I18nUtils.getMessage;

public class RightPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel inputMsgLabel = new JLabel(getMessage("message.label.inputMsgLabel"));

    private JTextArea sendMsgContentArea = new JTextArea();
    private JScrollPane sendMsgScrollPanel = new JScrollPane(sendMsgContentArea);

    private JButton sendBtn = new JButton(getMessage("message.button.sendBtn"));

    private JTextArea receiveMsgContentArea = new JTextArea();
    private JScrollPane receiveMsgScrollPanel = new JScrollPane(receiveMsgContentArea);

    public RightPanel(EventProcessor eventProcessor) {

        this.setLayout(null);

        inputMsgLabel.setBounds(20, 0, 200, 30);
        this.add(inputMsgLabel);

        sendMsgScrollPanel.setBounds(20, 30, 600, 230);
        sendMsgScrollPanel.setBorder(MetalBorders.getTextFieldBorder());
        //msgContentArea.setLineWrap(true);
        this.add(sendMsgScrollPanel);

        sendBtn.setBounds(520, 260, 100, 30);
        this.add(sendBtn);

        receiveMsgScrollPanel.setBounds(20, 300, 600, 230);
        receiveMsgScrollPanel.setBorder(MetalBorders.getTextFieldBorder());
        this.add(receiveMsgScrollPanel);

        eventProcessor.setSendMsgContentArea(sendMsgContentArea);
        eventProcessor.setSendBtn(sendBtn);
        eventProcessor.setReceiveMsgContentArea(receiveMsgContentArea);
    }
}
