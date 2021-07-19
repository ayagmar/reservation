package com.adservio.reservation.service;

import com.adservio.reservation.dao.RoleRepository;
import com.adservio.reservation.entities.Role;
import com.adservio.reservation.entities.dto.RoleDTO;
import com.adservio.reservation.exception.NotFoundException;
import com.adservio.reservation.mapper.RoleConvert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleConvert converter;

    public RoleService(RoleRepository roleRepository, RoleConvert converter) {
        this.roleRepository = roleRepository;
        this.converter = converter;
    }

    public List<RoleDTO> listAll() {

        return converter.entityToDto(roleRepository.findAll());
    }

    public RoleDTO getById(Long id) throws NotFoundException {

        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) throw new NotFoundException("Room Not Available");
        return converter.entityToDto(role.get());
    }

    public List<RoleDTO> saveRoles(List<RoleDTO> roleDTOS) {
        List<Role> roles = converter.dtoToEntity(roleDTOS);
        roles= roleRepository.saveAll(roles);
        return converter.entityToDto(roles);

    }


    public RoleDTO getRoleByName(String name) {
        return converter.entityToDto(roleRepository.findByRoleName(name));
    }

    public RoleDTO save(RoleDTO roleDTO) {
        Role role = converter.dtoToEntity(roleDTO);
        role = roleRepository.save(role);
        return converter.entityToDto(role);
    }


    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }



}
