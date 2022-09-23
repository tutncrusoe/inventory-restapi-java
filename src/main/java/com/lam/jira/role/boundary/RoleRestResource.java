package com.lam.jira.role.boundary;


import com.lam.jira.common.util.ResponseHelper;
import com.lam.jira.role.dto.RoleDTO;
import com.lam.jira.role.model.Role;
import com.lam.jira.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleRestResource {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Object> findAllRoles() {
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
    public Object saveRole(@RequestBody RoleDTO roleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHelper.getErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
        }

        Role role = roleService.save(roleDTO);
        return ResponseHelper.getResponse(role, HttpStatus.CREATED);
    }

    @PutMapping("/{role-id}")
    public Object updateRole(@PathVariable("role-id") String id,
                             @RequestBody RoleDTO roleDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHelper.getErrorResponse(bindingResult, HttpStatus.BAD_REQUEST);
        }

        RoleDTO newRoleDTO = roleService.update(roleDTO, id);
        return ResponseHelper.getResponse(newRoleDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public Object deleteRole(@RequestParam("code") String code) {
        int result = roleService.delete(code);
        if (result == 1) {
            return ResponseHelper.getResponse("Deleted role successfully", HttpStatus.OK);
        } else {
            return ResponseHelper.getErrorResponse("Role is not exist with code: " + code, HttpStatus.BAD_REQUEST);
        }
    }
}
