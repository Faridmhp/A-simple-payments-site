package server;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.util.JSON;
import dao.PaymentDao;
import dto.*;
import org.bson.Document;
import request.CustomParser;
import request.ParsedRequest;
import response.CustomHttpResponse;
import response.ResponseBuilder;
//import LoginDto;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class Server {

  private static final Gson gson = new Gson();
  private static uuidHolder UUID;
  private static int index;

  public static void main(String[] args) {
    ServerSocket ding;
    Socket dong = null;
    index = 0;

    UUID = new uuidHolder();
    try {
      ding = new ServerSocket(1234);
      System.out.println("Opened socket " + 1234);
      while (true) {
        // keeps listening for new clients, one at a time
        try {
          dong = ding.accept(); // waits for client here
        } catch (IOException e) {
          System.out.println("Error opening socket");
          System.exit(1);
        }

        InputStream stream = dong.getInputStream();
        byte[] b = new byte[1024*20];
        stream.read(b);
        String input = new String(b).trim();
        System.out.println(input);

        BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);  // char output to the client

        // HTTP Response
        if(!input.isEmpty()){
          writer.println(processRequest(input,UUID));
        }else{
          writer.println("HTTP/1.1 200 OK");
          writer.println("Server: TEST");
          writer.println("Connection: close");
          writer.println("Content-type: text/html");
          writer.println("");
        }

        dong.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }

  /**
   * This class process the request string and return a
   * response string
   * @return response string {@link ResponseBuilder}
   */
  public static String processRequest(String requestString, uuidHolder UUID){

    ParsedRequest request = CustomParser.parse(requestString);

    //initialize the response information holder
    String status = "";
    String body = null;
    Map<String,String> header = null;

    MongoClient mongoClient = new MongoClient("localhost", 27017);
    // get ref to database
    MongoDatabase db = mongoClient.getDatabase("UsersDatabase");
    // get ref to collection
    MongoCollection<Document> usersCollection = db.getCollection("usersCollection");

    System.out.println("Body: " + request.getBody());

    //GET
    if(request.getMethod().equals("GET")){
      String path = request.getPath();
      switch(path) {
        case "/getPayment":
          String username;
          BasePaymentDto getPaymentRes;
          //try to get the payment by id, not success status will be 404 and body will keep null
          if(!getState(UUID, usersCollection)){
            getPaymentRes = new CashPayment(0.0);
            getPaymentRes.setUniqueId("Not Login yet");
            getPaymentRes.setSentBy("Not Login yet");
            getPaymentRes.setSentTo("Not Login yet");
            getPaymentRes.setNote("Not Login yet");
            body = gson.toJson(getPaymentRes);
            break;
          }
          else{
            username = getUsername(UUID, usersCollection);
          }

          getPaymentRes = PaymentDao.getInstance().getNo(index, username);
          if(getPaymentRes == null){
            getPaymentRes = new CashPayment(0.0);
            getPaymentRes.setUniqueId("Not Found");
            getPaymentRes.setSentBy("Not Found");
            getPaymentRes.setSentTo("Not Login yet");
            getPaymentRes.setNote("Not Login yet");
            body = gson.toJson(getPaymentRes);
            break;
          }
          body = gson.toJson(getPaymentRes.toDocument());
          index++;

          status = "200 OK";
          break;
        case "/getAllPayments":
          //try to get the payment by id, not success status will be 404 and body will keep null
          body = gson.toJson(PaymentDao.getInstance().getAll());
          status = "200 OK";
          break;
        //otherwise, 404 not find
        case "/getState":
          if(UUID.get() == null){
            body = gson.toJson(new LoginResponseDto( false, "not login yet"));
          }
          if(getState(UUID, usersCollection)){
            String stateUsername = getUsername(UUID, usersCollection);
            body = gson.toJson(new LoginResponseDto( true, stateUsername));
          }
          else{
            body = gson.toJson(new LoginResponseDto( false, "Time out"));
          }
          break;
        default:
          status = "404 Not Found";
      }
    }

    //POST
    if(request.getMethod().equals("POST")){
      //go to payment
      String path = request.getPath();
      switch(path){
        case "/makeCreditCardPayment":
        case "/makeCashPayment":
          String addPaymentResult = addPayment(request, UUID, usersCollection);
          //paymentResponseDto MCPres = gson.fromJson(addPaymentResult, paymentResponseDto.class);
          status = "200 OK";
          body = addPaymentResult;
          break;
        case "/logIn":
          String respond = login(request, usersCollection);
          LoginResponseDto res = gson.fromJson(respond, LoginResponseDto.class);
          if(!respond.matches("error")){
            System.out.println("enter\n");
            LoginDto loginDto = gson.fromJson(request.getBody(), LoginDto.class);
            UUID.set(usersCollection.find(eq("username", loginDto.username)).first().get("_id"));
            System.out.println(UUID.get());
          }
          //System.out.println("respond: " + respond);
          status = "200 OK";
          body = respond;
          break;
        case "/logOut":
          status = "200 OK";
          body = gson.toJson(new LoginResponseDto(true,""));
          UUID.set(null);
          break;
        case "/register":
          String regRespond = register(request, usersCollection);
          LoginResponseDto regRes = gson.fromJson(regRespond, LoginResponseDto.class);
          System.out.println(regRespond);
          if(regRes.get()){
            System.out.println("enter\n");
            LoginDto loginDto = gson.fromJson(request.getBody(), LoginDto.class);
            UUID.set(usersCollection.find(eq("username", loginDto.username)).first().get("_id"));
            System.out.println(UUID.get());
          }
          body = regRespond;
          status = "200 OK";
          break;
        default:
          status = "404 Not Found";
      }
    }

    //build response
    CustomHttpResponse res = new ResponseBuilder()
            .setVersion("HTTP/1.1").setStatus(status)
            .setBody(body)
            .setHeaders(header)
            .build();
    System.out.println("res:" + res.toString());
    System.out.println("uuid:" + UUID.get());
    return res.toString();
  }

  /**
   Check the request body, add it to the PaymentDao list and return the result of process
   @return boolean
   */
  private static String addPayment(ParsedRequest request, uuidHolder uuid, MongoCollection<Document> usersCollection){
    //split the request to be an array - this can be replaced by use Json way
    BasePaymentDto newPayment;
    String getter = "";
    String note = " ";
    //System.out.println("uuid:" + res.toString());

    if(uuid.get() == null){
      return gson.toJson(new paymentResponseDto(null, "not login yet"));
    }

    //Create a new payment
    try {
      //set payment
      if(request.getPath().equals("/makeCreditCardPayment")) {
        newPayment = gson.fromJson(request.getBody(), CreditCardPayment.class);
        newPayment.setUniqueId(PaymentDao.MakeUID());
      }
      else if(request.getPath().equals("/makeCashPayment")){
        newPayment = gson.fromJson(request.getBody(), CashPayment.class);
        //newPayment = new CashPayment(Double.valueOf((String)test.get("amount")));
        //newPayment = gson.fromJson(request.getBody(), CashPayment.class);
        newPayment.setUniqueId(PaymentDao.MakeUID());
      }
      else {
        newPayment = null;
      }
    }
    catch (Exception e){
      return gson.toJson(new paymentResponseDto(null, "invalid number"));
    }

    //find the getter
    try {
      Map test = (Map)JSON.parse(request.getBody());
      System.out.println("test:\n" + test.get("sentTo") + " " + test.get("Note"));
      getter = (String) test.get("sentTo");
      note =  (String) test.get("Note") == null ? " ": (String) test.get("Note");
      usersCollection.find(eq("username", getter)).first().get("_id");
    }
    catch (Exception e){
      return gson.toJson(new paymentResponseDto(null, "the getter not exsit"));
    }

    //clean up the old one - need to delete it later when thee paymentDao list can be process
    String addPaymentResult = "";
    if(newPayment != null){
      String username = getUsername(uuid, usersCollection);
      newPayment.setSentBy(username);
      newPayment.setSentTo(getter);
      newPayment.setNote(note);
      addPaymentResult = PaymentDao.getInstance().put(newPayment);
    }
    System.out.println("Server add payment result:" + addPaymentResult);
    return addPaymentResult;
  }

  private static String login(ParsedRequest request, MongoCollection<Document> usersCollection){
    // open connection
    String body = request.getBody();
    LoginDto loginDto = gson.fromJson(body, LoginDto.class);
    Long current_time = System.currentTimeMillis();

    Document existingUser = usersCollection.find(eq("username", loginDto.username)).first();
    System.out.println(existingUser);
    if (existingUser != null) {
      if (existingUser.getString("password").equals(loginDto.password)) {
        Object id = existingUser.get("_id");
        existingUser.replace("time", current_time);
        //System.out.println(UUID.get());
        usersCollection.updateOne(Filters.eq("_id", id), Updates.set("time", current_time));
        return gson.toJson(new LoginResponseDto(true, null));
      } else {
        return gson.toJson(new LoginResponseDto(false, "Invalid password"));
      }
    } else {
      return gson.toJson(new LoginResponseDto(false, "Invalid username"));
    }
  }

  //check activity with 10 min
  public static boolean getState(uuidHolder UUID, MongoCollection<Document> usersCollection){
    Document existingUser;
    boolean isLoggedIn = false;
    long current_time = System.currentTimeMillis();
    try {
      existingUser = usersCollection.find(eq("_id", UUID.get())).first();
      isLoggedIn = current_time - existingUser.getLong("time") <= 600000;
      if(isLoggedIn){
        usersCollection.updateOne(Filters.eq("_id", UUID.get()), Updates.set("time", current_time));
      }
    }
    catch (Exception e){
      isLoggedIn = false;
    }

    return isLoggedIn;
  }

  public static String register(ParsedRequest request, MongoCollection<Document> usersCollection){
    String body = request.getBody();
    LoginDto loginDto = gson.fromJson(body, LoginDto.class);
    Long current_time = System.currentTimeMillis();

    //username empty
    if(loginDto.username.equals("")){
      return gson.toJson(new LoginResponseDto(false, "Invalid username"));
    }
    //password empty
    if(loginDto.password.equals("")){
      return gson.toJson(new LoginResponseDto(false, "Invalid password"));
    }

    Document existingUser = usersCollection.find(eq("username", loginDto.username)).first();
    if(existingUser != null){
      return gson.toJson(new LoginResponseDto(false, "register failed, username used"));
    }

    var  doc = new Document("username", loginDto.username)
              .append("password", loginDto.password).
              append("time", current_time);

    usersCollection.insertOne(doc);
    return gson.toJson(new LoginResponseDto(true, null));
  }

  public static String getUsername(uuidHolder UUID, MongoCollection<Document> usersCollection){
    Document existingUser;
    String username;
    try{
      existingUser = usersCollection.find(eq("_id",UUID.get())).first();
      username = existingUser.getString("username");
      System.out.println("username:" + username);
    }
    catch (Exception e){
      return "";
    }
    return username;
  }
}

class error{
  public boolean isError;
  public String error;

  public error(String errors) {
    this.isError = true;
    this.error = errors;
  }
}
