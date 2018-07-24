package ticlab1;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
public class MesSource {
  BufferedReader br = null;
  String text;
    MesSource(File f)
    {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"Cp1251"));
            text = br.readLine();
        } catch (IOException ex) {
        System.out.println(ex);
        } 
  }
    public String getText() {
        return text;
    }
}
