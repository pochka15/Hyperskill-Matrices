package processor.scenarios;

import processor.Matrix;
import processor.MatrixUtils;

import static processor.MatrixUtils.readMatrixFromConsole;

public class AddMatricesSc implements Scenario {
    @Override
    public void run() {
        Matrix<Double> m1 = readMatrixFromConsole();
        Matrix<Double> m2 = readMatrixFromConsole();
        System.out.println("The sum result is:");
        MatrixUtils.printMatrix(MatrixUtils.sum(m1, m2));
    }
}
