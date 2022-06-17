package dto;

import org.bson.Document;

public class CreditCardPayment extends BasePaymentDto {

  private String number;
  private String securityCode;
  private String type = "credit";


  public CreditCardPayment(Double amount, String number, String securityCode) {
    super();
    this.amount = amount;
    this.number = number;
    this.securityCode = securityCode;
  }

  @Override
  public Document toDocument() {
    Document doc = new Document("name", "MongoDB")
            .append("amount", amount)
            .append("type", "credit")
            .append("securityCode", securityCode)
            .append("number", number)
            .append("uniqueId", getUniqueId())
            .append("sentBy", getSentBy())
            .append("sentTo", getSentTo())
            .append("note", getNote());

    System.out.println("doc: " + doc.toString());
    return doc;
  }

  public static CreditCardPayment fromDocument(Document document){
    String className = document.getString("type");
    CreditCardPayment creditCardPayment = null;

    if(className.contentEquals("credit")){
      creditCardPayment = new CreditCardPayment(document.getDouble("amount"),
              document.getString("number"),
              document.getString("securityCode"));

      creditCardPayment.setUniqueId(document.getObjectId("_id").toString());
      creditCardPayment.setSentBy(document.getString("sentBy"));
      creditCardPayment.setSentTo(document.getString("sentTo"));
    }
    return creditCardPayment;
  }

  public String getNumber() {
    return number;
  }

  public String getSecurityCode() {
    return securityCode;
  }

  public String getType() {
    return type;
  }

  public String toString() {
    return "type" + type + "\namount" + amount +  "uid" + getUniqueId();
  }
}
