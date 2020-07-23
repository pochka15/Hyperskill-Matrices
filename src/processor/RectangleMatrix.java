package processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RectangleMatrix<E> implements Matrix<E> {
    private final List<List<E>> rows;
    private final int rowsNumber;
    private final int columnsNumber;

    /**
     * ctor
     *
     * @param nonEmptyRows must contain at list 1 element
     */
    public RectangleMatrix(List<List<E>> nonEmptyRows) {
        this.rows = nonEmptyRows;
        this.rowsNumber = nonEmptyRows.size();
        this.columnsNumber = nonEmptyRows.get(0).size();
    }

    @Override
    public int rowsNumber() {
        return rowsNumber;
    }

    @Override
    public int columnsNumber() {
        return columnsNumber;
    }

    @Override
    public List<List<E>> rows() {
        return rows;
    }

    @Override
    public List<List<E>> columns() {
        List<List<E>> columns = new ArrayList<>(columnsNumber);
        for (int column = 0; column < columnsNumber; column++) {
            ArrayList<E> currentColumn = new ArrayList<>(rowsNumber);
            for (List<E> row : rows) {
                currentColumn.add(row.get(column));
            }
            columns.add(currentColumn);
        }
        return columns;
    }

    @Override
    public String toString() {
        return rows.stream()
            .map(Object::toString)
            .collect(Collectors.joining("\n"));
    }
}
