package com.csi.service;

import com.csi.model.Customer;

import java.util.List;

public interface CustomerService {
    void signUp(Customer customer);

    boolean signIn(String custEmailId, String custPassword);

    Customer findById(int custId);

    List<Customer> findAll();

    void update(int custId, Customer customer);

    void changeAddress(int custId, String custAddress);

    void deleteById(int custId);

}
