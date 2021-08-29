package com.company.ticlab1;

import java.io.*;

public class MessageSource {
    private String text;

    private static final String CHARSET_NAME = "Cp1251";

    MessageSource(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), CHARSET_NAME))) {
            text = br.readLine();
        } catch (IOException ex) {
            System.out.println("Error reading error source:" + ex);
        }
    }

    public String getText() {
        return text;
    }
}
