package se.liu.ida.davsc542kimla207.tddd78.project.zombiemania;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by david on 24/03/2015.
 */


public class MapExtractor
{
    private Scanner scanner = null;
    private ArrayList<String> map = new ArrayList<>();
    private File fileName;

    public MapExtractor(String fileName) {
	this.fileName = loadMapResource(fileName);
	openFile();
	readFile();
	closeFile();

    }
    public void closeFile() {
	scanner.close();
    }
    public void readFile() {
	while (scanner.hasNext()) {
	    map.add(scanner.next());
	}
	System.out.println("map added");

    }
    public static File loadMapResource(String path) {
	URL resourceUrl = GameComponent.class.getClassLoader().getResource(path);
	if (resourceUrl != null) {
	    return new File(resourceUrl.getPath());
	} else {
	    System.out.println("Could not load resource " + path);
	}

	return null;
    }
    public ArrayList<String> getMap() {
	return map;
    }
    public void openFile() {


	try {
	    this.scanner = new Scanner(fileName);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();


	}

    }
}
