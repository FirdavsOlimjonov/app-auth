package uz.pdp.controller;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.UnicodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.entity.Employee;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.CurrierDTO;
import uz.pdp.service.CurrierServiceImpl;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CurrierControllerImpl implements CurrierController{
    private final CurrierServiceImpl currierService;
    @Override
    public ApiResult<List<CurrierDTO>> getAll() {
        return currierService.getCurrierList();
    }

    @Override
    public ApiResult<CurrierDTO> getOne(UUID id) {
        return currierService.getCurrier(id);
    }

    @Override
    public ApiResult<CurrierDTO> add(CurrierDTO currierDTO) {
        return currierService.add(currierDTO);
    }

    @Override
    public ApiResult<Boolean> edit(CurrierDTO currierDTO, UUID id) {
        return currierService.edit(currierDTO,id);
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        return currierService.delete(id);
    }
}
