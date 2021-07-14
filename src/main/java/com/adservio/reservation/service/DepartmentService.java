package com.adservio.reservation.service;

import com.adservio.reservation.dao.DepartmentRepository;
import com.adservio.reservation.entities.Department;
import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.mapper.DepartmentConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentConvert converter;


    public List<DepartmentDTO> listAll(){

        return converter.entityToDto(departmentRepository.findAll());
    }

    public DepartmentDTO getById(Long id){

        return converter.entityToDto(departmentRepository.findById(id).orElse(null));
    }

    public DepartmentDTO GetDepartmentByName(String name) {
        return converter.entityToDto(departmentRepository.findByName(name));
    }

    public DepartmentDTO save(DepartmentDTO departmentDTO){
        Department department=converter.dtoToEntity(departmentDTO);
        department=departmentRepository.save(department);
        return converter.entityToDto(department);
    }

    public List<DepartmentDTO> saveDepartments(List<DepartmentDTO> departmentDTOS){
        List<Department> departments=converter.dtoToEntity(departmentDTOS);
        departments=departmentRepository.saveAll(departments);
        return converter.entityToDto(departments);

    }

    public String deleteDepartment(Long id){
        departmentRepository.deleteById(id);
        return "Deleted successfully";
    }

}
