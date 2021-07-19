package com.adservio.reservation.service;

import com.adservio.reservation.dao.DepartmentRepository;
import com.adservio.reservation.entities.Department;
import com.adservio.reservation.entities.dto.DepartmentDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.DepartmentConvert;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService {
    final
    DepartmentRepository departmentRepository;
    final
    DepartmentConvert converter;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentConvert converter) {
        this.departmentRepository = departmentRepository;
        this.converter = converter;
    }


    public List<DepartmentDTO> listAll(){

        return converter.entityToDto(departmentRepository.findAll());
    }

    public DepartmentDTO getById(Long id) throws NotFoundException {
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isEmpty()) throw new NotFoundException("Department Not Available");
        return converter.entityToDto(department.get());
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


    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentdto) {

        Department depDB = departmentRepository.findById(id).get();

        if(Objects.nonNull(departmentdto.getName()) &&
                !"".equalsIgnoreCase(departmentdto.getName())) {
            depDB.setName(departmentdto.getName());
        }
        departmentRepository.save(depDB);


    return converter.entityToDto(depDB);

    }

}
