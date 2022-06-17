package dao;

import dto.BasePaymentDto;
import java.util.List;

public interface BaseDao<T extends BasePaymentDto> {

  public String put(T t);

  public T get(String id);

  List<T> getAll();
}
