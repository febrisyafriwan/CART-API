package com.grokonez.jwtauthentication.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grokonez.jwtauthentication.message.request.CartForm;
import com.grokonez.jwtauthentication.message.response.BadResourceException;
import com.grokonez.jwtauthentication.message.response.CommonResponse;
import com.grokonez.jwtauthentication.message.response.NotFoundException;
import com.grokonez.jwtauthentication.model.Item;
import com.grokonez.jwtauthentication.model.Transaction;
import com.grokonez.jwtauthentication.security.services.CartService;
import com.grokonez.jwtauthentication.security.services.ItemService;
import com.grokonez.jwtauthentication.security.services.TransactionService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestRestAPIs {

	private final int ROW_PER_PAGE = 5;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CartService cartService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return ">>> User Contents!";
	}

	@GetMapping("/pm")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public String projectManagementAccess() {
		return ">>> Board Management Project";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}

	@GetMapping("/user/item/index")
	@PreAuthorize("hasRole('USER')")
	public CommonResponse<Page<Item>> indexItem(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "name", defaultValue = "") String name) {
		Page<Item> itemList = itemService.findAllItems(pageNumber, ROW_PER_PAGE, name);
		CommonResponse<Page<Item>> response = new CommonResponse<>();
		response.setstatus(200);
		response.setMessage("success");
		response.setData(itemList);
		return response;
	}

	@PostMapping("/user/cart/add")
	@PreAuthorize("hasRole('USER')")
	public CommonResponse<CartForm> addCart(Model model, @RequestBody CartForm cartForm) throws BadResourceException, NotFoundException {
		CommonResponse<CartForm> response = new CommonResponse<>();
		cartService.save(cartForm);
		response.setstatus(200);
		response.setMessage("success");
		response.setData(cartForm);
		return response;
	}

	@PostMapping("/user/cart/delete")
	@PreAuthorize("hasRole('USER')")
	public CommonResponse<Long> deleteCart(@RequestParam(value = "id", defaultValue = "0") long id)
			throws NotFoundException {
		CommonResponse<Long> response = new CommonResponse<>();
		cartService.deleteById(id);
		response.setstatus(200);
		response.setMessage("success");
		response.setData(id);
		return response;
	}

	@PostMapping("/user/cart/finalize")
	@PreAuthorize("hasRole('USER')")
	public CommonResponse<Transaction> finalizeCart(@RequestParam(value = "userId", defaultValue = "0") long userId)
			throws BadResourceException, NotFoundException ,Exception{
		CommonResponse<Transaction> response = new CommonResponse<>();
		Transaction transaction = transactionService.finalizeCart(userId);
		response.setstatus(200);
		response.setMessage("success");
		response.setData(transaction);
		return response;
	}
}