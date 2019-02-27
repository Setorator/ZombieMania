package main.java;

import main.java.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by David on 05/05/15.
 */
public final class ResourceLoader
{
    private ResourceLoader() {}
    public static ArrayList<BlockType[][]> loadMaps() {
	int tmpCounter = 0;
	ArrayList<BlockType[][]> maps = new ArrayList<>();

	while (true) {
	    final String filename = "main/resources/Maps/map" + tmpCounter + ".txt";
	    try {
		final MapExtractor mapExtractor = new MapExtractor(filename);
		final MapConverter mapConverter = new MapConverter(mapExtractor.getMap());
		maps.add(mapConverter.getConvertedMap());
		System.out.println(filename + " added");
		tmpCounter++;
	    } catch (RuntimeException ignored) {
		System.out.println("nomap named dis");
		break;
	    }
	}
	System.out.println(maps.size());
	System.out.println(Arrays.deepToString(maps.get(0)));
	return maps;
    }
    public static AudioInputStream loadAudioSteam(String path) {
	try {
	    URL resourceUrl = MusicPlayer.class.getClassLoader().getResource(path);
	    return AudioSystem.getAudioInputStream(resourceUrl);

	} catch (IOException | UnsupportedAudioFileException e) {
	    e.printStackTrace();
	}

	return null;

    }
    public static ImageIcon loadImageResource(String path) {
	try {
	    URL resourceUrl = GameComponent.class.getClassLoader().getResource(path);
	    if (resourceUrl != null) {
		return new ImageIcon(resourceUrl);
	    } else {
		System.out.println("Could not load resource " + path);
	    }

	} catch (RuntimeException e) {
	    e.printStackTrace();
	    System.out.println("Couldn't Load resources system.exit");
	    System.exit(1);
	}

	return null;
    }
}
