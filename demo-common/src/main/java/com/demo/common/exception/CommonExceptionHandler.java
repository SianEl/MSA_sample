package com.demo.common.exception;

import com.demo.core.model.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice //모든 RestController에서 발생하는 exception을 처리한다.
public class CommonExceptionHandler {

    @ExceptionHandler(CommonRuntimeException.class)
    public ResponseEntity<Result> handleLoginException(LoginException e) {
        log.error("LoginException", e);
        Result result = new Result(e.getErrorCode(), e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
