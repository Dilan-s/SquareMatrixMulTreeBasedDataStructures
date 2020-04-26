package collections.FineGrained;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedCharNode {

  private boolean isWord;
  public final FineGrainedCharNode[] children;
  private final char label;
  private Lock lock;

  public FineGrainedCharNode(char label) {
    isWord = false;
    children = new FineGrainedCharNode[26];
    this.label = label;
    lock = new ReentrantLock();
  }

  private boolean getIsWord() {
    return isWord;
  }

  public FineGrainedCharNode getChild(char index) {
    assert ('a' <= index && 'z' >= index);
    return children[(int) (index - 'a')];
  }

  private FineGrainedCharNode getChild(int index) {
    assert (0 <= index && 25 >= index);
    return children[index];
  }

  private char getLabel() {
    return label;
  }

  private void validWord() {
    isWord = true;
  }

  private void setChild(char index, FineGrainedCharNode charNode) {
    assert ('a' <= index && 'z' >= index);
    children[(int) (index - 'a')] = charNode;
  }

  public boolean contains(String s) {
    if (s.length() == 0) {
      return isWord;
    }
    FineGrainedCharNode child = getChild(s.charAt(0));

    if (child == null) {
      setChild(s.charAt(0), new FineGrainedCharNode(s.charAt(0)));
    }
    return getChild(s.charAt(0)).contains(s.substring(1));
  }

  public boolean add(String s) {
    if (s.length() == 0) {
      isWord = true;
      return true;
    }
    FineGrainedCharNode child = getChild(s.charAt(0));
    if (child == null) {
      setChild(s.charAt(0), new FineGrainedCharNode(s.charAt(0)));
    }
    return getChild(s.charAt(0)).add(s.substring(1));
  }

  public boolean remove(String s) {
    if (s.length() == 0) {
      isWord = false;
      return true;
    }
    return getChild(s.charAt(0)).remove(s.substring(1));
  }

  public List<String> getWords(FineGrainedCharNode c) {
    List<String> list = new ArrayList<>();
    getWordsRec("", list, c);
    return list;
  }

  private void getWordsRec(String base, List<String> strings, FineGrainedCharNode c) {
    if (c.getIsWord()) {
      if (base.length() > 0) {
        strings.add((base + c.getLabel()).trim());
      } else {
        strings.add(String.valueOf(c.getLabel()));
      }
    }
    for (int i = 0; i < 26; i++) {
      if (!(c.getChild(i) == null)) {
        if (base.length() > 0) {
          getWordsRec(base + c.getLabel(), strings, c.getChild(i));
        } else {
          getWordsRec(base.concat(String.valueOf(c.getLabel())), strings, c.getChild(i));
        }
      }
    }
  }

  public void lock() {
    lock.lock();
  }

  public void unlock() {
    lock.unlock();
  }

}
