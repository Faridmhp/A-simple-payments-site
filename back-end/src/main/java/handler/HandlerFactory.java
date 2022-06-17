package handler;

import request.ParsedRequest;

public class HandlerFactory {

  public static BaseHandler getHandler(ParsedRequest request) {
    switch (request.getPath()){
      case "/getPayment":
        return new GetPaymentHandler();
      case "/makeCreditCardPayment":
        return new CreditCardPaymentHandler();
      case "/makeCashPayment":
        return new CashPaymentHandler();
      case "/getAllPayments":
        return new GetAllPaymentsHandler();
      default:
        return new FallbackHandler(); // 404
    }
  }
}


