package model;

import java.time.LocalDate;

public class ProductClass {
	private int id;
	private String name;
	private String category; /* food/ drink/ cleaning/ hygiene */
	private int amount;
	private String unit; /* unit/ package/ bottle/ kg/ liter */
	private int min_amount;
	private  LocalDate expiration;
	
	public ProductClass(int id, String name, String category, int amount, String unit, int min_amount, LocalDate expiration) {
		this.setId(id);
		this.name = name;
		this.setCategory(category);
		this.setAmount(amount);
		this.setUnit(unit);
		this.setMin_amount(min_amount);
		this.setExpiration(expiration);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getMin_amount() {
		return min_amount;
	}

	public void setMin_amount(int min_amount) {
		this.min_amount = min_amount;
	}

	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}
}
