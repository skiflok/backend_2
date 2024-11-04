package com.edu.backend.controller;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.ClientDto;
import com.edu.backend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ClientController", description = "Контроллер для работы с клиентами")
@RestController()
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/client",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClientController {

    private final ClientService service;

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Получение клиента по id",
            description = "Получение клиента по id"
    )
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getClient(
            @PathVariable Long id
    ) {
        return service.getClient(id);
    }

    @PostMapping(
            value = "/add",
            consumes = "application/json"
    )
    @Operation(
            summary = "Добавление клиента",
            description = "Добавление клиента (на вход подается json, соответствующей структуре, описанной сверху)."
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveClient(
            @Valid @RequestBody ClientDto clientDto
    ) {
        service.saveClient(clientDto);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Удаление клиента",
            description = "Удаление клиента (по его идентификатору)"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
    }

    @GetMapping(
            value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Получение клиентов по имени и фамилии",
            description = "Получение клиентов по имени и фамилии (параметры - имя и фамилия)"
    )
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getClientsByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname
    ) {
        return service.findClientsByNameAndSurname(name, surname);
    }

    @GetMapping(
            value = "/all/pageable",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Получение всех клиентов",
            description = """
                    В данном запросе необходимо предусмотреть опциональные параметры пагинации в строке запроса:
                    limit и offset
                    В случае отсутствия эти параметров возвращать весь список.
                    """
    )
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientDto> getAllClientsPageable(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        return service.getAllClientsPageable(limit, offset);
    }

    @PatchMapping(
            value = "/{id}/change-address",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAddress(
            @PathVariable Long id,
            @RequestBody @Valid AddressDto addressDto
    ) {
        service.changeAddress(id, addressDto);
    }
}
