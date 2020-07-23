package processor.scenarios;

import processor.Matrix;
import processor.MatrixUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static processor.MatrixUtils.printMatrix;
import static processor.MatrixUtils.readMatrixFromConsole;

public class MatrixMultiplicationToAConstantSc implements Scenario {
    @Override
    public void run() {
        try {
            Matrix<Double> matrix = readMatrixFromConsole();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the multiplication constant: > ");
            double constant;
            constant = Double.parseDouble(String.valueOf(br.readLine()));
            System.out.println("The multiplication result is:");
            printMatrix(MatrixUtils.multiplication(matrix, constant));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
