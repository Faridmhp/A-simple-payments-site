package dto;

import org.bson.Document;

public abstract class BasePaymentDto {

  private String uniqueId;
  public Double amount;
  private String sentBy;
  private String sentTo;
  private String note;

  //check the value valid and trans to double, else return -1
  public static double filter(String amount){
    double Ramount = 0;
    try {
      Ramount = Double.valueOf(amount);
    } catch ( NumberFormatException e){return -1;}
    return Ramount;
  }

  public BasePaymentDto() {
  }

  public BasePaymentDto(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getUniqueId(){
    return uniqueId;
  }

  public BasePaymentDto setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return this;
  }

  public BasePaymentDto setAmount(Double amount) {
    this.amount = amount;
    return this;
  }

  public abstract Document toDocument();

  public static BasePaymentDto toDto(Document document){
    if(document.getString("type").contentEquals("cash")){
      System.out.println("Correct1");
      return CashPayment.fromDocument(document);
    }
    if(document.getString("type").contentEquals("credit")){
      System.out.println("Correct1");
      return CreditCardPayment.fromDocument(document);
    }
    return null;
  }

  //public abstract void setSentBy(String senter);

  //public abstract String getSentBy();

  public void setSentBy(String sentBy){
    this.sentBy = sentBy;
  }

  public String getSentBy() {
    return sentBy;
  }

  public void setSentTo(String getter){
    this.sentTo = getter;
  }

  public String getSentTo() {
    return sentTo;
  }

  public void setNote(String note){
    this.note = note;
  }

  public String getNote() {
    return note;
  }

}
