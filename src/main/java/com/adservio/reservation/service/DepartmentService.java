package com.adservio.reservation.service;

import com.adservio.reservation.dao.DepartmentRepository;
import com.adservio.reservation.dao.RoomRepository;
import com.adservio.reservation.dto.RoomDTO;
import com.adservio.reservation.entities.Department;
import com.adservio.reservation.dto.DepartmentDTO;
import com.adservio.reservation.entities.Room;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.DepartmentConvert;

import com.adservio.reservation.mapper.RoomConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final
    DepartmentRepository departmentRepository;
    private final
    DepartmentConvert converter;
    private final RoomConvert roomConvert;
    private final RoomRepository roomRepository;




    public List<DepartmentDTO> listAll(){

        return converter.entityToDto(departmentRepository.findAll());
    }

    public DepartmentDTO getById(Long id) throws NotFoundException {
        Optional<Department> department=departmentRepository.findById(id);
        if(department.isEmpty()) throw new NotFoundException("Department Not Available");
        return converter.entityToDto(department.get());
    }

    public Collection<RoomDTO> ListRoomByDepartmentID(Long id) throws NotFoundException {
        DepartmentDTO departmentDTO=getById(id);
        Department department=converter.dtoToEntity(departmentDTO);
        Collection<Room> rooms=department.getRoom();
        return roomConvert.entityToDto(rooms);
    }

    public DepartmentDTO GetDepartmentByName(String name) throws NotFoundException {
        if(name.isEmpty()) throw new NotFoundException("Insert a name to find department");
        if(departmentRepository.findByName(name)==null) throw new NotFoundException("Department not found");
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
