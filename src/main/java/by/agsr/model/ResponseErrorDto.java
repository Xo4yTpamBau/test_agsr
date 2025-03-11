package by.agsr.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseErrorDto(int code,
                               List<String> messages,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
                               @JsonSerialize(using = LocalDateTimeSerializer.class)
                               LocalDateTime timestamp) {
}
