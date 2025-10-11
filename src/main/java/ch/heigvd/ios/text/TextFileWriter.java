package ch.heigvd.ios.text;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * A class that writes text files. This implementation write the file byte per byte. It manages the
 * file writer properly with a try-catch-finally block.
 */
public class TextFileWriter {

    public void write(String filename, String taches) {
        try (FileWriter writer = new FileWriter(filename, StandardCharsets.UTF_8))
        {
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(taches);

            bw.flush();
            bw.close();
        }catch (IOException e){
            System.out.println("Erreur d'écriture" + e.getMessage());
        }
    }
}
