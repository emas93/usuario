package com.emas93.usuario.controller.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private String numeroTelefone;
    private String ddd;
}
