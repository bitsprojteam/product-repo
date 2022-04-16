/**
 * 
 */
package edu.bitspilani.mtech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.bitspilani.mtech.model.Product;

/**
 * @author Mohamed Noohu
 *
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Boolean existsByCode(String code);
	Optional<Product> findBycode(String code);
}
