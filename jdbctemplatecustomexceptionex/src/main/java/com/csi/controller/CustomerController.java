package com.csi.controller;

import com.csi.model.Customer;
import com.csi.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Customer customer) {
        customerServiceImpl.signUp(customer);
        return ResponseEntity.ok("SignUp Done Successfully");
    }

    @GetMapping("/signin/{custEmailId}/{custPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String custEmailId, @PathVariable String custPassword) {
        return ResponseEntity.ok(customerServiceImpl.signIn(custEmailId, custPassword));
    }

    @GetMapping("/findbyid/{custId}")
    public ResponseEntity<Customer> findById(@PathVariable int custId) {
        return ResponseEntity.ok(customerServiceImpl.findById(custId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(customerServiceImpl.findAll());
    }

    @PutMapping("/update/{custId}")
    public ResponseEntity<String> update(@PathVariable int custId, @RequestBody Customer customer) {

        if (customerServiceImpl.findById(custId).getCustId() == custId) {
            customerServiceImpl.update(custId, customer);
        }

        return ResponseEntity.ok("Data Updated Successfully");
    }

    @PatchMapping("/changeaddress/{custId}/{custAddress}")
    public ResponseEntity<String> changeAddress(@PathVariable int custId, @PathVariable String custAddress) {
        if (customerServiceImpl.findById(custId).getCustId() == custId) {
            customerServiceImpl.changeAddress(custId, custAddress);
        }

        return ResponseEntity.ok("Address Changed Successfully");
    }

    @DeleteMapping("/deletebyid/{custId}")
    public ResponseEntity<String> deleteById(@PathVariable int custId) {
        if (customerServiceImpl.findById(custId).getCustId() == custId) {
            customerServiceImpl.deleteById(custId);
        }


        return ResponseEntity.ok("Data Deleted Successfully");
    }
}
