package com.prac.springkafka.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class SendMessageRequest {

    private String accessToken;

    private Long sendTo;

    private String msg;

}
