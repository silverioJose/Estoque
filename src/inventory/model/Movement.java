package inventory.model;

import java.time.LocalDateTime;

import inventory.model.Product;

public class Movement {
	
	    private int id;
	    private Product product;
	    private int quantity;
	    private String type;
	    private LocalDateTime date;
	    private String notes;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Product getProduct() {
			return product;
		}
		public void setProduct(Product product) {
			this.product = product;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public LocalDateTime getDate() {
			return date;
		}
		public void setDate(LocalDateTime date) {
			this.date = date;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
	    
	    
}
