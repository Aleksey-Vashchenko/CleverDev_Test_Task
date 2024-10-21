package com.vashchenko.cleverdev_test_task.service;

import com.vashchenko.cleverdev_test_task.entity.Patient;
import com.vashchenko.cleverdev_test_task.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository repository;
    public Patient findByOldGuid(UUID guid) {
        return repository.findByOldClientGuids(guid.toString());
    }
}
