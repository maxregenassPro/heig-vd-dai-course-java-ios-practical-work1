package ch.heigvd.ios.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that reads text files. This implementation reads the file byte per byte. It manages the
 * file reader properly with a try-catch-finally block.
 */
public class TextFileReader {

    public List<String> read(String filename) {
        try{
            FileReader reader = new FileReader(filename);
            BufferedReader br = new BufferedReader(reader);

            List<String> list = new ArrayList<>();
            String line = null;

            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();

            return list;
        }catch(IOException e){
            System.out.println("Erreur de lecture" + e.getMessage());
            return null;
        }
    }
}
