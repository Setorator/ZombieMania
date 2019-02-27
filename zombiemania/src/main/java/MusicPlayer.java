package main.java;

import java.io.IOException;
import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;

/**
 * Created by David on 28/04/15.
 */


@SuppressWarnings("ALL")
public class MusicPlayer
{
    private Clip clip = null;
    private String path = null;

    public MusicPlayer() {
	this.path = "main/resources/Sounds/gameMusic.wav";
	load();
    }

    public void play() {
	clip.start();
    }
    public void load() {

	try {
	    AudioInputStream stream = ResourceLoader.loadAudioSteam(path);
	    final AudioFormat format = stream.getFormat();
	    final Info info = new Info(Clip.class, format);
	    clip = (Clip) AudioSystem.getLine(info);
	    clip.open(stream);
	} catch (IOException e) {
	    e.printStackTrace();
	    System.exit(1);
	} catch (Exception ignored) {
	    System.out.println("no file found");
	}

    }
    public void setPath(final String path) {
	this.path = path;
    }
}
