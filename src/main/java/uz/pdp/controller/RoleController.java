package uz.pdp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.PermissionEnum;
import uz.pdp.payload.AddRoleDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.RoleDTO;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(path = RoleController.ROLE_BASE_PATH)
public interface RoleController {
    String ROLE_BASE_PATH = "/api/role";

    @PostMapping
    ApiResult<RoleDTO> add(@Valid @RequestBody AddRoleDTO addRoleDTO);

    @DeleteMapping("/{id}")
    ApiResult<Boolean> delete(@PathVariable Integer id);

    @GetMapping("/list")
    ApiResult<List<RoleDTO>> getRoles();

    @GetMapping("permissions-for-role")
    ApiResult<PermissionEnum[]> getPermissions();

}
