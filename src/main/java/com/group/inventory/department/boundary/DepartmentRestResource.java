package com.group.inventory.department.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.department.dto.DepartmentDTO;
import com.group.inventory.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/departments")
public class DepartmentRestResource {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Object findAllDepartments() {
        return ResponseHelper.getResponse(departmentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{role-id}")
    public Object findRoleById(@PathVariable("role-id") String id) {
        DepartmentDTO departmentDTO = departmentService.findById(UUID.fromString(id));
        if (departmentDTO == null) {
            return ResponseHelper.getErrorResponse("Role is not existed.", HttpStatus.BAD_REQUEST);
        }
        return ResponseHelper.getResponse(departmentDTO, HttpStatus.OK);
    }

    @PostMapping
    public Object saveRole(@Valid @RequestBody DepartmentDTO dto) {
        return ResponseHelper.getResponse(departmentService.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{role-id}")
    public Object updateRole(@PathVariable("role-id") String id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseHelper.getResponse(
                departmentService.update(departmentDTO, UUID.fromString(id)),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping
    public Object deleteRole(@RequestParam("id") String id) {
        departmentService.deleteById(UUID.fromString(id));
        return ResponseHelper.getResponse("Role is deleted successfully", HttpStatus.OK);
    }
}
