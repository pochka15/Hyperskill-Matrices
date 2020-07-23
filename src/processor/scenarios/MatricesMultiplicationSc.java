package processor.scenarios;

import processor.Matrix;
import processor.MatrixUtils;

import static processor.MatrixUtils.printMatrix;
import static processor.MatrixUtils.readMatrixFromConsole;

public class MatricesMultiplicationSc implements Scenario {
    @Override
    public void run() {
        Matrix<Double> m1 = readMatrixFromConsole();
        Matrix<Double> m2 = readMatrixFromConsole();
        System.out.println("The multiplication result is:");
        printMatrix(MatrixUtils.multiplication(m1, m2));
    }
}
