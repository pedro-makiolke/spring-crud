package com.duckinc.patolog.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Problem {
    private Integer status;
    private LocalDateTime time;
    private String title;
    private List<ErrorField> errorList;

    @Getter
    @AllArgsConstructor
    public static class ErrorField{
        private String field;
        private String reason;
    }
}

