package nl.ru.ai.exercise6;

import nl.ru.ai.gameoflife.Cell;
import static nl.ru.ai.gameoflife.Universe.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameOfLife
{

  public static void main(String[] args)
  {
	  generateUniverse("glider.txt");

  }
  /**
   * Creates universe + its generations
   * @param filename
   */
  private static void generateUniverse(String string) {
	  assert string != "" : "String is empty";
	  Cell[][] cell = readUniverseFile(string);
	  while(true) {
		  showUniverse(cell);
		  sleep(50);
		  cell = nextGeneration(cell);
	  }
  }

  /**
   * Fills universe array with data from file
   * @param fileName
   * @return
   */
  static Cell[][] readUniverseFile(String fileName) {
	  assert fileName != "" : "String is empty";
	  Cell[][] cell = new Cell[40][60];
	  try {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		String row;
		int x = 0, y = 0;
		for (x = 0; x <= 39 && (row = reader.readLine()) != null; x++) {
			for (y = 0; y < row.length(); y++) {
				if (row.charAt(y) == '*') {
					cell[x][y] = Cell.LIVE;
					if ((x == 0 || x == 39) || (y == 0 || y == 59))
						throw new IllegalArgumentException ("Live cell in the border.");
				} else if (row.charAt(y) == '.') {
					cell[x][y] = Cell.DEAD;
				} else {
					throw new IllegalArgumentException ("Unrecognised character in file.");
				}
			}
		}
		reader.close();
		checkNumberOfRowsAndColumns(x, y);
		
	} catch (FileNotFoundException e) {
		System.out.println("File not found.");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("Something went wrong.");
		e.printStackTrace();
	}
	  return cell;
  }
  /**
   * Self explanatory. Used in readUniverseFile()
   * @param x
   * @param y
   */
  private static void checkNumberOfRowsAndColumns(int x, int y) {
	  assert true;
	  if (x != 40) {
			throw new IllegalArgumentException ("There aren't 40 rows");
		}
		if (y != 60) {
			throw new IllegalArgumentException ("There aren't 60 columns");
		}
  }
  /**
   * Updates the screen with array data
   * @param universe
   */
  private static void showUniverse(Cell[][] universe)
  {
	  for (int row = 0; row <= 39; row++) {
		  for (int col = 0; col <= 59; col++) {
			  updateScreen(row, col, universe[row][col]);
		  }
	  }
  }
  /**
   * Calculates the next generation from the current universe array and puts it in a new array
   * @param universe
   * @return next generation
   */
  private static Cell[][] nextGeneration(Cell[][] universe)
  {
	  Cell[][] nextUniverse = deadUniverse();
	  for (int row = 1; row <= 38; row++) {
		  for (int col = 1; col <= 58; col++) {
			  int aliveCounter = countNeighbors(row, col, universe);
			  if (universe[row][col] == Cell.LIVE) {
				  aliveCounter--;
				  if (aliveCounter == 2 || aliveCounter == 3)
					  nextUniverse[row][col] = Cell.LIVE;
			  } else {
				  if (aliveCounter == 3)
					  nextUniverse[row][col] = Cell.LIVE;
			  }
		  }
	  }
	  return nextUniverse;
  }

  /**
   * Fills a universe with dead cells
   * @return universe
   */
  private static Cell[][] deadUniverse() {
	  Cell[][] deadUniverse = new Cell[40][60];
	  for (int row = 0; row <= 39; row++) {
		  for (int col = 0; col <= 59; col++) {
			  deadUniverse[row][col] = Cell.DEAD;
		  }
	  }
	return deadUniverse;
}

  /**
   * Counts the amount of neighbors around a cell (including the cell <-- MUST FIX)
   * @param row
   * @param col
   * @param universe
   * @return aliveCounter
   */
private static int countNeighbors(int row, int col, Cell[][] universe) {
	int aliveCounter = 0;
	for (int x = -1; x <= 1; x++)
		  for (int y = -1; y <= 1; y++)
			  if (universe[row + x][col + y] == Cell.LIVE)
				  aliveCounter++;
	return aliveCounter;
  }

}
