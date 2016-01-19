package token;

public interface Token {
  String generate(String username, String password);

  void revoke(String username, String password);
}
