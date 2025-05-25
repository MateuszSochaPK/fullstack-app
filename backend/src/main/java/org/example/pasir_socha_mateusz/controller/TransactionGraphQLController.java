package org.example.pasir_socha_mateusz.controller;

import jakarta.validation.Valid;
import org.example.pasir_socha_mateusz.dto.BalanceDto;
import org.example.pasir_socha_mateusz.dto.TransactionDTO;
import org.example.pasir_socha_mateusz.model.Transaction;
import org.example.pasir_socha_mateusz.model.User;
import org.example.pasir_socha_mateusz.service.TransactionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TransactionGraphQLController {
    private final TransactionService transactionService;
    public TransactionGraphQLController(TransactionService transactionService){
        this.transactionService=transactionService;
    }
    @QueryMapping
    public List<Transaction> transactions(){
        return transactionService.getAllTransactions();
    }
    @MutationMapping
    public Transaction addTransaction(@Valid @Argument TransactionDTO transactionDTO){
        return transactionService.saveTransactions(transactionDTO);
    }
    @MutationMapping
    public Transaction updateTransaction(
            @Argument Long id,
            @Valid @Argument TransactionDTO transactionDTO
    ){
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @MutationMapping
    public Boolean deleteTransaction(@Argument Long id) {
        transactionService.removeTransaction(id);
        return true;
    }
    @QueryMapping
    public BalanceDto userBalance(@Argument Float days){
        User user = transactionService.getCurrentUser();
        return transactionService.getUserBalance(user,days);
    }
}
