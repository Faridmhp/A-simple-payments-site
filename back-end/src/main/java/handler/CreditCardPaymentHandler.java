package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.BasePaymentDto;
import dto.CreditCardPayment;
import request.ParsedRequest;

import java.util.UUID;

public class CreditCardPaymentHandler  implements BaseHandler {

  private static final Gson gson = new Gson();
  static String notFound = "HTTP/1.1 404 Not Found\n";
  static String connectHttp = "HTTP/1.1 200 OK\n";

  // Only Post
  @Override
  public String handleRequest(ParsedRequest request) {
    BasePaymentDto creditCardPayment = gson.fromJson(request.getBody(), CreditCardPayment.class);
    if (request.getMethod().equalsIgnoreCase("Post")) {
      if (request.getPath().equalsIgnoreCase("/makeCreditCardPayment")) {
        creditCardPayment.setUniqueId(UUID.randomUUID().toString());
        PaymentDao.getInstance().put(creditCardPayment);

        return connectHttp;
      } else {
        return notFound;
      }
    }

    if (request.getMethod().equalsIgnoreCase("Get")) {
      if (request.getPath().equalsIgnoreCase("/getPayment")) {
        creditCardPayment.setUniqueId(UUID.randomUUID().toString());
        PaymentDao.getInstance().put(creditCardPayment);
        return connectHttp;
      } else {
        return notFound;
      }
    }
    return null;
  }
}
