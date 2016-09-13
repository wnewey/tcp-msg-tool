package com.csii.tmt.exception;

import javax.swing.JOptionPane;

public class UIException extends Exception{
	
	public UIException() { }
	
	public UIException(String msg)
    {
        super(msg);
        JOptionPane.showMessageDialog(null, msg,"Error", JOptionPane.ERROR_MESSAGE);
    }
}
