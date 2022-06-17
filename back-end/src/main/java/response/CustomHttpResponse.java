package response;

import java.util.Map;

public class CustomHttpResponse {
  public final Map<String,String> headers;
  public final String status;
  public final String version;
  public final String body;

  public CustomHttpResponse(Map<String, String> headers, String status, String version,
      String body) {
    this.headers = headers;
    this.status = status;
    this.version = version;
    this.body = body;
  }

  /**
   * version + " " + status + "\n"
   * headers / skip this if no header
   * skip line & body / skip this if no header
   * @return String
   */
  public String toString(){
    return version + " " + status + "\n"
            + getHeaderString()
            + (body!=null ? "\n" + body : "");
  }

  /**
   * Transfer header form map to string, remove "{" & "}" and replace ":" to "="
   * @return header key=value
   */
  public String getHeaderString(){
    return (headers!=null ? headers.toString().replace("=", ": ").replace("{", "").replace("}", "") + "\n" : "");
  }
}
