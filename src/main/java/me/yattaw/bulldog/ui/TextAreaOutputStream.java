package me.yattaw.bulldog.ui;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {

    private final JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char) b)); // Append single character
        textArea.setCaretPosition(textArea.getDocument().getLength()); // Auto-scroll
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        textArea.append(new String(b, off, len)); // Append string
        textArea.setCaretPosition(textArea.getDocument().getLength()); // Auto-scroll
    }

}