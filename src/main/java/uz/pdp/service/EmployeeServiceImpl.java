package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Employee;
import uz.pdp.entity.Role;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.AddEmployeeDTO;
import uz.pdp.payload.ApiResult;
import uz.pdp.payload.EmployeeDTO;
import uz.pdp.repository.EmployeeRepository;
import uz.pdp.repository.RoleRepository;
import uz.pdp.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final UserRepository userRepository;

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.defaultPassword}")
    private String defaultPassword;

    @Override
    public ApiResult<List<EmployeeDTO>> getAll() {
        List<Employee> all = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = mapEmployeesToEmployeeDTOList(all);

        return ApiResult.successResponse(employeeDTOList);
    }

    @Override
    public ApiResult<EmployeeDTO> getOne(UUID id) {
        Optional<Employee> employeeDTO = employeeRepository.findById(id);
        if (employeeDTO.isPresent()) {
            return ApiResult.successResponse(mapEmployeeToEmployeeDTO(employeeDTO.get()));
        }

        throw RestException.restThrow("id does not exist", HttpStatus.NOT_FOUND);
    }


    @Override
    public ApiResult<String> add(AddEmployeeDTO employeeDTO) {
        if (employeeRepository.existsByUser_PhoneNumber(employeeDTO.getPhoneNumber()))
            throw RestException.restThrow("Already exists", HttpStatus.CONFLICT);

        User user = userService
                .findByPhoneNumberIfNotCreate(
                        employeeDTO.getPhoneNumber(),
                        passwordEncoder.encode(defaultPassword));

        Role role = roleRepository
                .findById(employeeDTO.getRoleId())
                .orElseThrow(() ->
                        RestException.restThrow("Bunday role mavjud emas",
                                HttpStatus.BAD_REQUEST));

        Employee employee = new Employee(
                employeeDTO.getFirstName(),
                employeeDTO.getLastName(),
                user,
                role);
        employeeRepository.save(employee);
        return ApiResult.successResponse("Hammasi ok");
    }

    @Override
    public ApiResult<Boolean> edit(EmployeeDTO employeeDTO, UUID id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return ApiResult.successResponse(true);
    }


    @Override
    public ApiResult<String> delete(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
            return ApiResult.successResponse("deleted");
        }
        throw RestException.restThrow("id does not exist", HttpStatus.CONFLICT);
    }


    private List<EmployeeDTO> mapEmployeesToEmployeeDTOList(List<Employee> employees) {
        return
                employees
                        .stream()
                        .map(this::mapEmployeeToEmployeeDTO)
                        .collect(Collectors.toList());
    }

    private EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getUser().getPhoneNumber(),
                employee.getRole().getId()
        );
    }

}

