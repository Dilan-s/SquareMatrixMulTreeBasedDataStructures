package collections.FineGrained;

import collections.CompactWordsSet;
import collections.exceptions.InvalidWordException;
import java.util.ArrayList;
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
    Optional<FineGrainedCharNode> curr = Optional.of(root);
    curr.get().lock();
    Optional<FineGrainedCharNode> parent = Optional.empty();
    try {
      for (int i = 0; i < word.length(); i++) {
        FineGrainedCharNode currFile = curr.get();
        FineGrainedCharNode nextFile = currFile.getChild(word.charAt(i));
        Optional<FineGrainedCharNode> next;
        if (nextFile == null) {
          currFile.setChild(word.charAt(i), new FineGrainedCharNode(word.charAt(i)));
          nextFile = currFile.getChild(word.charAt(i));
        }
        next = Optional.of(nextFile);
        next.get().lock();
        parent.ifPresent(p -> p.unlock());
        parent = curr;
        curr = next;
      }
      if (curr.get().getIsWord()) {
        return false;
      } else {
        curr.get().validWord();
        return true;
      }
    } finally {
      parent.ifPresent(p -> p.unlock());
      curr.ifPresent(p -> p.unlock());
    }
  }

  @Override
  public boolean remove(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    Optional<FineGrainedCharNode> curr = Optional.of(root);
    curr.get().lock();
    Optional<FineGrainedCharNode> parent = Optional.empty();
    try {
      for (int i = 0; i < word.length(); i++) {
        FineGrainedCharNode currFile = curr.get();
        FineGrainedCharNode nextFile = currFile.getChild(word.charAt(i));
        Optional<FineGrainedCharNode> next;
        if (nextFile == null) {
          next = Optional.empty();
        } else {
          next = Optional.of(nextFile);
        }
        if (next.isEmpty()) {
          currFile.unlock();
          parent.ifPresent(FineGrainedCharNode::unlock);
          return false;
        }
        next.get().lock();
        parent.ifPresent(p -> p.unlock());
        parent = curr;
        curr = next;
      }
      if (curr.get().getIsWord()) {
        curr.get().invalidWord();
        return true;
      } else {
        return false;
      }
    } finally {
      parent.ifPresent(p -> p.unlock());
      curr.ifPresent(p -> p.unlock());
    }
  }

  @Override
  public boolean contains(String word) throws InvalidWordException {
    CompactWordsSet.checkIfWordIsValid(word);
    Optional<FineGrainedCharNode> curr = Optional.of(root);
    curr.get().lock();
    Optional<FineGrainedCharNode> parent = Optional.empty();
    try {
      for (int i = 0; i < word.length(); i++) {
        FineGrainedCharNode currFile = curr.get();
        FineGrainedCharNode nextFile = currFile.getChild(word.charAt(i));
        Optional<FineGrainedCharNode> next;
        if (nextFile == null) {
          next = Optional.empty();
        } else {
          next = Optional.of(nextFile);
        }
        if (next.isEmpty()) {
          currFile.unlock();
          parent.ifPresent(FineGrainedCharNode::unlock);
          return false;
        }
        next.get().lock();
        parent.ifPresent(p -> p.unlock());
        parent = curr;
        curr = next;
      }
      return true;
    } finally {
      parent.ifPresent(p -> p.unlock());
      curr.ifPresent(p -> p.unlock());
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

    List<String> words = new ArrayList<>();

    for (FineGrainedCharNode child : root.getChildren()) {
      if (child != null) {
        parseTree(child, "", words);
      }
    }

    return words;
  }

  public void parseTree(FineGrainedCharNode node, String string, List<String> words) {

    string = string + node.getLabel();

    if (node.getIsWord()) {
      words.add(string);
    }

    for (FineGrainedCharNode child : node.getChildren()) {
      if (child != null) {
        parseTree(child, string, words);
      }
    }
  }
}