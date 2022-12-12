package com.group.inventory.role.boundary;

import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.Role;
import com.group.inventory.role.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleRestResource {

    //    -----------------------       ATTRIBUTES       ----------------------
    private final RoleService roleService;

    //    -----------------------       CONSTRUCTOR       ----------------------
    public RoleRestResource(RoleService roleService) {
        this.roleService = roleService;
    }

    //    -----------------------       GET       ----------------------
    @GetMapping
    public Object findAllRoles() {
        return ResponseHelper.getResponse(roleService.findAllDTO(RoleDTO.class), HttpStatus.OK);
    }

    @GetMapping("/{role-id}")
    public Object findRoleById(@PathVariable("role-id") UUID roleId) {
        return ResponseHelper.getResponse(roleService.findByIdDTO(roleId, RoleDTO.class), HttpStatus.OK);
    }

    //    -----------------------       POST       ----------------------
    @PostMapping
    public Object saveRole(@RequestBody @Valid RoleDTO dto) {
        return ResponseHelper.getResponse(roleService.save(dto, Role.class, RoleDTO.class), HttpStatus.CREATED);
    }

    //    -----------------------       PUT       ----------------------
    @PutMapping("/{role-id}")
    public Object updateRole(@PathVariable("role-id") UUID roleId, @RequestBody @Valid RoleDTO dto) {
        return ResponseHelper.getResponse(roleService.update(dto, roleId, RoleDTO.class), HttpStatus.ACCEPTED);
    }
}
