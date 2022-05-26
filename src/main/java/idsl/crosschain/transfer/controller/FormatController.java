package idsl.crosschain.transfer.controller;

import idsl.crosschain.transfer.service.FormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/format")
public class FormatController {

    @Autowired
    private FormatService formatService;

    @GetMapping("/data-common")
    public ResponseEntity<?> getDataCommonFormat() {
        return new ResponseEntity<>(formatService.getDataCommonFormat(), HttpStatus.OK);
    }
}
