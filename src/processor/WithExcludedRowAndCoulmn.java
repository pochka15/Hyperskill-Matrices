package processor;

import java.util.ArrayList;
import java.util.List;

public class WithExcludedRowAndCoulmn<E> implements Matrix<E> {
    private final Matrix<E> matrix;
    private final int excludedRowNumber;
    private final int excludedColumnNumber;

    public WithExcludedRowAndCoulmn(Matrix<E> matrix, int excludedRowNumber, int excludedColumnNumber) {
        this.matrix = matrix;
        this.excludedRowNumber = excludedRowNumber;
        this.excludedColumnNumber = excludedColumnNumber;
    }

    @Override
    public int rowsNumber() {
        return matrix.rowsNumber() - 1;
    }

    @Override
    public int columnsNumber() {
        return matrix.columnsNumber() - 1;
    }

    @Override
    public List<List<E>> rows() {
        List<List<E>> outRows = new ArrayList<>(rowsNumber());
        int i = 0;
        for (List<E> row : matrix.rows()) {
            if (i != excludedRowNumber) {
                List<E> tmpRow = new ArrayList<>(columnsNumber());
                int j = 0;
                for (E element : row) {
                    if (j != excludedColumnNumber) {
                        tmpRow.add(element);
                    }
                    j++;
                }
                outRows.add(tmpRow);
            }
            i++;
        }
        return outRows;
    }

    @Override
    public List<List<E>> columns() {
        List<List<E>> outColumns = new ArrayList<>(columnsNumber());
        int i = 0;
        for (List<E> column : matrix.columns()) {
            if (i != excludedColumnNumber) {
                List<E> tmpColumn = new ArrayList<>(rowsNumber());
                int j = 0;
                for (E element : column) {
                    if (j != excludedRowNumber) {
                        tmpColumn.add(element);
                    }
                    j++;
                }
                outColumns.add(tmpColumn);
            }
            i++;
        }
        return outColumns;
    }

    @Override
    public String toString() {
        return new RectangleMatrix<>(rows()).toString();
    }
}
