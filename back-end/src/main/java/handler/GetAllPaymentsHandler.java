package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.BasePaymentDto;
import request.ParsedRequest;
import response.ResponseBuilder;

import java.util.List;

public class GetAllPaymentsHandler implements BaseHandler {

  private static final Gson gson = new Gson();
  static String connectHttp = "HTTP/1.1 200 OK\n\n";

  @Override
  public String handleRequest(ParsedRequest request) {
    List<BasePaymentDto> payments = null;
    PaymentDao dao = PaymentDao.getInstance();
    payments = dao.getAll();
    return connectHttp + gson.toJson(payments);
  }

}