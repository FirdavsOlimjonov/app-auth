package uz.pdp.service;

import uz.pdp.entity.PermissionEnum;
import uz.pdp.payload.AddRoleDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.RoleDTO;

import java.util.List;

public interface RoleService {

    ApiResult<RoleDTO> add(AddRoleDTO addRoleDTO);

    ApiResult<Boolean> delete(Integer id);

    ApiResult<List<RoleDTO>> getRoles();

    ApiResult<PermissionEnum[]> getPermissions();
}
