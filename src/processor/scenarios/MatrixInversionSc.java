package processor.scenarios;

import processor.MatrixUtils;

import static processor.MatrixUtils.printMatrix;
import static processor.MatrixUtils.readMatrixFromConsole;

public class MatrixInversionSc implements Scenario {
    @Override
    public void run() {
        var inversion = MatrixUtils.inversion(readMatrixFromConsole());
        System.out.println("The result of inversion is:");
        printMatrix(inversion);
    }
}
