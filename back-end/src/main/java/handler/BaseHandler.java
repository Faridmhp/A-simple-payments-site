package handler;

import request.ParsedRequest;

public interface BaseHandler {

  public String handleRequest(ParsedRequest request);
}
