package ch.heigvd.ios.text;

import java.io.*;

/**
 * A class that writes text files. This implementation write the file byte per byte. It manages the
 * file writer properly with a try-catch-finally block.
 */
public class TextFileWriter {

    public void write(String filename, String tache) {
        try (FileWriter writer = new FileWriter(filename, true))
        {
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("\n" + tache);

            bw.flush();
            bw.close();
        }catch (IOException e){
            System.out.println("Erreur d'écriture" + e.getMessage());
        }
    }
}
