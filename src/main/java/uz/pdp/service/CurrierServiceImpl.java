package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Currier;
import uz.pdp.entity.Role;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.CurrierDTO;
import uz.pdp.payload.RoleDTO;
import uz.pdp.repository.CurrierRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrierServiceImpl implements CurrierService {
    private final CurrierRepository currierRepository;
    @Override
    public ApiResult<CurrierDTO> add(CurrierDTO currierDTO) {
        if(currierRepository.existsByCarNumber(currierDTO.getCarNumber()) || currierRepository.existsByDriverLicense(currierDTO.getDriverLicense())){
            throw RestException.restThrow("Such currier already exists", HttpStatus.CONFLICT);
        }
        Currier currier = mapToCurrier(currierDTO);
        currierRepository.save(currier);
        return ApiResult.successResponse(mapCurrierTOCurrierDto(currier));
    }

    @Override
    public ApiResult<Boolean> delete(UUID id) {
        currierRepository.deleteById(id);
        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<List<CurrierDTO>> getCurrierList() {
        List<Currier> currierList = currierRepository.findAll();
        List<CurrierDTO> currierDTOList = mapCurriersToCurrierDTOList(currierList);
        return ApiResult.successResponse(currierDTOList);
    }

    @Override
    public ApiResult<Boolean> edit(CurrierDTO currierDTO, UUID id) {
        Optional<Currier> optionalCurrier = currierRepository.findById(id);
        if (optionalCurrier.isPresent())
            return ApiResult.successResponse("exist currier");
        Currier currier = optionalCurrier.get();
        currier.setBirthDate(currierDTO.getBirthDate());
        currier.setFirstName(currierDTO.getFirstName());
        currier.setLastName(currierDTO.getLastName());
        currier.setCarNumber(currierDTO.getCarNumber());
        currier.setDriverLicense(currierDTO.getDriverLicense());
        currierRepository.save(currier);
        return ApiResult.successResponse("successfully edited");
    }

    @Override
    public ApiResult<CurrierDTO> getCurrier(UUID id) {
        Currier currier = currierRepository.findById(id)
                .orElseThrow(() -> RestException.restThrow(
                        "NO currier available", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(mapCurrierTOCurrierDto(currier));
    }

    public Currier mapToCurrier(CurrierDTO currierDTO) {
        return new Currier(currierDTO.getBirthDate(),
                currierDTO.getFirstName(),
                currierDTO.getLastName(),
                currierDTO.getCarNumber(),
                currierDTO.getDriverLicense()
        );
    }
    private CurrierDTO mapCurrierTOCurrierDto(Currier currier) {
        return new CurrierDTO(currier.getBirthDate(),
                currier.getFirstName(),
                currier.getLastName(),
                currier.getCarNumber(),
                currier.getDriverLicense()
        );
    }
    private List<CurrierDTO> mapCurriersToCurrierDTOList(List<Currier> currierList) {
        return
                currierList
                        .stream()
                        .map(this::mapCurrierTOCurrierDto)
                        .collect(Collectors.toList());
    }
}
