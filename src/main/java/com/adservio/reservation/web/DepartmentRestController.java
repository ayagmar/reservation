package com.adservio.reservation.web;

import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/department")
public class DepartmentRestController {
   private final
    DepartmentService service;

    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> findAllDepartments(){
        return ResponseEntity.ok().body(service.listAll());
    }

    @GetMapping("/findID/{id}")
    public ResponseEntity<DepartmentDTO> findDepartmentById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(service.getById(id));
    }

    @GetMapping("/findNAME/{name}")
    public ResponseEntity<DepartmentDTO> findDepartmentByName(@PathVariable String name) {

        return ResponseEntity.ok().body(service.GetDepartmentByName(name));
    }
    @PostMapping("/save")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO departmentDTO){
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/save").toUriString());
        return  ResponseEntity.created(uri).body(service.save(departmentDTO));
    }

    @PostMapping("/save/all")
    public ResponseEntity<List<DepartmentDTO>> addDepartments(@RequestBody List<DepartmentDTO> departmentDTOS){
    URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/save/all").toUriString());
        return ResponseEntity.created(uri).body(service.saveDepartments(departmentDTOS));
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
