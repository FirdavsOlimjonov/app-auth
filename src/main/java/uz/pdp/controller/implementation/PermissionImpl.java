package uz.pdp.controller.implementation;

import uz.pdp.entity.PermissionEnum;
import uz.pdp.payload.ApiResult;

public class PermissionImpl implements PermissionController {
    private final ApiResult<PermissionEnum[]> apiResultAllPermissions =
            ApiResult.successResponse(PermissionEnum.values());

    @Override
    public ApiResult<PermissionEnum[]> getAllPermissions() {
        return apiResultAllPermissions;
    }

}
