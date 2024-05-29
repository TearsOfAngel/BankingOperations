package ru.vcarstein.service;

public interface TransferService {
    void transferMoney(Long senderId, Long recipientId, Double amount);
}
