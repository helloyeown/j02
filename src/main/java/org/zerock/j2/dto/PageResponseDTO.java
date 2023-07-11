package org.zerock.j2.dto;

import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

@Data
public class PageResponseDTO<E> {
	
	// 목록
	private List<E> dtoList;

	// 총 글 갯수
	private Long totalCount;

	// 페이지 숫자
	private List<Integer> pageNums;

	// 이전, 다음 버튼
	private boolean prev, next;

	private PageRequestDTO requestDTO;

	private int page, size, start, end;

	
	public PageResponseDTO(List<E> dtoList, Long totalCount, PageRequestDTO requestDTO){
		this.dtoList = dtoList;
		this.totalCount = totalCount;
		this.requestDTO = requestDTO;

		this.page = requestDTO.getPage();
		this.size = requestDTO.getSize();

		// 13 -> 1.3 -> 2.0 -> 20
		int tempEnd = (int)(Math.ceil(page/10.0) * 10);

		this.start = tempEnd - 9;
		this.prev = this.start != 1;

		// 20, 17.8 -> 18
		int realEnd = (int)(Math.ceil(totalCount/(double)size));

		this.end = tempEnd > realEnd ? realEnd : tempEnd;
		
		this.next = (this.end * this.size) < totalCount;

		this.pageNums = IntStream.rangeClosed(start, end).boxed().toList();
	}

}
