package com.grokonez.jwtauthentication.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByItemIdAndUserId(Long itemId, Long userId);
	List<Cart> findByUserId(Long userId);
	Boolean existsByItemIdAndUserId(Long itemId, Long userId);
}
