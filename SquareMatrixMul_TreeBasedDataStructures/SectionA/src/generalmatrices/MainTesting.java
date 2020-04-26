package generalmatrices;

import generalmatrices.matrix.Matrix;
import java.util.Arrays;

public class MainTesting {

  public static void main(String[] args) {
    final Matrix<String> stringMatrix1 = new Matrix<>(
        Arrays.asList("a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9"));
    final Matrix<String> stringMatrix2 = new Matrix<>(
        Arrays.asList("b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9"));

    final Matrix<String> stringMatrixProduct =
        stringMatrix1.product(stringMatrix2, String::concat,
            (a, b) -> a + "!" + b);

    System.out.println(stringMatrixProduct);
  }
}
