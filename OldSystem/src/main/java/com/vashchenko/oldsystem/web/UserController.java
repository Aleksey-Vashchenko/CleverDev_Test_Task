package com.vashchenko.oldsystem.web;

import com.vashchenko.oldsystem.entity.Client;
import com.vashchenko.oldsystem.repository.ClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Component
public class UserController {
    private final ClientRepository clientRepository;

    public UserController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping("/clients")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

}
