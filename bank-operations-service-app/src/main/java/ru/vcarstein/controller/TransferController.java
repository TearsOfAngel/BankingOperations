package ru.vcarstein.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vcarstein.dto.TransferRequest;
import ru.vcarstein.exception.TransferException;
import ru.vcarstein.service.TransferService;

@RequestMapping("/api/transfer")
@Tag(name = "Money transfer", description = "Transfer money from one account to another")
@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Operation(summary = "Transfer money")
    @PostMapping
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest request) {
        try {
            transferService.transferMoney(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
            return ResponseEntity.ok().build();
        } catch (TransferException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}