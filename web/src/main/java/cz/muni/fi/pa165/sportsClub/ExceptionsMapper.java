package cz.muni.fi.pa165.sportsClub;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.muni.fi.pa165.sportsClub.exception.SportClubsRuntimeException;
import cz.muni.fi.pa165.sportsClub.exception.TokenValidationException;

@ControllerAdvice
class ExceptionsMapper {
	
    @ExceptionHandler(SportClubsRuntimeException.class)
    public void unexpectedErrorHandle(SportClubsRuntimeException e, HttpServletResponse response) throws IOException {
    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	response.getWriter().append(e.getMessage());
    }

    @ExceptionHandler(TokenValidationException.class)
    public void tokenValidationHandle(TokenValidationException e, HttpServletResponse response) throws IOException {
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	response.getWriter().append(e.getMessage());
    }
    
    /**
     * Converts exception to proper status code and appends meaning full message.
     * Checks if exception is thrown by programmer or framework:
     * 1) if thrown by programmer: its message is appended to response
     * 2) if thrown by framework: finds which constraint was violated and appended meaning full message to response
     * @param e	exception thrown
     * @param response to be sent to user
     * @throws IOException
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void constraintViolationHandle(DataIntegrityViolationException e, HttpServletResponse response) throws IOException {
    	response.setStatus(HttpServletResponse.SC_CONFLICT);
    	if(!e.getMessage().contains("Exception")){
    		response.getWriter().append(e.getMessage());
    	}
    	StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	e.printStackTrace(pw);
    	String stackTrace = sw.toString();
    	//response.getWriter().append(stackTrace);
    	Pattern constraintName = Pattern.compile("(\'UNIQUE_.+\')"); 
    	Matcher m = constraintName.matcher(stackTrace);
    	if(m.find()){
    		response.getWriter().append("Violated constraint " + m.group(1));
    	}
    }
    
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void noResultHandle(EmptyResultDataAccessException e, HttpServletResponse response) throws IOException {
    	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    	response.getWriter().append(e.getMessage());
    }
}
