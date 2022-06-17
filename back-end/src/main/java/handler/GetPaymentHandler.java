package handler;

import com.google.gson.Gson;
import dao.PaymentDao;
import dto.BasePaymentDto;
import request.ParsedRequest;
import response.ResponseBuilder;

public class GetPaymentHandler implements BaseHandler {

  private static final Gson gson = new Gson();

  @Override
  public String handleRequest(ParsedRequest request) {
    PaymentDao dao = PaymentDao.getInstance();
    BasePaymentDto paymentDto = dao.get(request.getQueryParam("id"));
    return new ResponseBuilder()
            .setStatus("200 OK")
            .setVersion("HTTP/1.1")
            .setBody(gson.toJson(paymentDto))
            .build()
            .toString();
  }
}

