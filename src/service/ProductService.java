package service;

import java.util.ArrayList;
import java.util.List;

import model.ProductClass;

public class ProductService {
	private List<ProductClass> products = new ArrayList<>();
	private int nextId = 1;
	
	public void insertProduct(ProductClass new_product) {
		new_product.setId(nextId++);
		products.add(new_product);
	}
	
	public void listProducts() {
		for (ProductClass i : products) {
			System.out.printf("%d - %s (%s) Quantidade: %d %n", i.getId(), i.getName(), i.getCategory(), i.getAmount());
		}
	}
	
	public void updateAmount(int id, int amount) {
		for(int i = 0; i<products.size(); i++) {
			if(products.get(i).getId() == id) {
				products.get(i).setAmount(amount);
			}
		}
	}
	
	public List<ProductClass> verifyAmount() {
		List<ProductClass> lowAmount = new ArrayList<>();
		for(int i = 0; i<products.size(); i++) {
			if(products.get(i).getAmount() < products.get(i).getMin_amount()) {
				lowAmount.add(products.get(i));
				//teste
				System.out.printf("\n- %s esta abaixo do estoque minimo", products.get(i).getName());
			}
		}
		return lowAmount;
	}
}
