package co.bwsc.fallingwordsfx.client;

import java.io.*;
import java.util.ArrayList;

/**
 * DictionaryManager is a singleton class that read words from the dictionary file
 * and put them into an ArrayList of String called dictionary.
 *
 * @author Benjapol Worakan
 * @version 2015.11.12
 */
public class DictionaryManager {
    /**
     * A singleton instance of dictionary
     */
    private static DictionaryManager INSTANCE = new DictionaryManager();

    /**
     * A whole dictionary stored in an ArrayList of String.
     */
    private ArrayList<String> dictionary;

    /**
     * Current size of the dictionary.
     */
    private int size;

    /**
     * Constructor for DictionaryManager, initialize variables and read words from
     * dictionary file.
     */
    private DictionaryManager() {
        dictionary = new ArrayList<>();
        size = 0;
        readDictionaryFile();
    }

    /**
     * Get the singleton instance of the DictionaryManager.
     *
     * @return singleton instance of the DictionaryManager
     */
    public static DictionaryManager getInstance() {
        return INSTANCE;
    }

    /**
     * Get the size of the dictionary.
     *
     * @return size of the dictionary.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the dictionary ArrayList.
     *
     * @return dictionary ArrayList.
     */
    public ArrayList<String> getDictionary() {
        return (ArrayList<String>) dictionary.clone();
    }

    /**
     * Read words from the dictionary file into the dictionary ArrayList.
     */
    private void readDictionaryFile() {
        System.out.println("Reading dictionary from file...");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(ConfigManager.CFG.getDictionaryFile())));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found!");
            e.printStackTrace();
        }

        try {
            String line = br.readLine();
            while (line != null) {
                dictionary.add(line);
                size++;
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
