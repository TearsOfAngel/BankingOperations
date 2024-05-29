package ru.vcarstein.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;

    public TransferRequest() {
    }

    public TransferRequest(Long fromAccountId, Long toAccountId, Double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }

}
