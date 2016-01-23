package crypt;

public interface Crypt {
  String encode(String plainText);

  String decode(String encodedText);
}
