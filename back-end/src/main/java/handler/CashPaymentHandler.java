package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.BasePaymentDto;
import dto.CashPayment;
import request.ParsedRequest;

import java.util.UUID;

public class CashPaymentHandler  implements BaseHandler{
  static String notFound = "HTTP/1.1 404 Not Found\n";
  static String connectHttp = "HTTP/1.1 200 OK\n";

  private static final Gson gson = new Gson();

  // Only Post
  @Override
  public String handleRequest(ParsedRequest request) {
    BasePaymentDto cashPayment = gson.fromJson(request.getBody(), CashPayment.class);
    if (request.getMethod().equalsIgnoreCase("Post")) {
      if (request.getPath().equalsIgnoreCase("/makeCashPayment")) {
        cashPayment.setUniqueId(UUID.randomUUID().toString());
        //System.out.println(cashPayment);
        //PaymentDao.getInstance().put(cashPayment);
        //PaymentDao.getInstance().getAll().clear();
        PaymentDao.getInstance().put(cashPayment);
        return connectHttp;
      } else {
        return notFound;
      }
    }
    if (request.getMethod().equalsIgnoreCase("Get")) {
      if (request.getPath().equalsIgnoreCase("/getPayment")) {
        cashPayment.setUniqueId(UUID.randomUUID().toString());
        //System.out.println(cashPayment);
        //PaymentDao.getInstance().put(cashPayment);
        //PaymentDao.getInstance().getAll().clear();
        PaymentDao.getInstance().put(cashPayment);
        return connectHttp;
      } else {
        return notFound;
      }
    }
    return null;
  }
}
