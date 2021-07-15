package com.adservio.reservation.web;

import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentRestController {
    final
    DepartmentService service;
    public DepartmentRestController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> findAllDepartments(){
        return new ResponseEntity<>(service.listAll(),HttpStatus.OK);
    }

    @GetMapping("/findID/{id}")
    public ResponseEntity<DepartmentDTO> findDepartmentById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
    }

    @GetMapping("/findNAME/{name}")
    public ResponseEntity<DepartmentDTO> findDepartmentByName(@PathVariable String name) {
        return new ResponseEntity<>(service.GetDepartmentByName(name),HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        DepartmentDTO newDepartment= service.save(departmentDTO);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PostMapping("/addall")
    public List<DepartmentDTO> addDepartments(@RequestBody List<DepartmentDTO> departmentDTOS){
        return service.saveDepartments(departmentDTOS);
    }

    @DeleteMapping("/delete/{id}")
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
