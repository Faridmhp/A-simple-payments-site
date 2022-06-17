package dto;

import org.bson.Document;

public class CashPayment extends BasePaymentDto {

  private String type = "cash";
  private String number = "null";
  private String securityCode = "null";

  public CashPayment() {
  }

  public CashPayment(String uniqueId, Double amount) {
    super(uniqueId);
    this.amount = amount;
  }

  public CashPayment(Double amount) {
    super();
    this.amount = amount;
  }

  @Override
  public Document toDocument() {
    Document doc = new Document("name", "MongoDB")
            .append("amount", amount)
            .append("type", "cash")
            .append("uniqueId", getUniqueId())
            .append("sentBy", getSentBy())
            .append("sentTo", getSentTo())
            .append("note", getNote());
    System.out.println("doc: " + doc.toString());
    return doc;
  }

  public static CashPayment fromDocument(Document document) {
    String className = document.getString("type");
    CashPayment cashPayment = null;

    if(className.contentEquals("cash")){
      cashPayment = new CashPayment(document.getObjectId("_id").toString(),
              document.getDouble("amount"));

      cashPayment.setSentBy(document.getString("sentBy"));
      cashPayment.setSentTo(document.getString("sentTo"));
      cashPayment.setNote(document.getString("note"));
    }
    return cashPayment;
  }
}
