package org.example.pasir_socha_mateusz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class GroupTransactionDTO {
    private Long groupId;
    private Double amount;
    private String type;
    private String title;
    private List<Long> selectedUserIds;
}
