package collections.CoarseGrained;

import collections.CharNode;
import java.util.ArrayList;
import java.util.List;

public class CoarseGrainedCharNode {

  private boolean isWord;
  private final CoarseGrainedCharNode[] children;
  private final char label;

  public CoarseGrainedCharNode(char label) {
    isWord = false;
    children = new CoarseGrainedCharNode[26];
    this.label = label;
  }

  private boolean getIsWord() {
    return isWord;
  }

  private CoarseGrainedCharNode getChild(char index) {
    assert ('a' <= index && 'z' >= index);
    return children[(int) (index - 'a')];
  }

  private CoarseGrainedCharNode getChild(int index) {
    assert (0 <= index && 25 >= index);
    return children[index];
  }

  private char getLabel() {
    return label;
  }

  private void validWord() {
    isWord = true;
  }

  private void setChild(char index, CoarseGrainedCharNode charNode) {
    assert ('a' <= index && 'z' >= index);
    children[(int) (index - 'a')] = charNode;
  }

  public boolean contains(String s) {
    if (s.length() == 0) {
      return isWord;
    }
    CoarseGrainedCharNode child = getChild(s.charAt(0));
    if (child == null) {
      setChild(s.charAt(0), new CoarseGrainedCharNode(s.charAt(0)));
    }
    return getChild(s.charAt(0)).contains(s.substring(1));
  }

  public boolean add(String s) {
    if (s.length() == 0) {
      isWord = true;
      return true;
    }
    CoarseGrainedCharNode child = getChild(s.charAt(0));
    if (child == null) {
      setChild(s.charAt(0), new CoarseGrainedCharNode(s.charAt(0)));
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

  public List<String> getWords(CoarseGrainedCharNode c) {
    List<String> list = new ArrayList<>();
    getWordsRec("", list, c);
    return list;
  }

  private void getWordsRec(String base, List<String> strings, CoarseGrainedCharNode c) {
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
}
