package ch.heigvd.ios.text;

import java.io.FileReader;
import java.io.IOException;

/**
 * A class that reads text files. This implementation reads the file byte per byte. It manages the
 * file reader properly with a try-catch-finally block.
 */
public class TextFileReader {

    public void read(String filename) {
        try{
            FileReader reader = new FileReader(filename);

            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char)c);
            }
            reader.close();
        }catch(IOException e){
            System.out.println("Erreur de lecture" + e.getMessage());
        }
    }
}
