package com.cg.iba.controller;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    
    @InjectMocks
    TransactionController transactionController;
    
    @Mock
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    
    @Test
    void testCreateTransaction() {
        fail("Not yet implemented");
    }

    @Test
    void testViewTransaction() {
        fail("Not yet implemented");
    }

    @Test
    void testFindTransactionById() {
        fail("Not yet implemented");
    }

    @Test
    void testListAllTransactions() {
        fail("Not yet implemented");
    }

    @Test
    void testGetAllMyAccTransactions() {
        fail("Not yet implemented");
    }

}
