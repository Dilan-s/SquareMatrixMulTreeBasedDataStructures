package generalmatrices.examples;

import generalmatrices.matrix.Matrix;
import generalmatrices.pair.PairWithOperators;
import java.util.List;
import java.util.Optional;

public class Example {

  public static Matrix<PairWithOperators> multiplyPairMatrices(
      List<Matrix<PairWithOperators>> matrices) {
    return matrices.stream()
        .reduce((x, y) -> x.product(y, PairWithOperators::sum, PairWithOperators::product))
        .orElse(null);
  }

}
