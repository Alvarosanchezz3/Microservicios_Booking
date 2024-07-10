package com.alvaro.usuario_service.repository;


import com.alvaro.usuario_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName (String nombre);
}
