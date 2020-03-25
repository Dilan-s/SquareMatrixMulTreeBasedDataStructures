package generalmatrices.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Matrix<T> {

  private final T[][] matrix;
  private final int n;

  public Matrix(List<T> matrixEntires) throws IllegalArgumentException {
    if (matrixEntires.isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.n = (int) Math.sqrt(matrixEntires.size());
    matrix = (T[][]) new Object[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = matrixEntires.get(i * n + j);
      }
    }
  }

  public T get(int row, int col) {
    return matrix[row][col];
  }

  public int getOrder() {
    return n;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = 0; i < n; i++) {
      sb.append('[');
      for (int j = 0; j < n; j++) {
        sb.append(matrix[i][j]);
        if (j != n - 1) {
          sb.append(' ');
        } else {
          sb.append(']');
        }
      }
      if (i == n - 1) {
        sb.append(']');
      }
    }
    return sb.toString();
  }

  public Matrix<T> sum(Matrix<T> other, BinaryOperator<T> elementSum) {
    ArrayList<T> res = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        res.add(elementSum.apply(this.get(i, j), other.get(i, j)));
      }
    }
    return new Matrix<>(res);
  }

  public Matrix<T> product(Matrix<T> other, BinaryOperator<T> elementSum,
      BinaryOperator<T> elementProduct) {
    ArrayList<T> res = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        T runningRes = null;
        for (int k = 0; k < n; k++) {
          T elem = elementProduct.apply(this.get(i, k), other.get(k, j));
          if (k == 0) {
            runningRes = elem;
          } else {
            runningRes = elementSum.apply(runningRes, elem);
          }
        }
        res.add(runningRes);
      }
    }
    return new Matrix<>(res);
  }

}
