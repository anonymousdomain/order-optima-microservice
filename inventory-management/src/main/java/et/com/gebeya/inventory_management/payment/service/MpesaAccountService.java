package et.com.gebeya.inventory_management.payment.service;

import et.com.gebeya.inventory_management.Models.UpdateRequest;
import et.com.gebeya.inventory_management.enums.Status;
import et.com.gebeya.inventory_management.exceptions.InsufficientBalanceException;
import et.com.gebeya.inventory_management.exceptions.ResourceNotFoundException;
import et.com.gebeya.inventory_management.payment.model.MpesaAccount;
import et.com.gebeya.inventory_management.payment.repository.MpesaAccountRepository;
import et.com.gebeya.inventory_management.payment.requestDto.*;
import et.com.gebeya.inventory_management.payment.responseDto.AccountCreationResponseDto;
import et.com.gebeya.inventory_management.payment.utility.MappingFunction;
import et.com.gebeya.inventory_management.repos.UpdateRequestRepository;
import et.com.gebeya.inventory_management.service.SmsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MpesaAccountService {
    private final MpesaAccountRepository mpesaAccountRepository;
    private UpdateRequestRepository updateRequestRepository;
    private final MappingFunction mappingFunction;
    private final SmsService smsService;


    public AccountCreationResponseDto createAccount(AccountCreationRequestDto requestDto) throws IOException {
        MpesaAccount account = mappingFunction.dtoToEntity(requestDto);
        MpesaAccount savedAccount = mpesaAccountRepository.save(account);
        String phoneNumber = requestDto.getPhoneNumber();
        String message = " CONGRATULATIONS ! : M-pesa account successfully created .";
        smsService.sendSms(phoneNumber, "e80ad9d8-adf3-463f-80f4-7c4b39f7f164", "", message);
        return mappingFunction.entityToDto(savedAccount);
    }

    public AccountCreationResponseDto getAccount(Long id) {
        MpesaAccount account = mpesaAccountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));
        return mappingFunction.entityToDto(account);
    }
    public void deleteAccount(Long id) {
        mpesaAccountRepository.deleteById(id);
    }
    public BalanceDto getCurrentBalance(String phoneNumber) {
        MpesaAccount account = mpesaAccountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with phone number: " + phoneNumber));
        BalanceDto balanceDto = new BalanceDto();
        balanceDto.setBalance(account.getBalance());
        return balanceDto;
    }
    public BalanceDto updateBalance(String phoneNumber, BalanceDto balanceDto) {
        MpesaAccount account = mpesaAccountRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with phone number: " + phoneNumber));
        Double updatedBalance = account.getBalance() + balanceDto.getBalance();
        account.setBalance(updatedBalance);
        mpesaAccountRepository.save(account);
        balanceDto.setBalance(updatedBalance);
        return balanceDto;
    }


    @Transactional
    public PaymentDto processVendorPayment(PaymentRequestDto requestDto) throws IOException {
        UpdateRequest updateRequest = updateRequestRepository.findById(requestDto.getUpdateRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("UpdateRequest not found"));
        String vendorPhoneNumber = updateRequest.getVendor().getPhoneNumber().get(0).getPhoneNumber();
        MpesaAccount vendorAccount = mpesaAccountRepository.findByPhoneNumber(vendorPhoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor M-Pesa account not found  " + vendorPhoneNumber));

        double paymentAmount = updateRequest.getProductQuantity() * updateRequest.getVendorProductPrice();
        MpesaAccount senderAccount = mpesaAccountRepository.findByPhoneNumber(requestDto.getSenderPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Sender M-Pesa account not found " + requestDto.getSenderPhoneNumber()));

        if (senderAccount.getBalance() < paymentAmount) {
            throw new InsufficientBalanceException("Insufficient balance in sender's account for this amount :" + paymentAmount);
        }
        senderAccount.setBalance(senderAccount.getBalance() - paymentAmount);
        mpesaAccountRepository.save(senderAccount);

        vendorAccount.setBalance(vendorAccount.getBalance() + paymentAmount);
        mpesaAccountRepository.save(vendorAccount);

        String message = " CONGRATULATIONS ! : Your get paid for your product: " +paymentAmount;
        smsService.sendSms(vendorPhoneNumber, "e80ad9d8-adf3-463f-80f4-7c4b39f7f164", "", message);
        return new PaymentDto(requestDto.getSenderPhoneNumber(), vendorPhoneNumber, paymentAmount, senderAccount.getBalance());
    }


}
