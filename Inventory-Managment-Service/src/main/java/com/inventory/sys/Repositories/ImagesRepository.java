package com.inventory.sys.Repositories;

import com.inventory.sys.entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images,Long> {
    void deleteAllByItemId(Long itemId);
}
