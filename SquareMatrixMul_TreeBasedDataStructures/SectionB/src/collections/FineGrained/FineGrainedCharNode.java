package collections.FineGrained;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedCharNode {

  private boolean isWord;
  private final FineGrainedCharNode[] children;
  private final char label;
  private Lock lock;

  public FineGrainedCharNode(char label) {
    isWord = false;
    children = new FineGrainedCharNode[26];
    this.label = label;
    lock = new ReentrantLock();
  }

  public boolean getIsWord() {
    return isWord;
  }

  public FineGrainedCharNode[] getChildren() {
    return children;
  }

  public FineGrainedCharNode getChild(char index) {
    assert ('a' <= index && 'z' >= index);
    return children[(int) (index - 'a')];
  }

  public FineGrainedCharNode getChild(int index) {
    assert (0 <= index && 25 >= index);
    return children[index];
  }

  public char getLabel() {
    return label;
  }

  public void validWord() {
    isWord = true;
  }

  public void invalidWord() {
    isWord = false;
  }

  public void setChild(char index, FineGrainedCharNode charNode) {
    assert ('a' <= index && 'z' >= index);
    children[(int) (index - 'a')] = charNode;
  }



  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

}
