package com.csii.tmt.view;

import com.csii.tmt.event.EventProcessor;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static com.csii.tmt.utils.I18nUtils.getMessage;

public class LeftPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel tipInfoLabel = new JLabel(getMessage("message.label.tipInfoLabel"));

    private GroupPanel groupPanel = new GroupPanel();

    public LeftPanel(EventProcessor eventProcessor) {
        this.setLayout(null);
        tipInfoLabel.setBounds(30, 10, 100, 30);
        this.add(tipInfoLabel);

        groupPanel.setBounds(30, 40, 250, 450);
        this.add(groupPanel);

        eventProcessor.setIpField(groupPanel.getIpField());
        eventProcessor.setOpenBtn(groupPanel.getOpenBtn());
        eventProcessor.setPortField(groupPanel.getPortField());
        eventProcessor.setCharsetField(groupPanel.getCharsetField());
        eventProcessor.setMsgLocationArea(groupPanel.getMsgLocationArea());
    }

}
