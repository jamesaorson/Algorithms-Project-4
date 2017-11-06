package algorithmsproject4;

import java.io.FileNotFoundException;

public class AlgorithmsProject4 {
    public static void main(String[] args) throws FileNotFoundException {
        String input = args[0];
        Solver rushHourSolver = new Solver(input);
        rushHourSolver.solve();
    }
}
