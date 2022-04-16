package edu.bitspilani.mtech.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity

@JsonInclude(Include.NON_NULL)
@NamedQueries({@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p"),
	@NamedQuery(name = "Product.findByCode", query = "FROM Product p WHERE p.code = ?1")
	
})
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="buying_price")
	private float buyingPrice;


	private String code;
	private String description;
	private int quantity;

	@Column(name="selling_price")
	private float sellingPrice;

	@Transient
	private String tranType;

	public Product() {
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the buyingPrice
	 */
	public float getBuyingPrice() {
		return buyingPrice;
	}



	/**
	 * @param buyingPrice the buyingPrice to set
	 */
	public void setBuyingPrice(float buyingPrice) {
		this.buyingPrice = buyingPrice;
	}



	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}



	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}



	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}



	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	/**
	 * @return the sellingPrice
	 */
	public float getSellingPrice() {
		return sellingPrice;
	}



	/**
	 * @param sellingPrice the sellingPrice to set
	 */
	public void setSellingPrice(float sellingPrice) {
		this.sellingPrice = sellingPrice;
	}



	/**
	 * @return the tranType
	 */
	public String getTranType() {
		return tranType;
	}



	/**
	 * @param tranType the tranType to set
	 */
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	
}