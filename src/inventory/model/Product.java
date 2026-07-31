package inventory.model;

import inventory.model.Department;
import inventory.model.Unit;

public class Product {

    private int id;
    private String name;
    private String description;
    private int currentStock;
    private int minimumStock;
    private Department department;
    private Unit stockUnit;
    private double packageContent;
    private Unit contentUnit;
    private boolean active;
    
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}
	public int getMinimumStock() {
		return minimumStock;
	}
	public void setMinimumStock(int minimumStock) {
		this.minimumStock = minimumStock;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Unit getStockUnit() {
		return stockUnit;
	}
	public void setStockUnit(Unit stockUnit) {
		this.stockUnit = stockUnit;
	}
	public double getPackageContent() {
		return packageContent;
	}
	public void setPackageContent(double packageContent) {
		this.packageContent = packageContent;
	}
	public Unit getContentUnit() {
		return contentUnit;
	}
	public void setContentUnit(Unit contentUnit) {
		this.contentUnit = contentUnit;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}