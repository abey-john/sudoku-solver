package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
		
	//
	// Standard backtracking recursive solver.
	//
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			solutions.add(grid);
		}
		else
		{
			// Here if eval == Evaluation.CONTINUE. Generate all 9 possible next grids. Recursively 
			// call solveRecurse() on those grids.
			if (eval == Evaluation.CONTINUE)
			{
				ArrayList<Grid> grids = grid.next9Grids();
				for (Grid g : grids)
				{
					solveRecurse(g);
				}
			}
		}
	}
	
	//
	// Returns Evaluation.ABANDON if the grid is illegal. 
	// Returns ACCEPT if the grid is legal and complete.
	// Returns CONTINUE if the grid is legal and incomplete.
	//
	public Evaluation evaluate(Grid grid)
	{
		if (grid.isFull() && grid.isLegal()) 
		{
			return Evaluation.ACCEPT;
		} 
		else if (grid.isLegal()) 
		{
			return Evaluation.CONTINUE;
		} 
		else 
		{
			return Evaluation.ABANDON;
		}
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.createGrid();		// or any other puzzle
		Solver solver = new Solver(g);
		System.out.println("Will solve\n" + g);
		solver.solve();
		
		
		
		// Prints out the solution, or test if it equals() the solution in TestGridSupplier.
		if (solver.getSolutions().size() != 0)
		{
			Grid solution = solver.getSolutions().get(0);
			System.out.println("The solution is: ");
			System.out.println(solution.toString());
		}
		else
		{
			System.out.println("This is an unsolvable puzzle.");
		}
		
	}
}
