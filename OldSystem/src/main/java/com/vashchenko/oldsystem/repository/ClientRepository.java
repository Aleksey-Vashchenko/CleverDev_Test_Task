package com.vashchenko.oldsystem.repository;

import com.vashchenko.oldsystem.entity.Client;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ClientRepository {
    private List<Client> clients = new ArrayList<>();

    public List<Client> findAll(){
        List<Client> copyList = new ArrayList<>(clients.size());
        Collections.copy(copyList,clients);
        return copyList;
    }

    public void add(Client client) {
        clients.add(client);
    }

    public Integer getClientAmount(){
        return clients.size();
    }

    public Client getClientByIndex(Integer index){
        return clients.get(index);
    }
}
