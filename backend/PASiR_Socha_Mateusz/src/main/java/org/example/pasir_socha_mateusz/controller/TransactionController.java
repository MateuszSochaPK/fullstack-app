package org.example.pasir_socha_mateusz.controller;
import jakarta.validation.Valid;
import org.example.pasir_socha_mateusz.dto.TransactionDTO;
import org.example.pasir_socha_mateusz.model.Transaction;
import org.example.pasir_socha_mateusz.repository.TransactionRepository;
import org.example.pasir_socha_mateusz.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Validated
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping
    public ResponseEntity<Transaction> saveTransactions(@Valid @RequestBody TransactionDTO transactionDetails){
        Transaction transaction = transactionService.saveTransactions(transactionDetails);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDetails){
        Transaction transaction = transactionService.updateTransaction(id, transactionDetails);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> removeTransaction(@PathVariable Long id){
        Transaction transaction = transactionService.removeTransaction(id);
        return ResponseEntity.ok(transaction);
    }

}
