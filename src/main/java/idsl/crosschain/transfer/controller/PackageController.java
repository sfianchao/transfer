package idsl.crosschain.transfer.controller;

import idsl.crosschain.transfer.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping("/data-common/format")
    public ResponseEntity<?> getDataCommonFormat() {
        return new ResponseEntity<>(packageService.getDataCommonFormat(), HttpStatus.OK);
    }
}
