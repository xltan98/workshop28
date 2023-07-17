package sg.edu.nus.iss.practiceworkshop7.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleException(ResourceNotFoundException ex,HttpServletRequest request,HttpServletResponse response,Model model){
        response.setStatus(404);
        ApiError errMsg= new ApiError(response.getStatus(),new Date(),ex.getMessage()+ " "+request.getRequestURI());
        model.addAttribute("errMsg", errMsg);

        return "not-found";
    }
    
}
