package processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixUtils {
    public static Matrix<Double>
    multiplication(Matrix<Double> matrix, Double constant) {
        List<List<Double>> rows = new ArrayList<>(matrix.rowsNumber());
        for (int i = 0; i < matrix.rowsNumber(); i++) {
            List<Double> row = new ArrayList<>(matrix.columnsNumber());
            for (int j = 0; j < matrix.columnsNumber(); j++) {
                row.add(matrix.rows().get(i).get(j) * constant);
            }
            rows.add(row);
        }
        return new RectangleMatrix<>(rows);
    }

    public static Matrix<Double>
    multiplication(Matrix<Double> matrix1, Matrix<Double> matrix2) {
        if (matrix1.columnsNumber() == matrix2.rowsNumber()) {
            List<List<Double>> rows = new ArrayList<>(matrix1.rowsNumber());
            for (int i = 0; i < matrix1.rowsNumber(); i++) {
                List<Double> outRow = new ArrayList<>(matrix2.columnsNumber());
                var row = matrix1.rows().get(i);
                for (int j = 0; j < matrix2.columnsNumber(); j++) {
                    var column = matrix2.columns().get(j);
                    Iterator<Double> iterator = column.iterator();
                    row.stream()
                        .map(val -> val * iterator.next())
                        .reduce(Double::sum).ifPresent(outRow::add);
                }
                rows.add(outRow);
            }
            return new RectangleMatrix<>(rows);
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    public static Matrix<Double> sum(Matrix<Double> matrix1, Matrix<Double> matrix2) {
        if (matrix1.rowsNumber() == matrix2.rowsNumber() &&
            matrix1.columnsNumber() == matrix2.columnsNumber()) {
            List<List<Double>> rows = new ArrayList<>(matrix1.rowsNumber());
            for (int i = 0; i < matrix1.rowsNumber(); i++) {
                List<Double> row = new ArrayList<>(matrix1.columnsNumber());
                for (int j = 0; j < matrix1.columnsNumber(); j++) {
                    row.add(matrix1.rows().get(i).get(j) + matrix2.rows().get(i).get(j));
                }
                rows.add(row);
            }
            return new RectangleMatrix<>(rows);
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    public static Matrix<Double> mainDiagonalTransposition(Matrix<Double> matrix) {
        if (matrix.rowsNumber() == matrix.columnsNumber()) {
            return new RectangleMatrix<>(matrix.columns());
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    public static Matrix<Double> sideDiagonalTransposition(Matrix<Double> matrix) {
        if (matrix.rowsNumber() == matrix.columnsNumber()) {
            List<List<Double>> outRows = new ArrayList<>(matrix.rowsNumber());
            List<List<Double>> columns = matrix.columns();
            for (int i = columns.size() - 1; i >= 0; --i) {
                List<Double> row = new ArrayList<>(columns.get(i).size());
                for (int j = columns.get(i).size() - 1; j >= 0; --j) {
                    row.add(columns.get(i).get(j));
                }
                outRows.add(row);
            }
            return new RectangleMatrix<>(outRows);
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    public static Matrix<Double> verticalLineTransposition(Matrix<Double> matrix) {
        if (matrix.rowsNumber() == matrix.columnsNumber()) {
            List<List<Double>> outRows = new ArrayList<>(matrix.rowsNumber());
            List<List<Double>> rows = matrix.rows();
            for (List<Double> row : rows) {
                List<Double> outRow = new ArrayList<>(row.size());
                for (int i = row.size() - 1; i >= 0; --i) {
                    outRow.add(row.get(i));
                }
                outRows.add(outRow);
            }
            return new RectangleMatrix<>(outRows);
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    public static Matrix<Double> horizontalLineTransposition(Matrix<Double> matrix) {
        if (matrix.rowsNumber() == matrix.columnsNumber()) {
            List<List<Double>> outRows = new ArrayList<>(matrix.rowsNumber());
            List<List<Double>> rows = matrix.rows();
            for (int i = rows.size() - 1; i >= 0; --i) {
                outRows.add(rows.get(i));
            }
            return new RectangleMatrix<>(outRows);
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    /**
     * Recursive algorithm for finding the determinant of a given matrix
     */
    public static double determinant(Matrix<Double> matrix) {
        if (matrix.rowsNumber() == matrix.columnsNumber()) {
            List<List<Double>> rows = matrix.rows();

            if (matrix.rowsNumber() == 1) {
                return rows.get(0).get(0);
            } else if (matrix.rowsNumber() == 2) {
                return rows.get(0).get(0) * rows.get(1).get(1)
                    - rows.get(0).get(1) * rows.get(1).get(0);
            }

            int[] cheapestRow = cheapestRow(matrix);
            int[] cheapestColumn = cheapestColumn(matrix);
            if (cheapestRow[1] <= cheapestColumn[1]) { // use row for finding cofactors
                int baseRowNumber = cheapestRow[0];
                double cofactorsSum = 0;
                for (int columnNumber = 0; columnNumber < matrix.columnsNumber(); columnNumber++) {
                    Double curElement = rows.get(baseRowNumber).get(columnNumber);
                    if (curElement != 0) {
                        cofactorsSum += Math.pow(-1, baseRowNumber + columnNumber)
                            * curElement
                            * determinant(new WithExcludedRowAndCoulmn<>(matrix, baseRowNumber, columnNumber));
                    }
                }
                return cofactorsSum;
            } else { // use column for finding cofactors
                int baseColumnNumber = cheapestColumn[1];
                double cofactorsSum = 0;
                for (int rowNumber = 0; rowNumber < matrix.rowsNumber(); rowNumber++) {
                    Double curElement = rows.get(rowNumber).get(baseColumnNumber);
                    if (curElement != 0) {
                        cofactorsSum += Math.pow(-1, baseColumnNumber + rowNumber)
                            * curElement
                            * determinant(new WithExcludedRowAndCoulmn<>(matrix, rowNumber, baseColumnNumber));
                    }
                }
                return cofactorsSum;
            }
        }
        return 0;
    }

    /**
     * The column of a matrix which has the biggest amount of zero values (the cheapest column)
     *
     * @param matrix non zero matrix
     * @return an array, where [0] - the number of the cheapest column (belongs to [0, number of columns in matrix),
     * [1] the number of non zero elements in the columns (the cost of the column)
     */
    private static int[] cheapestColumn(Matrix<Double> matrix) {
        int[] outValues = new int[2];
        outValues[1] = matrix.rowsNumber();
        int i = 0;
        for (List<Double> column : matrix.columns()) {
            int nonZeroElementsNumber = 0;
            for (Double val : column) {
                if (val != 0) nonZeroElementsNumber++;
            }
            if (nonZeroElementsNumber < outValues[1]) {
                outValues[0] = i;
                outValues[1] = nonZeroElementsNumber;
            }
            i++;
        }
        return outValues;
    }

    /**
     * The row of a matrix which has the biggest amount of zero values (the cheapest row)
     *
     * @param matrix non zero matrix
     * @return an array, where [0] - the number of the cheapest row (belongs to [0, number of rows in matrix),
     * [1] the number of non zero elements in the row (the cost of the row)
     */
    private static int[] cheapestRow(Matrix<Double> matrix) {
        int[] outValues = new int[2];
        outValues[1] = matrix.columnsNumber();
        int i = 0;
        for (List<Double> row : matrix.rows()) {
            int nonZeroElementsNumber = 0;
            for (Double val : row) {
                if (val != 0) nonZeroElementsNumber++;
            }
            if (nonZeroElementsNumber < outValues[1]) {
                outValues[0] = i;
                outValues[1] = nonZeroElementsNumber;
            }
            i++;
        }
        return outValues;
    }

    public static Matrix<Double> inversion(Matrix<Double> matrix) {
        double determinant = determinant(matrix);
        if (determinant != 0) {
            return multiplication(adjugate(matrix),
                                  (1 / determinant));
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }

    public static Matrix<Double> adjugate(Matrix<Double> matrix) {
        Matrix<Double> transposed = mainDiagonalTransposition(matrix);
        List<List<Double>> rows = transposed.rows();
        List<List<Double>> outRows = new ArrayList<>(transposed.rowsNumber());
        int rowNumber = 0;
        for (List<Double> row : rows) {
            List<Double> outRow = new ArrayList<>(transposed.columnsNumber());
            int columnNumber = 0;
            for (Double e : row) {
                outRow.add(Math.pow(-1, rowNumber + columnNumber)
                               * determinant(new WithExcludedRowAndCoulmn<>(transposed, rowNumber, columnNumber)));
                columnNumber++;
            }
            outRows.add(outRow);
            rowNumber++;
        }
        return new RectangleMatrix<>(outRows);
    }

    public static void printMatrix(Matrix<Double> matrix) {
        matrix.rows().forEach(row -> {
            for (Double e : row) {
                if (e == e.intValue()) {
                    System.out.print(e.intValue() + " ");
                } else {
                    System.out.print(e + " ");
                }
            }
            System.out.println();
        });
    }

    public static Matrix<Double> readMatrixFromConsole() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter the size of your matrix: > ");
            String[] rowsAndColumnsNumber = br.readLine().split(" ");
            int rowsNumber = Integer.parseInt(rowsAndColumnsNumber[0]);
            int columnsNumber = Integer.parseInt(rowsAndColumnsNumber[1]);

            System.out.println("Enter the matrix:");
            List<String> lines = new ArrayList<>(rowsNumber);
            for (int j = 0; j < rowsNumber; j++) {
                System.out.print("> ");
                lines.add(br.readLine());
            }
            return parsedDoubleMatrix(lines, rowsNumber, columnsNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RectangleMatrix<>(List.of(List.of(0d)));
    }


    private static RectangleMatrix<Double>
    parsedDoubleMatrix(List<String> matrixRows, int rowsNumber, int columnsNumber) {
        if (!isCorrectInput(rowsNumber,
                            columnsNumber)) {
            return new RectangleMatrix<>(List.of(List.of(0d)));
        }
        List<List<Double>> rows = new ArrayList<>(rowsNumber);
        for (int i = 0; i < rowsNumber; i++) {
            List<Double> row = Arrays.stream(matrixRows.get(i).split(" "))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
            rows.add(row);
        }
        return new RectangleMatrix<>(rows);
    }

    private static boolean isCorrectInput(int rowsNumber, int columnsNumber) {
        return rowsNumber >= 1 && columnsNumber >= 1;
    }
}
