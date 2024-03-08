package com.csi.dao;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String INSERT_SQL = "insert into customer(custid, custname, custaddress, custaccbalance, custcontactnumber, custdob, custemailid, custpassword)values(?, ?, ?, ?, ?, ?, ?, ?)";

    String SELECT_SQL_BY_ID = "select * from customer where custid=?";

    String SELECT_ALL_SQL = "select * from customer";

    String UPDATE_SQL = "update customer set custname=?, custaddress=?, custaccbalance=?, custcontactnumber=?, custdob=?, custemailid=?, custpassword=? where custid=?";

    String PATCH_SQL = "update customer set custaddress=? where custid=?";

    String DELETE_SQL = "delete from customer where custid=?";

    private Customer customer(ResultSet resultSet, int numRow) throws SQLException {

        return Customer.builder().custId(resultSet.getInt(1)).custName(resultSet.getString(2)).custAddress(resultSet.getString(3)).custAccBalance(resultSet.getDouble(4)).custContactNumber(resultSet.getLong(5)).custDOB(resultSet.getDate(6)).custEmailId(resultSet.getString(7)).custPassword(resultSet.getString(8)).build();
    }

    @Override
    public void signUp(Customer customer) {

        jdbcTemplate.update(INSERT_SQL, customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustAccBalance(), customer.getCustContactNumber(), customer.getCustDOB(), customer.getCustEmailId(), customer.getCustPassword());

    }

    @Override
    public boolean signIn(String custEmailId, String custPassword) {

        boolean flag = false;

        for (Customer customer : findAll()) {
            if (customer.getCustEmailId().equals(custEmailId) && customer.getCustPassword().equals(custPassword)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Customer findById(int custId) {

        try{
            return jdbcTemplate.query(SELECT_SQL_BY_ID, this::customer, custId).get(0);
        }catch (Exception exception){
            throw new RecordNotFoundException("Customer ID Does Not Exist");
        }


    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, this::customer);
    }

    @Override
    public void update(int custId, Customer customer) {
        jdbcTemplate.update(UPDATE_SQL, customer.getCustName(), customer.getCustAddress(), customer.getCustAccBalance(), customer.getCustContactNumber(), customer.getCustDOB(), customer.getCustEmailId(), customer.getCustPassword(), custId);

    }

    @Override
    public void changeAddress(int custId, String custAddress) {

        jdbcTemplate.update(PATCH_SQL, custAddress, custId);
    }

    @Override
    public void deleteById(int custId) {
        jdbcTemplate.update(DELETE_SQL, custId);
    }
}
