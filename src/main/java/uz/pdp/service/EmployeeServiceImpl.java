package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Employee;
import uz.pdp.payload.EmployeeDTO;
import uz.pdp.repository.EmployeeRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> all = employeeRepository.findAll();

//        List<EmployeeDTO> list = new ArrayList<>();
//
//        for (Employee employee : all) {
//
//            EmployeeDTO employeeDTO = new EmployeeDTO();
//            employeeDTO.setFirstName(employee.getUser().getFirstName());
//            employeeDTO.setLastName(employee.getUser().getLastName());
//            employeeDTO.setRole(employee.getRole());
//            employeeDTO.setMiddleName(employee.getUser().getMiddleName());
//            employeeDTO.setPhoneNumber(employee.getUser().getPhoneNumber());
//
//            list.add(employeeDTO);
//
//            return ResponseEntity.ok(list);
//        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Employee> getOne(UUID id) {
//        Optional<Employee> employeeDTO = employeeRepository.findEmployeeByUserId(id);
//        if (employeeDTO.isPresent()) {
//            Employee employee = employeeDTO.get();
//            EmployeeDTO employeeDTO1 = new EmployeeDTO();
//            employeeDTO1.setFirstName(employee.getUser().getFirstName());
//            employeeDTO1.setLastName(employee.getUser().getLastName());
//            employeeDTO1.setRole(employee.getRole());
//            employeeDTO1.setMiddleName(employee.getUser().getMiddleName());
//            employeeDTO1.setPhoneNumber(employee.getUser().getPhoneNumber());
//            return ResponseEntity.ok(employeeDTO1);
//        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @Override
//    public ResponseEntity<Boolean> add(EmployeeDTO employeeDTO) {
//        if (employeeRepository.existsByPhoneNumber(employeeDTO.getPhoneNumber())) {
//            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
//        }
//
//        Employee employee = new Employee();
//
//        employee.setFirstName(employeeDTO.getFirstName());
//        employee.setLastName(employeeDTO.getLastName());
//        employee.setMiddleName(employeeDTO.getMiddleName());
//        employee.setRole_String(employeeDTO.getRole_String());
//        employee.setPhoneNumber(employeeDTO.getPhoneNumber());
//
//        employeeRepository.save(employee);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//    }

    @Override
    public ResponseEntity<Boolean> add(Employee employee) {
        if (employeeRepository.existsByUserId(employee.getUser().getId())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }
        employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Boolean> edit(Employee employee, UUID id) {
//        if (employeeRepository.existsById(id)) {
//
//            List<Employee> all = employeeRepository.findAll();
//
//            for (Employee employee1 : all) {
//                if (employee1.getUser().getId().equals(employee.getUser().getId())) {
//                    if (employee1.getUser().getPhoneNumber().equals(employee.getUser().getPhoneNumber())) {
//                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
//                    }
//
//                    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//
//                }
//
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//            }
//        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @Override
    public ResponseEntity<Boolean> delete(UUID id) {
//        if (employeeRepository.existsById(id)) {
//            employeeRepository.deleteById(id);
//            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//        }
//
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

