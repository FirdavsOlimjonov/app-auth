package uz.pdp.service;

import lombok.RequiredArgsConstructor;
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
import uz.pdp.repository.EmployeeRepository;
import uz.pdp.repository.RoleRepository;
import uz.pdp.repository.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> all = employeeRepository.findAll();
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Employee> getOne(UUID id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

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
    public ResponseEntity<Boolean> edit(Employee employee, UUID id) {

        if (employeeRepository.existsById(id)) {

            if (userRepository.existsByPhoneNumber(employee.getUser().getPhoneNumber()))
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();

            employeeRepository.save(employee);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @Override
    public ResponseEntity<Boolean> delete(UUID id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

