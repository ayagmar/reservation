package com.adservio.reservation.web;

import com.adservio.reservation.entities.Department;
import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentRestController {
    @Autowired
    DepartmentService service;
    @GetMapping("/departments")
    public List<DepartmentDTO> findAllRooms(){
        return service.listAll();
    }

    @GetMapping("/department/{id}")
    public DepartmentDTO findDepartmentById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/department/{name}")
    public DepartmentDTO findDepartmentByName(@PathVariable String name) {
        return service.GetDepartmentByName(name);
    }
    @PostMapping("/department/add")
    public DepartmentDTO addDepartment(@RequestBody DepartmentDTO departmentDTO){
        return service.save(departmentDTO);
    }

    @PostMapping("/department/addall")
    public List<DepartmentDTO> addDepartments(@RequestBody List<DepartmentDTO> departmentDTOS){
        return service.saveDepartments(departmentDTOS);
    }

    @DeleteMapping("/department/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        return service.deleteDepartment(id);
    }
    /*
    @PutMapping("/department/update")
    public Department updateRoom(@RequestBody Department department) {
        return service.updateDepartment(department);
    }
*/



}
