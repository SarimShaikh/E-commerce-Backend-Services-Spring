package com.usermangment.auth.repository;

import com.usermangment.auth.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
