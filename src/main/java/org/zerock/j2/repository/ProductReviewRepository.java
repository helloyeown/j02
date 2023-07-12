package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j2.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
		
}
