package com.adservio.reservation.web;

import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.exception.NotFoundException;
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
    public ResponseEntity<DepartmentDTO> findDepartmentById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(service.getById(id),HttpStatus.OK);
    }

    @GetMapping("/findNAME/{name}")
    public ResponseEntity<DepartmentDTO> findDepartmentByName(@PathVariable String name) {
        return new ResponseEntity<>(service.GetDepartmentByName(name),HttpStatus.OK);
    }
    @PostMapping("/save")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        DepartmentDTO newDepartment= service.save(departmentDTO);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PostMapping("/save/all")
    public List<DepartmentDTO> addDepartments(@RequestBody List<DepartmentDTO> departmentDTOS){
        return service.saveDepartments(departmentDTOS);
    }

    @PutMapping("/update/{id}")
    public DepartmentDTO updateDepartment(@PathVariable("id") Long departmentId,
                                       @RequestBody DepartmentDTO department) {
        return service.updateDepartment(departmentId,department);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        return service.deleteDepartment(id);
    }





}
