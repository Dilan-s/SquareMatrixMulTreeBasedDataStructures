package collections.CoarseGrained;

import collections.CompactWordsSet;
import collections.CharNode;
import collections.SimpleCompactWordTree;
import collections.exceptions.InvalidWordException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CoarseGrainedSimpleCompactWordTree implements CompactWordsSet {

  private AtomicInteger size;
  private CoarseGrainedCharNode root;

  public CoarseGrainedSimpleCompactWordTree() {
    size = new AtomicInteger(0);
    root = new CoarseGrainedCharNode(' ');
  }

  @Override
  public synchronized boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    if (contains(word)) {
      return false;
    }
    size.incrementAndGet();
    return root.add(word);
  }

  @Override
  public synchronized boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    if (!contains(word)) {
      return false;
    }
    size.decrementAndGet();

    return root.remove(word);
  }

  @Override
  public synchronized boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    return root.contains(word);
  }

  @Override
  public int size() {
    return size.get();
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    return root.getWords(root);
  }
}
