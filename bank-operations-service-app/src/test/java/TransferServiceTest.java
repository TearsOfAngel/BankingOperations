import ru.vcarstein.dto.BankAccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.vcarstein.entity.BankAccount;
import ru.vcarstein.exception.TransferException;
import ru.vcarstein.mapper.BankAccountMapper;
import ru.vcarstein.repository.BankAccountRepository;
import ru.vcarstein.service.impl.TransferServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransferServiceTest {
    @Mock
    private BankAccountRepository accountRepository;

    @Mock
    private BankAccountMapper bankAccountMapper;

    @InjectMocks
    private TransferServiceImpl transferService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTransferMoneySuccess() throws TransferException {
        Long senderId = 1L;
        Long recipientId = 2L;
        Double amount = 50.0;

        BankAccount senderAccount = new BankAccount();
        senderAccount.setId(senderId);
        senderAccount.setBalance(100.0);

        BankAccount recipientAccount = new BankAccount();
        recipientAccount.setId(recipientId);
        recipientAccount.setBalance(50.0);

        BankAccountDTO senderDTO = new BankAccountDTO();
        senderDTO.setBalance(100.0);

        BankAccountDTO recipientDTO = new BankAccountDTO();
        recipientDTO.setBalance(50.0);

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipientAccount));
        when(bankAccountMapper.fromEntityToDTO(senderAccount)).thenReturn(senderDTO);
        when(bankAccountMapper.fromEntityToDTO(recipientAccount)).thenReturn(recipientDTO);

        transferService.transferMoney(senderId, recipientId, amount);

        assertEquals(50.0, senderAccount.getBalance());
        assertEquals(100.0, recipientAccount.getBalance());

        verify(accountRepository, times(1)).save(senderAccount);
        verify(accountRepository, times(1)).save(recipientAccount);
    }

    @Test
    public void testTransferMoneyInsufficientFunds() {
        Long senderId = 1L;
        Long recipientId = 2L;
        Double amount = 150.0;

        BankAccount senderAccount = new BankAccount();
        senderAccount.setId(senderId);
        senderAccount.setBalance(100.0);

        BankAccount recipientAccount = new BankAccount();
        recipientAccount.setId(recipientId);
        recipientAccount.setBalance(50.0);

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(recipientId)).thenReturn(Optional.of(recipientAccount));

        TransferException exception = assertThrows(TransferException.class, () -> {
            transferService.transferMoney(senderId, recipientId, amount);
        });

        assertEquals("Insufficient funds", exception.getMessage());

        verify(accountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    public void testTransferMoneySenderNotFound() {
        Long senderId = 1L;
        Long recipientId = 2L;
        Double amount = 50.0;

        when(accountRepository.findById(senderId)).thenReturn(Optional.empty());

        TransferException exception = assertThrows(TransferException.class, () -> {
            transferService.transferMoney(senderId, recipientId, amount);
        });

        assertEquals("Sender account not found", exception.getMessage());

        verify(accountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    public void testTransferMoneyRecipientNotFound() {
        Long senderId = 1L;
        Long recipientId = 2L;
        Double amount = 50.0;

        BankAccount senderAccount = new BankAccount();
        senderAccount.setId(senderId);
        senderAccount.setBalance(100.0);

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(senderAccount));
        when(accountRepository.findById(recipientId)).thenReturn(Optional.empty());

        TransferException exception = assertThrows(TransferException.class, () -> {
            transferService.transferMoney(senderId, recipientId, amount);
        });

        assertEquals("Recipient account not found", exception.getMessage());

        verify(accountRepository, never()).save(any(BankAccount.class));
    }
}
