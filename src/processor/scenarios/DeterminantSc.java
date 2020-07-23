package processor.scenarios;

import processor.Matrix;
import processor.MatrixUtils;

import static processor.MatrixUtils.readMatrixFromConsole;

public class DeterminantSc implements Scenario {
    @Override
    public void run() {
        Matrix<Double> matrix = readMatrixFromConsole();
        System.out.println("The result is:\n" +
                               MatrixUtils.determinant(matrix)
                               + "\n");
    }
}
