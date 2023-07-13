package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.Product;
import org.zerock.j2.repository.search.ProductSearch;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

	@EntityGraph(attributePaths = "images")	// images를 같이 로딩
	@Query("select p from Product p where p.delFlag = false and p.pno = :pno")
	Product selectOne(@Param("pno") Long pno);

}
