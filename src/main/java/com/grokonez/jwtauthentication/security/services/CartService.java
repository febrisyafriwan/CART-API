package com.grokonez.jwtauthentication.security.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grokonez.jwtauthentication.message.request.CartForm;
import com.grokonez.jwtauthentication.message.response.BadResourceException;
import com.grokonez.jwtauthentication.message.response.NotFoundException;
import com.grokonez.jwtauthentication.model.Cart;
import com.grokonez.jwtauthentication.model.Item;
import com.grokonez.jwtauthentication.repository.CartRepository;
import com.grokonez.jwtauthentication.repository.ItemRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;

	private boolean existsById(Long id) {
		return cartRepository.existsById(id);
	}

	public void deleteById(Long id) throws NotFoundException {
		if (!existsById(id)) {
			throw new NotFoundException(404, "Cannot find Cart with id: " + id);
		} else {
			cartRepository.deleteById(id);
		}
	}

	public void save(CartForm cartForm) throws BadResourceException, NotFoundException {
		
		if (cartForm.getItemId() != null && cartForm.getUserId() != null && cartForm.getAmount() != null) {
			Item item = itemRepository.findById(cartForm.getItemId()).orElseThrow(() -> new NotFoundException(404, "Cannot find Item with id: " + cartForm.getItemId()));
			if (cartRepository.existsByItemIdAndUserId(cartForm.getItemId(),cartForm.getUserId())){
				Cart cart = cartRepository.findByItemIdAndUserId(cartForm.getItemId(),cartForm.getUserId()).orElse(null);
				if(cart != null) {
					cart.setAmount(cart.getAmount() + cartForm.getAmount());
					cart.setTotalPrice(item.getPrice() * cart.getAmount());	
					cartRepository.save(cart);
				}
			}else {
				Cart cart = new Cart();
				cart.setAmount(cartForm.getAmount());
				cart.setTotalPrice(item.getPrice() * cartForm.getAmount());	
				cart.setItemId(cartForm.getItemId());
				cart.setUserId(cartForm.getUserId());
				cartRepository.save(cart);
			}
		} else {
			BadResourceException exc = new BadResourceException(400, "Failed to save cart cause item id or user id or amount is null");
			throw exc;
		}
	}
}
