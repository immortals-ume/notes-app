package com.immortals.notesapp.controller.exception;

import com.immortals.notesapp.constant.NoteConstant;
import com.immortals.notesapp.model.error.ErrorDto;
import com.immortals.notesapp.util.DateUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.immortals.notesapp.util.DateUtils.getInstant;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
        super();
    }



    /**
     * Handling Bad Request For POST method and PUT method
     * <ol>
     *     <li>field level validation</li>
     *     <li>json level validation</li>
     *     <li>Type Mismatch</li>
     *     <li>Missing Servlet Request Parameter</li>
     *     <li>Missing Path Variable</li>
     *     <li>handleMethodArgumentTypeMismatch</li>
     * </ol>
     */


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException methodArgumentNotValidException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        logger.error(methodArgumentNotValidException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION,methodArgumentNotValidException);
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : methodArgumentNotValidException.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, DateUtils.getInstant(), methodArgumentNotValidException.getLocalizedMessage(), errors);
        return handleExceptionInternal(methodArgumentNotValidException, errorDto, headers, errorDto.getStatus(), request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(@NotNull TypeMismatchException typeMismatchException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        logger.error(typeMismatchException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, typeMismatchException);
        final String error = typeMismatchException.getValue() + " value for " + typeMismatchException.getPropertyName() + " should be of type " + typeMismatchException.getRequiredType();

        final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, DateUtils.getInstant(), typeMismatchException.getLocalizedMessage(), Collections.singletonList(error));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(@NotNull MissingServletRequestParameterException missingServletRequestParameterException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        logger.error(missingServletRequestParameterException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, missingServletRequestParameterException);

        final String error = missingServletRequestParameterException.getParameterName() + " parameter is missing";
        final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, DateUtils.getInstant(), missingServletRequestParameterException.getLocalizedMessage(), Collections.singletonList(error));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(@NotNull MissingPathVariableException missingPathVariableException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        logger.error(missingPathVariableException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, missingPathVariableException);

        final String error = missingPathVariableException.getParameter() + " path Variable is missing";
        final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, DateUtils.getInstant(), missingPathVariableException.getLocalizedMessage(), Collections.singletonList(error));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        logger.error(methodArgumentTypeMismatchException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, methodArgumentTypeMismatchException);
        final String error = methodArgumentTypeMismatchException.getName() + " should be of type " + Objects.requireNonNull(methodArgumentTypeMismatchException.getRequiredType()).getName();

        final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, DateUtils.getInstant(), methodArgumentTypeMismatchException.getLocalizedMessage(), Collections.singletonList(error));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(@NotNull NoHandlerFoundException noHandlerFoundException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        final String error = "No handler found for " + noHandlerFoundException.getHttpMethod() + " " + noHandlerFoundException.getRequestURL();

        logger.error(noHandlerFoundException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, noHandlerFoundException);

        final ErrorDto errorDto = new ErrorDto(HttpStatus.NOT_FOUND, DateUtils.getInstant(), noHandlerFoundException.getLocalizedMessage(), Collections.singletonList(error));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(@NotNull AsyncRequestTimeoutException asyncRequestTimeoutException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        logger.error(asyncRequestTimeoutException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, asyncRequestTimeoutException);
        final ErrorDto errorDto = new ErrorDto(HttpStatus.REQUEST_TIMEOUT, DateUtils.getInstant(), asyncRequestTimeoutException.getLocalizedMessage(), Collections.singletonList("The request took too long to process."));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        final StringBuilder error = new StringBuilder();
        error.append(httpRequestMethodNotSupportedException.getMethod());
        error.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(httpRequestMethodNotSupportedException.getSupportedHttpMethods()).forEach(t -> error.append(t).append(" "));

        final ErrorDto errorDto = new ErrorDto(HttpStatus.METHOD_NOT_ALLOWED, DateUtils.getInstant(), httpRequestMethodNotSupportedException.getLocalizedMessage(), Collections.singletonList(error.toString()));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    /**
     * Handling Unprocessable Entity Exception For POST and PUT request
     *
     * @return UNPROCESSABLE_ENTITY - exception
     */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleConstraintViolation(final ConstraintViolationException constraintViolationException) {
        logger.error(constraintViolationException.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, constraintViolationException);
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : constraintViolationException.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }

        final ErrorDto errorDto = new ErrorDto(HttpStatus.BAD_REQUEST, DateUtils.getInstant(), constraintViolationException.getLocalizedMessage(), errors);
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> duplicateDataException(DataIntegrityViolationException dataIntegrityViolationException) {
        final ErrorDto errorDto = new ErrorDto(HttpStatus.CONFLICT, DateUtils.getInstant(), dataIntegrityViolationException.getCause().getLocalizedMessage());
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }
    /**
     * Handling Internal Server Error For any type of Request
     *
     * @param e exception thrown at the service level
     * @return Error Response Entity
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDto> handleAll(final Exception e) {
        logger.error(e.getClass().getName() + NoteConstant.ENCOUNTERED_EXCEPTION, e);
        final ErrorDto errorDto = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, getInstant(), e.getLocalizedMessage(), Collections.singletonList("Error At Server End, Please try After Sometime"));
        return new ResponseEntity<>(errorDto, new HttpHeaders(), errorDto.getStatus());
    }

}
