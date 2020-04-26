package collections;

import collections.exceptions.InvalidWordException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCompactWordTree implements CompactWordsSet {

  //see individual implementations of locking

  private int size;
  private CharNode root;

  public SimpleCompactWordTree() {
    this.size = 0;
    this.root = new CharNode(' ');
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    if (contains(word)) {
      return false;
    }
    size++;
    return root.add(word);
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    if (!contains(word)) {
      return false;
    }
    size--;

    return root.remove(word);
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    return root.contains(word);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    return root.getWords(root);
  }
}
