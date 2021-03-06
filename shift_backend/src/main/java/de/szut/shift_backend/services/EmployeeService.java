package de.szut.shift_backend.services;

import de.szut.shift_backend.exceptionHandling.CreationException;
import de.szut.shift_backend.exceptionHandling.PasswordMissmatch;
import de.szut.shift_backend.exceptionHandling.ResourceNotFoundException;
import de.szut.shift_backend.helper.ClassReflectionHelper;
import de.szut.shift_backend.model.Department;
import de.szut.shift_backend.model.Employee;
import de.szut.shift_backend.model.Holiday;
import de.szut.shift_backend.model.ShiftType;
import de.szut.shift_backend.model.dto.UpdatePasswordDto;
import de.szut.shift_backend.repository.EmployeeRepository;
import de.szut.shift_backend.templates.KeycloakInteractionService;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.JsonWebToken;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final ShiftTypeService shiftTypeService;
    private final KeycloakInteractionService keyService;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentService departmentService, ShiftTypeService shiftTypeService, KeycloakInteractionService keyService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.shiftTypeService = shiftTypeService;
        this.keyService = keyService;
    }

    public Employee create(Employee newEmployee){
        try{
            this.keyService.addUserToKeycloak(newEmployee);
            return employeeRepository.save(newEmployee);
        } catch (Exception e){
            throw new CreationException(e.getMessage());
        }
    }

    public void save(Employee newEmployee){
        this.employeeRepository.save(newEmployee);
    }

    public Employee getEmployeeById(Long empID){
        Optional<Employee> emp = employeeRepository.findById(empID);

        if (emp.isEmpty())
            throw new ResourceNotFoundException("Employee with id '"+ empID + "' could not be found");

        return emp.get();
    }

    public Employee getEmployeeByUsername(String username){
        Employee emp = employeeRepository.findByUsername(username);

        if (emp == null)
            throw new ResourceNotFoundException("Employee with username: '"+ username + "' could not be found");

        return emp;
    }

    public Department getDepartmentByEmployeeId(Long employeeId) {
        Employee emp = this.getEmployeeById(employeeId);

        return departmentService.getDepartmentById(emp.getDepartment().getDepartmentId());
    }

    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }

    public void deleteEmployeeById(Long employeeId){

        if (!this.employeeRepository.existsById(employeeId))
            throw new ResourceNotFoundException("Employee with id '"+ employeeId + "' could not be found");

        this.employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Long employeeId, Map<String, Object> empUpdate) throws ConstraintViolationException{
        Employee emp = this.getEmployeeById(employeeId);

        if (empUpdate.containsKey("preferredShiftType")){
            if (empUpdate.get("preferredShiftType") == null)
                emp.setPreferredShiftType(null);
            else {
                Integer prefType = (Integer) empUpdate.get("preferredShiftType");

                ShiftType st = this.shiftTypeService.getShiftTypeById(prefType.longValue());
                emp.setPreferredShiftType(st);
            }

            empUpdate.remove("preferredShiftType");
        }

        if(empUpdate.containsKey("departmentId")){
            if (empUpdate.get("departmentId") == null)
                emp.setDepartment(null);
            else {
                Integer deptId = (Integer) empUpdate.get("departmentId");

                Department dept = this.departmentService.getDepartmentById(deptId.longValue());
                emp.setDepartment(dept);
            }

            emp.setPreferredShiftType(null);
            empUpdate.remove("departmentId");
        }

        Employee empUpdated = ClassReflectionHelper.UpdateFields(emp, empUpdate);

        this.employeeRepository.save(empUpdated);

        return empUpdated;
    }

    public List<Employee> getAllById(List<Long> employeeIdList) {
        return this.employeeRepository.findAllById(employeeIdList);
    }

    public void updateEmployeePassword(String token, UpdatePasswordDto passwordDto) throws VerificationException {
        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword()))
            throw new PasswordMissmatch("Provided passwords do not match");

        String token2 = token.substring(7);
        JsonWebToken decodedT = TokenVerifier.create(token2, AccessToken.class).getToken();

        this.keyService.updateUserPassword(decodedT.getSubject(), passwordDto);
    }

    public void updateEmployeePasswordAsAdmin(String token,Long employeeId, UpdatePasswordDto passwordDto) throws VerificationException {
        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword()))
            throw new PasswordMissmatch("Provided passwords do not match");

        String token2 = token.substring(7);
        AccessToken decodedT = TokenVerifier.create(token2, AccessToken.class).getToken();

        if(decodedT.getRealmAccess().getRoles().contains("shiftadmin")){
            Employee emp = this.getEmployeeById(employeeId);
            String subjectId = this.keyService.getSubjectIdByUsername(emp.getUsername());

            this.keyService.updateUserPassword(subjectId, passwordDto);
        }
    }

    public void changeEmployeeRole(Long empId, Employee.EMPLOYEE_ROLE role){
        Employee emp = this.getEmployeeById(empId);

        this.keyService.changeUserRole(emp, role);

    }
}
