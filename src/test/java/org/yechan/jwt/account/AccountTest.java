package org.yechan.jwt.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AccountTest {
    @Autowired
    private AccountRepository accountRepository;
    @Test
    public void testDatabaseConnection() {
        Account sampleEntity = new Account();
        sampleEntity.setUsername("test");
        accountRepository.save(sampleEntity);

        List<Account> entities = accountRepository.findAll();
        assertFalse(entities.isEmpty(), "The entity list should not be empty");

        Account retrievedEntity = entities.get(0);
        assertEquals("test", retrievedEntity.getUsername(), "The name of the entity should match");
    }
}