/**
 * 
 */
package edu.bitspilani.mtech.controller;


import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.bitspilani.mtech.dto.Error;
import edu.bitspilani.mtech.dto.ProductDTO;
import edu.bitspilani.mtech.model.Product;
import edu.bitspilani.mtech.model.ResponseModel;
import edu.bitspilani.mtech.repository.ProductRepository;

/**
 * @author Mohamed Noohu
 *
 */
@RestController
@RequestMapping("/api")
public class ProductController {

private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductRepository repo;
	
	@Autowired
    ObjectMapper objectMapper;
	
	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@RequestPart String productData) throws JsonMappingException, JsonProcessingException {
		ResponseModel rm = new ResponseModel();
		
		logger.info("Entering createProduct....");
		
		
		Product p = this.getJson(productData);
		
		if (p.getBuyingPrice() < 0)
			rm.setError("buying_price","Buying price must be greater than 0");
		
		if (p.getSellingPrice() < 0)
			rm.setError("selling_price","Selling price must be greater than 0");
		
		if (p.getQuantity() < 0)
			rm.setError("quantity","Quantity must be greater than 0");
		
		if (p.getDescription() == null)
			rm.setError("description","Product description must be specified");
		
		if (p.getCode() == null)
			rm.setError("code","Product code must be specified");
		
		if (repo.existsByCode(p.getCode()))
			rm.setError("code","Product code already exist");
		
		if (!rm.getErrors().isEmpty())
			return ResponseEntity.ok().body(new Error(rm));
		else {
			repo.save(p);
			return ResponseEntity.ok().body(null);
		}
	}

	//@GetMapping("/product/code/{code}")
	@GetMapping(value="/product/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getByCode(@PathVariable("code") String code) {
		
		logger.info("Entering getByCode with:"+code);
		
		ResponseModel rm = new ResponseModel();
		if (code == null)
			rm.setError("code","Product code must be specified");
		
		if (!repo.existsByCode(code))
			rm.setError("code","Product code does not exist");
		
		if (!rm.getErrors().isEmpty()) {
			logger.info("Returning error response as code doesn't exist...");
			return ResponseEntity.ok().body(new Error(rm));
		}
		else {
			Optional<Product> product = repo.findBycode(code);
			Product p = product.get();
			ProductDTO pDto = ProductDTO.builder()
			.buyingPrice(p.getBuyingPrice())
			.sellingPrice(p.getSellingPrice())
			.quantity(p.getQuantity())
			.code(p.getCode())
			.description(p.getDescription())
			.id(p.getId())
			.build();
			return ResponseEntity.ok().body(pDto);
		}
			
	}
	
	@PutMapping(value="/product", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestPart String productData ) throws IOException {

		logger.info("Entering update....");
		
		ResponseModel rm = new ResponseModel();

		Product modified = this.getJson(productData);
		
		logger.info("productData:"+productData);
		
		if (modified.getCode() == null)
			rm.setError("code","Product code must be specified");
		
		if (modified.getQuantity() < 0)
			rm.setError("quantity","Quantity must be greater than 0");
		
		if (modified.getTranType() == null || (!modified.getTranType().equals("purchase") && !modified.getTranType().equals("sale")))
			rm.setError("tranType","Tranype must be specified as either 'purchase' or 'sale'");
		
		if (rm.getErrors().isEmpty()) {
			Optional<Product> p = repo.findBycode(modified.getCode());
			if (!p.isPresent()) {
				rm.setError("code","Product code does not exist");
			} else {
				int q = p.get().getQuantity();
				if (modified.getTranType().equals("purchase"))
					q += modified.getQuantity();
				else
					q -= modified.getQuantity();
				
				p.get().setQuantity(q);
				
				repo.save(p.get());
			}
		}
		
		if (!rm.getErrors().isEmpty())
			return ResponseEntity.ok().body(new Error(rm));
		else {
			return ResponseEntity.ok().body(null);
		}
			
		
	}
	public Product getJson(String productData) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(productData, Product.class);
	}
	
}
