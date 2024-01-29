package com.endpointstest.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseTest {
    private Integer id;
    private String name;
    private String rate;
}
