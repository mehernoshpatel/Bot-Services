package demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@ControllerAdvice
public class ErrorHandlingController {
	 ObjectMapper mapper = new ObjectMapper();
   
   
   /*
    * Exception thrown by edge in case of any issue with the request
    */
   @ExceptionHandler(HttpStatusCodeException.class)
   public ResponseEntity<String> httpStatusException(HttpStatusCodeException e) throws Exception {

        
     JsonNode root = mapper.readTree(e.getResponseBodyAsString());
    e.printStackTrace();
     return new ResponseEntity<String>(mapper.writeValueAsString( mapper.createObjectNode().set("Modified Response", root)), HttpStatus.INTERNAL_SERVER_ERROR);
   }
   
   /*
    * Any General exception scenario
    */
   @ExceptionHandler(Exception.class)
   public ResponseEntity<String> generalException(Exception e) throws Exception {
      
    //  ErrorModel errorModel=createErrorResponse(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.toString(),"Getting general exception from edge");
       e.printStackTrace();
            return new ResponseEntity<String>(mapper.writeValueAsString(mapper.createObjectNode().put("Modified Response for general exception", e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);        
      
   }
   
   /*
    * Creating error model to return to spa in particular  format
    */
  /* public ErrorModel createErrorResponse(String message,String code,String status){
      
      ErrorModel errorModel = new ErrorModel();
      
      MessageModel messageModel= errorModel.new MessageModel();
      messageModel.setMessage(message);
      messageModel.setCode(code);
      messageModel.setStatus(status);
      Error error =errorModel.new Error();
      error.setError(messageModel);
      errorModel.setResult(error);

           return errorModel;
   }*/
}

	 
 
 


