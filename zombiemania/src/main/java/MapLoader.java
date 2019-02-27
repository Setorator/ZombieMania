package main.java;

import java.util.ArrayList;

/**
 * Created by David on 28/04/15.
 */

public class MapLoader
{
    private ArrayList<BlockType[][]> mapArray;
    public MapLoader() {
	this.mapArray = ResourceLoader.loadMaps();


    }
    public ArrayList<BlockType[][]> getMapArray() {
	return mapArray;
    }
}

