package com.authsys.SpringSecurity.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class SendMessageRequest {

    private Long senderId;

    private Long sendTo;

    private String msg;
}
