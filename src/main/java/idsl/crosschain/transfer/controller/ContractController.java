package idsl.crosschain.transfer.controller;

import idsl.crosschain.transfer.util.ContractUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/contract")
public class ContractController {

    private final ContractUtil contractUtil;

    @Autowired
    public ContractController(ContractUtil contractUtil) {
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


}
