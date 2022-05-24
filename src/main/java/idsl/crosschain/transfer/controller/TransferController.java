package idsl.crosschain.transfer.controller;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.dto.NotifyRequest;
import idsl.crosschain.transfer.dto.SendRequest;
import idsl.crosschain.transfer.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @MessageMapping("/service")
    @SendTo("/topic/tx")
    public JSONObject ws(@Payload String string) throws Exception {
        return new JSONObject(Map.of("msg", string));
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendTx(@RequestBody SendRequest sendRequest) {
        return new ResponseEntity<>(transferService.sendTx(sendRequest), HttpStatus.CREATED);
    }

    @PostMapping("/status/notify")
    public ResponseEntity<?> notifyTxState(@RequestBody NotifyRequest notifyRequest) {
        return new ResponseEntity<>(transferService.notifyTxState(notifyRequest), HttpStatus.CREATED);
    }


}
