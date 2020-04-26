package collections;

import java.util.ArrayList;
import java.util.List;

public class CharNode {

  private boolean isWord;
  private final CharNode[] children;
  private final char label;

  public CharNode(char label) {
    isWord = false;
    children = new CharNode[26];
    this.label = label;
  }

  public boolean getIsWord() {
    return isWord;
  }

  private CharNode getChild(char index) {
    assert ('a' <= index && 'z' >= index);
    return children[(int) (index - 'a')];
  }

  public CharNode getChild(int index) {
    assert (0 <= index && 25 >= index);
    return children[index];
  }

  public char getLabel() {
    return label;
  }

  private void validWord() {
    isWord = true;
  }

  private void setChild(char index, CharNode charNode) {
    assert ('a' <= index && 'z' >= index);
    children[(int) (index - 'a')] = charNode;
  }

  public boolean contains(String s) {
    if (s.length() == 0) {
      return isWord;
    }
    CharNode child = getChild(s.charAt(0));
    if (child == null) {
      setChild(s.charAt(0), new CharNode(s.charAt(0)));
    }
    return getChild(s.charAt(0)).contains(s.substring(1));
  }

  public boolean add(String s) {
    if (s.length() == 0) {
      isWord = true;
      return true;
    }
    CharNode child = getChild(s.charAt(0));
    if (child == null) {
      setChild(s.charAt(0), new CharNode(s.charAt(0)));
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

  public List<String> getWords(CharNode c) {
    List<String> list = new ArrayList<>();
    getWordsRec("", list, c);
    return list;
  }

  private void getWordsRec(String base, List<String> strings, CharNode c) {
    if (c.isWord) {
      if (base.length() > 0) {
        strings.add((base + c.label).trim());
      } else {
        strings.add(String.valueOf(c.label));
      }
    }
    for (int i = 0; i < 26; i++) {
      if (!(c.children[i] == null)) {
        if (base.length() > 0) {
          getWordsRec(base + c.label, strings, c.getChild(i));
        } else {
          getWordsRec(base.concat(String.valueOf(c.label)), strings, c.getChild(i));
        }
      }
    }
  }
}
