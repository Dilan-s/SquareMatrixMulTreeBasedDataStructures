package collections.FineGrained;

import collections.CompactWordsSet;
import collections.exceptions.InvalidWordException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class FineGrainedSimpleCompactWordTree implements CompactWordsSet {

  private AtomicInteger size;
  private final FineGrainedCharNode root;

  public FineGrainedSimpleCompactWordTree() {
    this.size = new AtomicInteger(0);
    this.root = new FineGrainedCharNode(' ');
  }

  @Override
  public boolean add(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    if (contains(word)) {
      return false;
    }
    size.incrementAndGet();
    return root.add(word);
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    if (!contains(word)) {
      return false;
    }
    size.decrementAndGet();

    return root.remove(word);
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    Position p = findPosition(word);
    try {
      if (p.current.isPresent() && p.parent.isPresent()) {
        return root.contains(word);
      }
      return false;
    } finally {
      p.unlock();
    }
  }

  @Override
  public int size() {
    synchronized (this) {
      return size.get();
    }
  }

  @Override
  public List<String> uniqueWordsInAlphabeticOrder() {
    return root.getWords(root);
  }

  private Position findPosition(String word) {
    Optional<FineGrainedCharNode> curr = Optional.of(root);
    curr.get().lock();
    Optional<FineGrainedCharNode> parent = Optional.empty();
    for (int i = 0; i < word.length(); i++) {
      FineGrainedCharNode currFile = curr.get();
      Optional<FineGrainedCharNode> nextFile = Optional.of(currFile.getChild(word.charAt(0)));
      if (nextFile.isEmpty()) {
        currFile.unlock();
        parent.ifPresent(FineGrainedCharNode::unlock);
        return new Position(Optional.empty(), Optional.empty());
      }
      nextFile.get().lock();
      parent.ifPresent(p -> p.unlock());
      parent = curr;
      curr = nextFile;
      word = word.substring(1);
    }
    return new Position(parent, curr);
  }

  private class Position {

    private

    final Optional<FineGrainedCharNode> parent;
    final Optional<FineGrainedCharNode> current;

    public Position(Optional<FineGrainedCharNode> parent, Optional<FineGrainedCharNode> current) {
      this.parent = parent;
      this.current = current;
    }

    public void unlock() {
      current.ifPresent(p -> p.unlock());
      parent.ifPresent(p -> p.unlock());
    }
  }
}
