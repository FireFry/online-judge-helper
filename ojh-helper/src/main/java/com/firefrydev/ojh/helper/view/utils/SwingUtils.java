package com.firefrydev.ojh.helper.view.utils;

import javax.swing.*;

public class SwingUtils {

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
    }

    private SwingUtils() {}

}
