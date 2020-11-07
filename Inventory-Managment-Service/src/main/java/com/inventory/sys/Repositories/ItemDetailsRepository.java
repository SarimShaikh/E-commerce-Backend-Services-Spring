package com.inventory.sys.Repositories;

import com.inventory.sys.entities.ItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailsRepository  extends JpaRepository<ItemDetails,Long> {

}
