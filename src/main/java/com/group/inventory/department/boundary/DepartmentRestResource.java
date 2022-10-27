package com.group.inventory.department.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.department.dto.CreateDepartmentDTO;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departments")
@Slf4j
public class DepartmentRestResource {
    private final DepartmentService departmentService;

    public DepartmentRestResource(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public Object findDepartments() {
        log.info("Get all departments");
        return ResponseHelper.getResponse(
                departmentService.findAllDTO(DepartmentDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/include-users")
    public Object findDepartmentsIncludeUsers() {
        log.info("Get all departments with include users");
        return ResponseHelper.getResponse(
                departmentService.findAllDepartmentWithUsers(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{department-id}")
    public Object findById(@PathVariable("department-id") UUID id) {
        log.info("Get department by id: {}", id);
        return ResponseHelper.getResponse(
                departmentService.findByIdDTO(id, DepartmentDTO.class),
                HttpStatus.OK
        );
    }

    @GetMapping("/{department-id}/include-users")
    public Object findByIdIncludeUsers(@PathVariable("department-id") UUID id) {
        log.info("Get department in include users by id: {}", id);
        return ResponseHelper.getResponse(
                departmentService.findDepartmentWithUsers(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public Object save(@RequestBody @Valid CreateDepartmentDTO createDepartmentDTO) {
        log.info("Create new department {}", createDepartmentDTO.getName());
        return ResponseHelper.getResponse(
                departmentService.save(createDepartmentDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{department-id}")
    public Object update(@PathVariable("department-id") UUID id,
                         @RequestBody @Valid DepartmentDTO departmentDTO
    ) {
        log.info("Update department by id: {}", id);
        return ResponseHelper.getResponse(
                departmentService.update(departmentDTO, id, DepartmentDTO.class),
                HttpStatus.OK
        );
    }

    @PostMapping("/{department-id}/add-users")
    public Object addUsersToDepartment(@PathVariable("department-id") UUID id,
                                       @RequestBody List<UUID> userIds
    ) {
        log.info("Add users to department id: {}", id);
        return ResponseHelper.getResponse(
                departmentService.addUsersToDepartment(id, userIds),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{department-id}/remove-users")
    public Object removeUsersFromDepartment(@PathVariable("department-id") UUID id,
                                            @RequestBody List<UUID> userIds
    ) {
        log.info("Remove users to department id: {}", id);
        return ResponseHelper.getResponse(
                departmentService.removeUsersFromDepartment(id, userIds),
                HttpStatus.OK
        );
    }
}
