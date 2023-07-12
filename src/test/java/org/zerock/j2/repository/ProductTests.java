package org.zerock.j2.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ProductTests {
	
	@Autowired
	ProductRepository repo;

	// @Test
	// public void testInsert(){

	// 	for(int i=0; i<200; i++){

	// 	Product product = Product.builder()
	// 										.pname("TEST" + i)
	// 										.pdesc("TEST" + i)
	// 										.writer("user" + i)
	// 										.price(4000)
	// 										.build();

	// 	product.addImage(UUID.randomUUID().toString() + "_aaa.jpg");
	// 	product.addImage(UUID.randomUUID().toString() + "_bbb.jpg");
	// 	product.addImage(UUID.randomUUID().toString() + "_ccc.jpg");

	// 	repo.save(product);
	// 	}

	// }


	@Transactional
	@Test
	public void readTest1(){

		Optional<Product> result = repo.findById(1L);

		Product product = result.orElseThrow();

		System.out.println(product);
		System.out.println("---------------------------");
		System.out.println(product.getImages());
		// Transactional이 없으면 이미지를 가져올 때 오류 -> lazy
		// -> 쓸 데 없이 쿼리를 한 번 더 날리기 때문에 비효율적
		// 레파지토리에 selectOne 쿼리, 엔티티 그래프

	}


	@Test
	public void readTest2(){

		Product product = repo.selectOne(1L);

		System.out.println(product);
		System.out.println("---------------------------");
		System.out.println(product.getImages());

	}


	@Test
	public void testDelete(){

		repo.deleteById(1L);

	}


	@Commit
	@Transactional
	@Test
	public void testModify(){
		
		// Product product = repo.selectOne(2L);

		Optional<Product> result = repo.findById(2L);
		Product product = result.orElseThrow();

		product.changePrice(2000);
		
		// product.clearImages();

		product.addImage(UUID.randomUUID().toString() + "_newImage.jpg");

		repo.save(product);
		// 이미지를 모두 지우고 다시 insert
		// 쿼리가 많이 날라가지만 수정은 많이 일어나는 작업이 아님
		// selectOne을 쓰지 않고 findById를 쓰면 쿼리가 한 번 줄어듬

	}


	@Test
	public void testList1(){

		PageRequestDTO requestDTO = new PageRequestDTO();
		PageResponseDTO<ProductListDTO> result = repo.list(requestDTO);

		for (ProductListDTO dto : result.getDtoList()) {
			System.out.println(dto);
		}

	}


	@Test
	public void testList2(){

		PageRequestDTO requestDTO = new PageRequestDTO();
		PageResponseDTO<ProductListDTO> result = repo.listWithReview(requestDTO);

		for (ProductListDTO dto : result.getDtoList()) {
			System.out.println(dto);
		}

	}

}
