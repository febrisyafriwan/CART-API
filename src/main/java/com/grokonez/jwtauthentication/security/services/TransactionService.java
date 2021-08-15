package com.grokonez.jwtauthentication.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grokonez.jwtauthentication.message.response.BadResourceException;
import com.grokonez.jwtauthentication.message.response.NotFoundException;
import com.grokonez.jwtauthentication.model.Cart;
import com.grokonez.jwtauthentication.model.Item;
import com.grokonez.jwtauthentication.model.Transaction;
import com.grokonez.jwtauthentication.repository.CartRepository;
import com.grokonez.jwtauthentication.repository.ItemRepository;
import com.grokonez.jwtauthentication.repository.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction finalizeCart(Long userId) throws BadResourceException, NotFoundException, Exception{

		Transaction transaction = transactionRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(404, "Cannot find User with id: " + userId));
		List<Cart> carts = cartRepository.findByUserId(transaction.getUserId());
		
		if (transaction != null) {
			Double total = 0D;
			for (Cart obj : carts) {
				Item item = itemRepository.findById(obj.getItemId()).orElse(null);
				obj.setItems(item);
				total += obj.getTotalPrice();
			}
			transaction.setTotalPayment(total);
			transactionRepository.save(transaction);
		}
		transaction.setCarts(carts);
		return transaction;
	}
}
