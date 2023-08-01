package com.prac.springkafka.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class SendMessageResponse {

    private String message;

}
