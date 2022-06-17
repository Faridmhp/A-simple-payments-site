package request;

public class CustomParser extends ParsedRequest {

  /**
   * This class break out request, pass and store variables to parsedRequest class
   * @param  request
   * @return {@link ParsedRequest} parsedRequest
   */
  public static final String delim = "[?= \n]";

  public static ParsedRequest parse(String request){

    //split the request to be an array
    String[] splitRequest = request.split(delim);

    ParsedRequest parsedRequest = new ParsedRequest();

    //Method
    parsedRequest.setMethod(splitRequest[0]);

    //path
    parsedRequest.setPath(splitRequest[1]);

    //check if there are any header, add header if request have; move to version
    String[] head =  findHeader(splitRequest);
    if (head != null){
      parsedRequest.setQueryParam(head[0],head[1]);
    }

    //language

    //Encoding

    //Connection

    //body:
    parsedRequest.setBody(findBody(splitRequest));

    return parsedRequest;
  }

  //check whether request have body- the line before body should be \n or null
  private static String findBody(String[] splitRequest){
    String body = "";

    if(splitRequest[splitRequest.length-2].length()<2){
      body = splitRequest[splitRequest.length-1];
    }

    return body;
  }

  private static String[] findHeader(String[] splitRequest){
    String[] header = null;
    for(int i = 2; i < splitRequest.length-1; i++){
      if(!splitRequest[i].equals("HTTP/1.1") && !splitRequest[i+1].equals("HTTP/1.1")){
        header = new String[] {splitRequest[i],splitRequest[i+1]};
        i++;
        continue;
      }
      //found version, skip the first line
      else if(splitRequest[i].equals("HTTP/1.1")){
        break;
      }
      //System.out.println("Index " + i + ", "+  splitRequest[i]);
    }

    return header;
  }
}

