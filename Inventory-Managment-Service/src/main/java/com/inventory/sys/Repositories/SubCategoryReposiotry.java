package com.inventory.sys.Repositories;

import com.inventory.sys.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryReposiotry extends JpaRepository<SubCategory,Long> {

}
