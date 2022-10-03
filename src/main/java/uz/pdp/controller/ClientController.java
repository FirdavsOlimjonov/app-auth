package uz.pdp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.filterPayload.ClientDTO;
import uz.pdp.payload.filterPayload.ViewDTO;
import uz.pdp.utils.Pagination;

import java.util.List;

@RequestMapping(ClientController.CLIENT_CONTROLLER_BASE_PATH)
public interface ClientController {
    String CLIENT_CONTROLLER_BASE_PATH = "/api/client";

    String LIST_PATH = "/list";

    @PostMapping(LIST_PATH)
    ApiResult<List<ClientDTO>> getALl(@RequestBody(required = false) ViewDTO viewDTO,
                                     @RequestParam(defaultValue = Pagination.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(defaultValue = Pagination.DEFAULT_PAGE_SIZE) int size);
}