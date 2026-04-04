package com.example.demo.controllerAdvice;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.customException.TodoNotFoundException;
import com.example.demo.filter.RequestIdFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ProblemDetail handleNotFound(TodoNotFoundException ex, HttpServletRequest req) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("TODO NotFound");
        pb.setDetail("todo is not exists in db");
        pb.setProperty("instance", req.getRequestURI());
        pb.setProperty("errorCode", "TODO_NOTFOUND");

        logWithRequestID(
                "TODO NotFound",
                req,
                ex);

        enrich(pb, req);

        return pb;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Malformed JSON");
        pb.setDetail("Request body is not valid JSON.");
        pb.setProperty("errorCode", "MALFORMED_JSON");
        pb.setProperty("instance", req.getRequestURI());

        ex.getMostSpecificCause();

        logWithRequestID(
                "Malformed JSON",
                req,
                ex);

        // 詳細が出るので返さないほうがいい？
        // pb.setProperty("cause", ex.getCause().getMessage());
        enrich(pb, req);

        return pb;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Validation failed");
        pb.setDetail("One or more fields are invalid.");
        pb.setProperty("errorCode", "VALIDATION_ERROR");
        pb.setProperty("instance", req.getRequestURI());

        List<Map<String, String>> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toFieldError)
                .toList();

        logWithRequestID(
                "Validation failed",
                req,
                ex);

        pb.setProperty("fieldErrors", fieldErrors);
        enrich(pb, req);

        return pb;
    }

    private Map<String, String> toFieldError(FieldError fe) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("field", fe.getField());
        m.put("message", fe.getDefaultMessage());

        return m;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnknown(Exception ex, HttpServletRequest req) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.name());
        pb.setDetail("Unexpected server error occured .");
        pb.setProperty("errorCode", "INTERNAL_ERROR");
        pb.setProperty("instance", req.getRequestURI());

        logWithRequestID(
                "Unexpected server error occured",
                req,
                ex);

        enrich(pb, req);
        return pb;
    }

    private void enrich(ProblemDetail pd, HttpServletRequest req) {
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("requestId", MDC.get(RequestIdFilter.MDC_KEY));
    }

    private void logWithRequestID(String title, HttpServletRequest req, Exception ex) {
        String rid = MDC.get(RequestIdFilter.MDC_KEY);
        String uri = req.getRequestURI();
        String cause = (ex.getCause() != null) ? ex.getCause().toString() : ex.getClass().getSimpleName();
        // JSON系はwarn、それ以外は状況でinfo/warn/errorを選ぶ
        log.warn("[{}] {} uri={} cause={}", rid, title, uri, cause);
    }
}
