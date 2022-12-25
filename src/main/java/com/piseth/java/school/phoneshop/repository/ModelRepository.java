package com.piseth.java.school.phoneshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.piseth.java.school.phoneshop.model.Model;

public interface ModelRepository extends JpaRepository<Model, Long> , JpaSpecificationExecutor<Model>{

}
