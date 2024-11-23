import java.util.HashSet;
import java.util.Set;

class Sudoku {

    // ANSI color codes for terminal output
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static Set<Integer> set = new HashSet<>();

    /**
     * Checks if placing a number in a specific cell is valid.
     * Validity depends on the row, column, and 3x3 grid constraints.
     */
    public static boolean issafe(int[][] grid, int row, int col, int num) {
        // Check if the number already exists in the row
        for (int x = 0; x <= 8; x++) {
            if (grid[row][x] == num) {
                return false;
            }
        }

        // Check if the number already exists in the column
        for (int x = 0; x <= 8; x++) {
            if (grid[x][col] == num) {
                return false;
            }
        }

        // Check if the number already exists in the 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Solves the Sudoku puzzle using backtracking.
     * Displays the solving process step-by-step with a delay.
     */
    public static boolean solveSudoku(int[][] grid, int row, int col) throws InterruptedException {
        int N = 9; // Dimension of the Sudoku grid

        // If we have reached the end of the grid, the puzzle is solved
        if (row == N - 1 && col == N) {
            return true;
        }

        // Move to the next row if we are at the last column
        if (col == N) {
            row++;
            col = 0;
        }

        // Skip already filled cells
        if (grid[row][col] != 0) {
            return solveSudoku(grid, row, col + 1);
        }

        // Try placing numbers 1-9 in the cell
        for (int i = 1; i <= 9; i++) {
            if (issafe(grid, row, col, i)) {
                // Place the number
                grid[row][col] = i;
                print(grid);
                Thread.sleep(500);

                // Recur to solve the rest of the grid
                if (solveSudoku(grid, row, col + 1)) {
                    return true;
                }

                // Undo the placement (backtrack)
                grid[row][col] = 0;
                print(grid);
                Thread.sleep(500);
            }
        }

        return false; // Trigger backtracking
    }

    /**
     * Prints a horizontal border for the Sudoku grid.
     */
    public static void prindBordar() {
        System.out.println("-------------------------------------");
    }

    /**
     * Clears the terminal screen.
     */
    public static void clearScreen() {
        System.out.println("\033[H");
        System.out.println("\033[2J");
    }

    /**
     * Prints the Sudoku grid with color-coded values:
     * - Original numbers in red
     * - Newly added numbers in green
     * - Empty cells as blue dashes
     */
    public static void print(int[][] grid) {
        int len = grid.length;
        clearScreen();
        prindBordar();
        for (int i = 0; i < len; i++) {
            System.out.print(": ");
            for (int j = 0; j < len; j++) {
                if (grid[i][j] == 0) {
                    System.out.print(BLUE + "-" + RESET);
                } else {
                    int hash = i + j * 100; // Unique identifier for each cell
                    if (set.contains(hash)) {
                        System.out.print(RED + grid[i][j] + RESET); // Original value
                    } else {
                        System.out.print(GREEN + grid[i][j] + RESET); // New value
                    }
                }

                // Print grid separators
                if (j == 2 || j == 5) {
                    System.out.print(" | ");
                } else {
                    System.out.print(" : ");
                }
            }
            System.out.println();

            // Print horizontal borders after 3 rows
            if (i == 2 || i == 5) {
                prindBordar();
            }
        }
        prindBordar();
    }

    /**
     * Adds unique identifiers (hashes) for all empty cells to a set.
     */
    public static void addHashs(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    int hash = i + j * 100;
                    set.add(hash);
                }
            }
        }
    }

    /**
     * Main function to initialize the grid and solve the Sudoku puzzle.
     */
    public static void main(String[] args) throws InterruptedException {
        int[][] grid = {
            {3, 0, 6, 5, 0, 8, 4, 0, 0},
            {5, 2, 0, 0, 0, 0, 0, 0, 0},
            {0, 8, 7, 0, 0, 0, 0, 3, 1},
            {0, 0, 3, 0, 1, 0, 0, 8, 0},
            {9, 0, 0, 8, 6, 3, 0, 0, 5},
            {0, 5, 0, 0, 9, 0, 6, 0, 0},
            {1, 3, 0, 0, 0, 0, 2, 5, 0},
            {0, 0, 0, 0, 0, 0, 0, 7, 4},
            {0, 0, 5, 2, 0, 6, 3, 0, 0}
        };

        addHashs(grid);
        if (solveSudoku(grid, 0, 0)) {
            System.out.println("solved");
            print(grid);
        } else {
            System.out.println("not solved");
        }
    }
}
