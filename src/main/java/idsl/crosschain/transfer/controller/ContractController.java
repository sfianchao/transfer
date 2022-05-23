package idsl.crosschain.transfer.controller;

import idsl.crosschain.transfer.service.ContractService;
import idsl.crosschain.transfer.util.ContractUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract")
public class ContractController {

    private final ContractService contractService;
    private final ContractUtil contractUtil;

    @Autowired
    public ContractController(ContractService contractService,
                              ContractUtil contractUtil) {
        this.contractService = contractService;
        this.contractUtil = contractUtil;
    }

    @PostMapping("/deploy")
    public ResponseEntity<?> deployContract() {
        return new ResponseEntity<>("deploy success", HttpStatus.CREATED);
    }

    @PostMapping("/load")
    public ResponseEntity<?> loadContract() {
        return new ResponseEntity<>("load success", HttpStatus.CREATED);
    }

    @PostMapping("/status/set")
    public ResponseEntity<?> setTxStatus() {
        return new ResponseEntity<>(contractService.setTxStatus(), HttpStatus.CREATED);
    }

    @GetMapping("/status/get")
    public ResponseEntity<?> getTxStatus() {
        return new ResponseEntity<>(contractService.getTxStatus(), HttpStatus.OK);
    }

}
