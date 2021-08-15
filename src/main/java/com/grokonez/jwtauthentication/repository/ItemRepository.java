package com.grokonez.jwtauthentication.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>  {

	@Transactional
	@Query(value = "SELECT u FROM Item u WHERE TRIM(UPPER(u.name)) LIKE %:name%")
	Page<Item> findAllItems(@Param("name") String name, Pageable pageable);
	
}
