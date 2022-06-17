package handler;

import request.ParsedRequest;

public class FallbackHandler implements BaseHandler {
  static String notFound = "HTTP/1.1 404 Not Found\n";

  @Override
  public String handleRequest(ParsedRequest request) {
    return notFound;
  }
}
