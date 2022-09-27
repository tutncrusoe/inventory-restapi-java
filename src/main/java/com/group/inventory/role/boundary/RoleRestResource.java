package com.group.inventory.role.boundary;


import com.group.inventory.role.service.RoleService;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleRestResource {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public Object findAllRoles() {
        List<RoleDTO> roles = roleService.findAll();
        return ResponseHelper.getResponse(roles, HttpStatus.OK);
    }

    @GetMapping("/{role-id}")
    public Object findRoleById(@PathVariable("role-id") String id) {
        RoleDTO roleDTO = roleService.findRoleById(id);
        if (roleDTO == null) {
            return ResponseHelper.getErrorResponse("Role is not existed.", HttpStatus.BAD_REQUEST);
        }
        return ResponseHelper.getResponse(roleDTO, HttpStatus.OK);
    }

    @PostMapping
    public Object saveRole(@Valid @RequestBody RoleDTO roleDTO) {
        Role role = roleService.save(roleDTO);
        return ResponseHelper.getResponse(role, HttpStatus.CREATED);
    }

    @PutMapping("/{role-id}")
    public Object updateRole(@PathVariable("role-id") String id, @Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO newRoleDTO = roleService.update(roleDTO, id);
        return ResponseHelper.getResponse(newRoleDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public Object deleteRole(@RequestParam("id") String id) {
        roleService.delete(id);
        return ResponseHelper.getResponse("Role is deleted successfully", HttpStatus.OK);
    }
}
