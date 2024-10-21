package com.vashchenko.cleverdev_test_task.repository;

import com.vashchenko.cleverdev_test_task.entity.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient,Long> {
    @Query("SELECT p FROM Patient p WHERE p.oldClientGuids LIKE %:oldClientGuid%")
    Patient findByOldClientGuids(@Param("oldClientGuid") String oldClientGuid);
}
