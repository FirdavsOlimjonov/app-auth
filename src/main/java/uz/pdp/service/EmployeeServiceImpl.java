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

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<List<EmployeeDTO>> getAll() {
//        List<Employee> all = employeeRepository.findAll();
//        List<EmployeeDTO> list = new ArrayList<>();
//        for (Employee employee : all) {
//            EmployeeDTO employeeDTO = new EmployeeDTO();
//            employeeDTO.setFirstName(employee.getFirstName());
//            employeeDTO.setLastName(employee.getLastName());
//            employeeDTO.setRole_String(employee.getRole_String());
//            employeeDTO.setPhoneNumber(employee.getFirstName());
//
//            list.add(employeeDTO);
//
//            return ResponseEntity.ok(list);
//        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<EmployeeDTO> getOne(Integer id) {
//        Optional<Employee> employeeDTO = employeeRepository.findById(id);
//        if(employeeDTO.isPresent()){
//            Employee employee = employeeDTO.get();
//            EmployeeDTO employeeDTO1 = new EmployeeDTO();
//            employeeDTO1.setFirstName(employee.getFirstName());
//            employeeDTO1.setMiddleName(employee.getMiddleName());
//            employeeDTO1.setLastName(employee.getLastName());
//            employeeDTO1.setPhoneNumber(employee.getPhoneNumber());
//            employeeDTO1.setRole_String(employee.getRole_String());
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
//        if (employeeRepository.existsByPhoneNumber(employee.getPhoneNumber())) {
//            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
//        }
//        employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Boolean> edit(EmployeeDTO employeeDTO, Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> delete(Integer id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
