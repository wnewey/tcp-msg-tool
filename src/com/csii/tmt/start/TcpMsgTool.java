package com.csii.tmt.start;

import com.csii.tmt.view.MainFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TcpMsgTool {

    private static Log log = LogFactory.getLog(TcpMsgTool.class);

    public static void main(String[] args) {
        // System.out.println(System.getProperty("file.encoding"));
        MainFrame frame = new MainFrame();
        frame.showWindow();
        log.info("=======应用启动成功！=======");
    }

}
