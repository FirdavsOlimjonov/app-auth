package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import uz.pdp.entity.Client;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.response_DTO.ClientDTO;
import uz.pdp.service.ProfileService;

@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController{
    private final ProfileService profileService;


    @Override
    public ApiResult<Client> getClientProfile(String token) {
        return null;
    }

    @Override
    public ApiResult<Client> editClientProfile(ClientDTO clientDTO) {
        return profileService.editClientProfile(clientDTO);
    }
}
