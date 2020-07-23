package processor;

import java.util.List;

public interface Matrix<E> {
    int rowsNumber();

    int columnsNumber();

    List<List<E>> rows();

    List<List<E>> columns();
}