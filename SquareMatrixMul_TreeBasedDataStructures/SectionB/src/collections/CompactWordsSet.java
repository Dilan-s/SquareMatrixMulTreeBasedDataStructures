package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;

public interface CompactWordsSet {

  static void checkIfWordIsValid(String word) throws InvalidWordException {
    if (word == null || word.length() == 0) {
      throw new InvalidWordException("word cannot be empty");
    }
    boolean validCharacters = word.chars().mapToObj(x -> 'a' <= x && 'z' >= x)
        .reduce((x, y) -> x && y).orElse(false);
    if (!validCharacters) {
      throw new InvalidWordException("word contains invalid characters");
    }
  }

  boolean add(String word) throws InvalidWordException;

  boolean remove(String word) throws InvalidWordException;

  boolean contains(String word) throws InvalidWordException;

  int size();

  List<String> uniqueWordsInAlphabeticOrder();

}
