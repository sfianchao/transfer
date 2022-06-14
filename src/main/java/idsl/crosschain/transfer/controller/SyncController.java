package idsl.crosschain.transfer.controller;

import idsl.crosschain.transfer.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    @PostMapping("/status/check")
    public ResponseEntity<?> checkTxStatus() {
        return new ResponseEntity<>(syncService.checkTxStatus(), HttpStatus.CREATED);
    }

    @GetMapping("/relay/status/{chainName}")
    public ResponseEntity<?> getTxStatus(@PathVariable String chainName) {
        return new ResponseEntity<>(syncService.getTxStatus(chainName), HttpStatus.OK);
    }
}
