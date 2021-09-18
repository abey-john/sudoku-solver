package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	

	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. You’ll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Don’t change
	// “this” grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell = -1;
		int yOfNextEmptyCell = -1;

		// Checks if the grid is already full
		if (this.isFull())
		{
			return null;
		}
		
		// Finds the x and y of the next empty cell
		boolean foundEmpty = false;
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (values[i][j] == 0 && !foundEmpty)
				{
					xOfNextEmptyCell = j;
					yOfNextEmptyCell = i;
					foundEmpty = true;
				}
			}
	
		}
		
		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();
		int[][] copy = values;

		// Creates 9 new grids as described in the comments above. Adds them to grids.
		for (int gridNumber = 1; gridNumber <= 9; gridNumber++)
		{
			String[] gridValues = new String[9];
			for (int i = 0; i < 9; i++)
			{
				gridValues[i] = "";
			}
			for (int i = 0; i < yOfNextEmptyCell; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					gridValues[i] += copy[i][j];
				}
			}
			for (int i = 0; i < xOfNextEmptyCell; i++)
			{
				gridValues[yOfNextEmptyCell] += copy[yOfNextEmptyCell][i];
			}
			
			gridValues[yOfNextEmptyCell] += gridNumber;
			
			for (int i = xOfNextEmptyCell + 1; i < 9; i++)
			{
				gridValues[yOfNextEmptyCell] += copy[yOfNextEmptyCell][i];
			}
			for (int i = yOfNextEmptyCell + 1; i < 9; i++)
			{
				for (int j = 0; j < 9; j++)
				{
					gridValues[i] += copy[i][j];
				}
			}
			
			grids.add(new Grid(gridValues));
			
		}
		return grids;
	}
	
	
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{	
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		
		// Checks every row. Returns false if there is an illegal row.
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				for (int k = j + 1; k < 9; k++)
				{
					if (values[i][j] == values[i][k] && numbers.contains(values[i][j]))
					{
						return false;
					}
				}
			}
		}

		// Checks every column. Returns false if there is an illegal column.
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				for (int k = j + 1; k < 9; k++)
				{
					if (values[j][i] == values[k][i] && numbers.contains(values[j][i]))
					{
						return false; 
					}
				}
			}
		}
		
		// Checks every block. Returns false if there is an illegal block.
		for (int vertNumber = 0; vertNumber < 3; vertNumber++)
		{
			// Separates the grid into 9 3x3 blocks
			int vert = vertNumber * 3;
			for (int horizNumber = 0; horizNumber < 3; horizNumber++)
			{
				int horiz = horizNumber * 3 ; 
				for (int i = horiz; i < 3 + horiz; i++)
				{
					// Iterates through each block twice and compares each value to every other value
					for (int j = vert; j < 3 + vert; j++)
					{
						for (int k = horiz; k < 3 + horiz; k++)
						{
							for (int h = vert; h < 3 + vert; h++)
							{
								if (values[i][j] == values[k][h] && numbers.contains(values[i][j])
										&& j != h && i != k)
								{
									return false;
								}
							}
						}
					}
				}
			}
		}

		// All rows/columns/blocks are legal.
		return true;
	}

	
	//
	// COMPLETE THIS
	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (values[i][j] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	

	//
	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		Grid that = (Grid)x;
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				if (this.values[i][j] != that.values[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
}
