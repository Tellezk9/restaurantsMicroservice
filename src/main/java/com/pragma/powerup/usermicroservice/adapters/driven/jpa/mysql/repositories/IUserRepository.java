package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndRoleEntityId(Long idUser, Long idRole);
    Optional<UserEntity> findByDniNumberAndRoleEntityId(Integer idUser, Long idRole);
    void deleteByDniNumberAndRoleEntityId(Integer idUser,Long idRole);
    List<UserEntity> findAllByRoleEntityId(Long idRole, Pageable pageable);
    Optional<UserEntity> findByDniNumber(Integer dniNumber);
    Optional<UserEntity> findByMail(String mail);

}
