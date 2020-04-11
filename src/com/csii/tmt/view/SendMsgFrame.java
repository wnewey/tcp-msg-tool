package com.csii.tmt.view;

import static com.csii.tmt.utils.I18nUtils.getMessage;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SendMsgFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea = new JTextArea();

	private JScrollPane scrollPane = new JScrollPane(textArea);

	public SendMsgFrame(String resultMsg) {

		this.setResizable(false);
		this.setTitle(getMessage("message.window.title"));
		this.setBounds(new Rectangle(600, 800));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE );
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowevent) {
				SendMsgFrame.this.dispose();
			}
		});

		textArea.setText(resultMsg);
		this.add(scrollPane,BorderLayout.CENTER);

		this.setVisible(true);
	}
}
