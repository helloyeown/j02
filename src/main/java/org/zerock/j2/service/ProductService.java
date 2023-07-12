package org.zerock.j2.service;

import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ProductService {

	PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO);

	Long register(ProductDTO productDTO);
	
}
