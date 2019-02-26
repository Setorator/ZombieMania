package se.liu.ida.davsc542kimla207.tddd78.project.zombiemania;

import java.util.List;


/**
 * Created by david on 24/03/2015.
 */


// this class converts our map from a single array of strings to a 2D-array of BlockTypes.
public class MapConverter
{
    private Iterable<String> map;
    private BlockType[][] convertedMap;


    public MapConverter(List<String> map) {
	this.map = map;
	this.convertedMap = new BlockType[map.size()][map.get(0).length()];
	this.setConvertedMap();

    }

    // getter
    public BlockType[][] getConvertedMap() {
	return convertedMap;
    }


    // this method first converts the array of strings to an array of chars and from there to an array of BlockTypes.
    public void setConvertedMap() {
	int mapCount = 0;
	for (String row : map) {
	    char[] charArray = row.toCharArray();
	    int charCounter = 0;
	    for (char character : charArray) {
		switch (character) {
		    case '#':
			convertedMap[mapCount][charCounter] = BlockType.BOX;
			break;
		    case 'p':
			convertedMap[mapCount][charCounter] = BlockType.PLAYER;
			break;
		    case 'g':
			convertedMap[mapCount][charCounter] = BlockType.GROUND;
			break;
		    case 'c':
			convertedMap[mapCount][charCounter] = BlockType.CREEP;
			break;
		    case 'm':
			convertedMap[mapCount][charCounter] = BlockType.MAN;
			break;
		    case 's':
			convertedMap[mapCount][charCounter] = BlockType.SPIKE;
			break;
		    case 'f':
			convertedMap[mapCount][charCounter] = BlockType.FINISH;
			break;
		    case 'h':
			convertedMap[mapCount][charCounter] = BlockType.HOUND;
			break;
		    case '*':
			convertedMap[mapCount][charCounter] = BlockType.INSTRUCTIONS;
			break;
		    case 'Y':
			convertedMap[mapCount][charCounter] = BlockType.STARTSIGN;
			break;
		    case 'd':
			convertedMap[mapCount][charCounter] = BlockType.DIRT;
			break;
		    default:
			convertedMap[mapCount][charCounter] = BlockType.EMPTY;
			break;
		}

		charCounter++;
	    }
	    mapCount++;
	}

    }


// --Commented out by Inspection START (04/05/15 20:19):
//    // is never used but is handy if you want to have the map printed out in the console
//    public void printMap() {
//	for (se.liu.ida.davsc542kimla207.tddd78.project.zombiemania.BlockType[] blockTypes : convertedMap) {
//	    String tmpString = "";
//	    for (se.liu.ida.davsc542kimla207.tddd78.project.zombiemania.BlockType blockType : blockTypes) {
//		switch (blockType) {
//		    case PLAYER:
//			tmpString += "P";
//			break;
//		    case EMPTY:
//			tmpString += "E";
//			break;
//		    case CREEP:
//			tmpString += "C";
//			break;
//		    case MAN:
//			tmpString += "M";
//			break;
//		    case HOUND:
//			tmpString += "H";
//			break;
//		    case GROUND:
//			tmpString += "G";
//			break;
//		    case BOX:
//			tmpString += "#";
//			break;
//		    case SPIKE:
//			tmpString += "^";
//			break;
//		    case FINISH:
//			tmpString += "F";
//			break;
//		    case STARTSIGN:
//			tmpString += "Y";
//			break;
//		    case INSTRUCTIONS:
//			tmpString += "*";
//			break;
//		    default:
//			tmpString += "ö";
//		}
//
//	    }
//	    System.out.println(tmpString);
//	}
//    }
// --Commented out by Inspection STOP (04/05/15 20:19)
}
