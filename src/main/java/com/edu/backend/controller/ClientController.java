package com.edu.backend.controller;

import com.edu.backend.entity.Client;
import com.edu.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1//clients")
public class ClientController {

    private final ClientService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Client getClient(@PathVariable Long id) {
        return service.getClient(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveClient(@RequestBody Client client) {
        service.saveClient(client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getClientsByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname) {
        return service.findClientsByNameAndSurname(name, surname);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAllClientsPageable(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {

        return service.getAllClientsPageable(limit, offset);
    }


}


//Удаление клиента (по его идентификатору)
//Получение клиентов по имени и фамилии (параметры - имя и фамилия)
//Получение всех клиентов (В данном запросе необходимо предусмотреть опциональные параметры пагинации в строке запроса: limit и offset).
// В случае отсутствия эти параметров возвращать весь список.
//Изменение адреса клиента (параметры: Id и новый адрес в виде json в соответствии с выше описанным форматом)