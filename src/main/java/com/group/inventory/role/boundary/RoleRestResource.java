package com.group.inventory.role.boundary;


import com.group.inventory.role.service.RoleService;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.role.dto.RoleDTO;
import com.group.inventory.role.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleRestResource {

    @Autowired
    private RoleService roleService;

    // 1. FindAll
    @GetMapping
    public Object findAllRoles() {
        List<RoleDTO> roles = roleService.findAllDTO();
        return ResponseHelper.getResponse(roles, HttpStatus.OK);
    }

    @GetMapping
    public Object findAllDTOPaging(@RequestParam("size") int size, @RequestParam("index") int index) {
        return ResponseHelper.getResponse(roleService.findAllDTO(Pageable.ofSize(size).withPage(index)), HttpStatus.OK);
    }

    // 2. FindBy
    @GetMapping("/{role-id}")
    public Object findRoleById(@PathVariable("role-id") String id) {
        RoleDTO roleDTO = roleService.findRoleById(id);
        if (roleDTO == null) {
            return ResponseHelper.getErrorResponse("Role is not existed.", HttpStatus.BAD_REQUEST);
        }
        return ResponseHelper.getResponse(roleDTO, HttpStatus.OK);
    }

    // 3. save
    @PostMapping
    public Object saveRoleDTO(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseHelper.getResponse(roleService.save(roleDTO), HttpStatus.CREATED);
    }

    // 4. update
    @PutMapping("/{role-id}")
    public Object updateRoleDTO(@PathVariable("role-id") String id, @Valid @RequestBody RoleDTO roleDTO) {
        return ResponseHelper.getResponse(roleService.update(roleDTO, id), HttpStatus.ACCEPTED);
    }

    // 4. delete
    @DeleteMapping
    public Object deleteRole(@RequestParam("id") String id) {
        roleService.delete(id);
        return ResponseHelper.getResponse("Role is deleted successfully", HttpStatus.OK);
    }

}
