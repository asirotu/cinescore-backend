package br.com.cinescore.exception;

import lombok.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomExceptionResponse extends RuntimeException {

    private Date timestamp;
    private String message;
    private String details;
}
