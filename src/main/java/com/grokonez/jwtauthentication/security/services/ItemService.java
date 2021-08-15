package com.grokonez.jwtauthentication.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.grokonez.jwtauthentication.message.response.NotFoundException;
import com.grokonez.jwtauthentication.model.Item;
import com.grokonez.jwtauthentication.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	public Page<Item> findAllItems(int pageNumber, int rowPerPage, String name) {
		Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
		Page<Item> items= itemRepository.findAllItems(name.toUpperCase(), sortedByIdAsc);
		return items;
	}
	
	public Item findById(Long id) throws NotFoundException {
		Item item = itemRepository.findById(id).orElse(null);
		if (item == null) {
			throw new NotFoundException(404, "Cannot find Item with id: " + id);
		} else
			return item;
	}

	public Long count() {
		return itemRepository.count();
	}
}
