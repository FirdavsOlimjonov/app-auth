package uz.pdp.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.entity.Role;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.add_DTO.AddRoleDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.RoleDTO;
import uz.pdp.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final ApiResult<PermissionEnum[]> apiResultAllPermissions =
            ApiResult.successResponse(PermissionEnum.values());

    @Override
    public ApiResult<RoleDTO> add(AddRoleDTO addRoleDTO) {
        if (roleRepository.existsByName(addRoleDTO.getName()))
            throw RestException.restThrow("Such role already exists", HttpStatus.CONFLICT);

        Role role = addRoleDTO.mapToRole();
        roleRepository.save(role);

        return ApiResult.successResponse(mapRoleToRoleDTO(role));
    }


    @Override
    public ApiResult<Boolean> delete(Integer id) {
        roleRepository.deleteById(id);
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<List<RoleDTO>> getRoles() {
        List<Role> all = roleRepository.findAll();
        List<RoleDTO> roleDTOList = mapLanguagesToLanguageDTOList(all);

        return ApiResult.successResponse(roleDTOList);
    }

    @Override
    public ApiResult<RoleDTO> getRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(
                        "NO_SUCH_ROLE", HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(new RoleDTO(role));
    }

    @Override
    public ApiResult<PermissionEnum[]> getPermissions() {
        return apiResultAllPermissions;
    }

    @Override
    public ApiResult<Boolean> edit(AddRoleDTO addRoleDTO, Integer id) {

        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isPresent())
            return ApiResult.successResponse("bu role mavjud");
        Role role = optionalRole.get();
        role.setName(addRoleDTO.getName());
        role.setDescription(addRoleDTO.getDescription());
        role.setPermissions(addRoleDTO.getPermissions());

        roleRepository.save(role);
        return ApiResult.successResponse("Ozgartirildi!");

    }

    private List<RoleDTO> mapLanguagesToLanguageDTOList(List<Role> roles) {
        return
                roles
                        .stream()
                        .map(this::mapRoleToRoleDTO)
                        .collect(Collectors.toList());
    }

    private RoleDTO mapRoleToRoleDTO(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getPermissions()
        );
    }
}
