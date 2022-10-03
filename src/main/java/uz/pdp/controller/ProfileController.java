package uz.pdp.controller;

import org.apache.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.ClientDTO;
import uz.pdp.service.ProfileService;

/**
 * Profil API. [til, telefon, ism, tugulgan kun, maznilini] ozgartir olish
 * Tilni o'zgartirish figmada yo'qku i bizni app miz hozircha bitta tilga mo'ljallanganku
 * Telefon raqami USER entity da
 * Ismi, tug'ilgan kuni CLIENT entity da
 * Manzilni qayerdan olishni bilmayman :(
 */
@RequestMapping(path = ProfileController.PROFILE_CONTROLLER_BASE_PATH)
public interface ProfileController {


    String PROFILE_CONTROLLER_BASE_PATH = "/api/profile";

    String READ_PATH = "/read-profile";

    String EDIT_PATH = "/edit-profile";

    @GetMapping(value = READ_PATH)
    ApiResult<Client> getClientProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);


    @PutMapping(value = EDIT_PATH)
    ApiResult<Client> editClientProfile(ClientDTO clientDTO);


}
