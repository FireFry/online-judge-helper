package com.firefrydev.ojh.helper.view;

import javax.swing.*;

public class JFrameView implements View {
    private final JFrame frame;

    public JFrameView(JFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void hide() {
        frame.setVisible(false);
    }
}
