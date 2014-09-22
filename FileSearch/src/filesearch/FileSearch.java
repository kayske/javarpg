
package filesearch;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;

public class FileSearch {
    public static void main(String[] args) {
        FileReader in = null;
        LineNumberReader lnr = null;

        try {
            in = new FileReader("C:\\Users\\cl3\\Documents\\池辺\\java\\誰でも使えるJava\\test.txt");
            lnr = new LineNumberReader(in);
            String line;
            while ((line = lnr.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                lnr.close();
            } catch (Exception e) {
            }
        }  
    }
    
}
