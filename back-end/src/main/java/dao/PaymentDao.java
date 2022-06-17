package dao;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import dto.BasePaymentDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import dto.paymentResponseDto;
import org.bson.Document;
import org.bson.types.ObjectId;
import server.uuidHolder;

import static com.mongodb.client.model.Filters.eq;

public class PaymentDao implements BaseDao<BasePaymentDto> {

  private static PaymentDao instance;
  public MongoCollection<Document> collection; // TODO instead of using a list, directly use mongo to load/store
  private static final Gson gson = new Gson();

  private PaymentDao(MongoCollection<Document> collection){
    this.collection = collection;
  }

  public static String MakeUID(){
    String uid = UUID.randomUUID().toString().toString();
    return uid;
  }

  public static PaymentDao getInstance() {
    if (instance == null) {
      instance = new PaymentDao(MongoConnection.getCollection("paymentCollection"));
    }
    return instance;
  }

  public static PaymentDao getInstance(MongoCollection<Document> collection) {
    instance = new PaymentDao(collection);
    return instance;
  }

  @Override
  /**return the payment uid and error, when no error, it should be null*/
  public String put(BasePaymentDto basePaymentDto) {
    //check getter
    String sentTo = basePaymentDto.getSentTo();
    try {
      MongoConnection.getDb().getCollection("usersCollection").find(eq("username", sentTo)).first();
    }
    catch (Exception e){
      System.out.println("Put: add payment result:" + "fail");
      return gson.toJson(new paymentResponseDto("00000000-0000-0000-0000000000000000",
              "add payment fail, recipient not exist "));
    }

    //add
    collection.insertOne(basePaymentDto.toDocument());

    //check success in database
    String uniqueId = basePaymentDto.getUniqueId();
    Document existingDocument = collection.find(eq("uniqueId", uniqueId)).first();
    if(existingDocument != null){
      String res = gson.toJson(new paymentResponseDto(uniqueId,null));
      System.out.println("Put: add payment result:" + existingDocument.toString());
      return res;
    }

    //the database not find it
    System.out.println("Put: add payment result:" + "fail");
    return gson.toJson(new paymentResponseDto("00000000-0000-0000-0000000000000000",
            "add payment fail, database error"));
  }

  @Override
  public BasePaymentDto get(String id) {
    BasePaymentDto findId = BasePaymentDto.toDto(collection.find(eq(id)).first());
    return findId;
  }

  public BasePaymentDto getNo(int num, String username) {
    List<Document> docs = collection.find(eq("sentBy", username)).into(new ArrayList<Document>());
    BasePaymentDto find;
    try{
        find = BasePaymentDto.toDto(docs.get(num));
    }
    catch (Exception e){
      find = null;
    }
    return find;
  }

  @Override
  public List<BasePaymentDto> getAll(){
    List<BasePaymentDto> paymentList = new ArrayList<BasePaymentDto>();
    List<Document> docs = collection.find().into(new ArrayList<Document>());
    for(Document d : docs){
      //System.out.println(d);
      //BasePaymentDto b = BasePaymentDto.toDto(d);
      System.out.println(BasePaymentDto.toDto(d));
      paymentList.add(BasePaymentDto.toDto(d));
    }
    System.out.println("return?");
    //System.out.println(paymentList);
    return paymentList;
  }


}
