package uz.pdp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.entity.Employee;
import uz.pdp.entity.User;
import uz.pdp.exceptions.RestException;
import uz.pdp.payload.ApiResult;
import uz.pdp.repository.EmployeeRepository;
import uz.pdp.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public User findByPhoneNumberIfNotCreate(String phoneNumber) {
        return findByPhoneNumberIfNotCreate(phoneNumber, null);
    }

    @Override
    public User findByPhoneNumberIfNotCreate(String phoneNumber, String password) {
        User user = Optional.of(userRepository
                        .findByPhoneNumber(phoneNumber)
                        .orElseGet(() -> userRepository.save(new User(phoneNumber))))
                .orElseThrow(() -> RestException.restThrow("Error in saving", HttpStatus.CONFLICT));

        return user;
    }

    @Override
    public ApiResult<User> getUserByToken() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Employee> optionalEmployee = employeeRepository.findByUserId(user.getId());

        //todo userdto shuncha data db dan emas cache dan ol
        return ApiResult.successResponse(user);
    }
}
