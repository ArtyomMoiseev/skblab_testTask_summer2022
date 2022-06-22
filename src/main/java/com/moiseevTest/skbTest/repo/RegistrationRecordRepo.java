package com.moiseevTest.skbTest.repo;

import com.moiseevTest.skbTest.model.RegistrationRecord;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRecordRepo extends CrudRepository<RegistrationRecord, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
