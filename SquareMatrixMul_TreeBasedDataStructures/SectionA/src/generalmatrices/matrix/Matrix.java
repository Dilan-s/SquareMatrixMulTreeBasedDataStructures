package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Matrix<T> {

  private final T[][] matrix;
  private final int order;

  public Matrix(List<T> entrySet) throws IllegalArgumentException {
    if (entrySet.isEmpty()) {
      throw new IllegalArgumentException();
    }
    Double n = Math.sqrt(entrySet.size());
    order = n.intValue();
    matrix = (T[][]) new Object[order][order];
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        matrix[i][j] = entrySet.get(i * order + j);
      }
    }
  }

  public T get(int row, int col) {
    return matrix[row][col];
  }

  public int getOrder() {
    return order;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("[");
    boolean first = true;
    for (int i = 0; i < order; i++) {
      sb.append("[");
      for (int j = 0; j < order; j++) {
        if (first) {
          first = false;
        } else {
          sb.append(" ");
        }
        sb.append(matrix[i][j]);
      }
      sb.append("]");
      first = true;
    }
    sb.append("]");
    return sb.toString();
  }

  public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum) {
    List<T> newMatrix = new ArrayList<>();
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        newMatrix.add(elementSum.apply(get(i, j), other.get(i, j)));
      }
    }
    return new Matrix<>(newMatrix);
  }

  public Matrix<T> product(Matrix<T> other, BinaryOperator<T> elementSum,
      BinaryOperator<T> elementProduct) {
    List<T> newMatrix = new ArrayList<>();
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        T newVal = elementProduct.apply(get(i, 0), other.get(0, j));
        for (int k = 0; k < order; k++) {
          if (k != 0){
            newVal = elementSum.apply(newVal, elementProduct.apply(get(i, k), other.get(k, j)));
          }
        }
        newMatrix.add(newVal);
      }
    }
    return new Matrix<>(newMatrix);
  }
}
