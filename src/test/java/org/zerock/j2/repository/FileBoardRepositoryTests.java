package org.zerock.j2.repository;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.entity.FileBoard;
import org.zerock.j2.entity.FileBoardImage;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class FileBoardRepositoryTests {

	@Autowired
	FileBoardRepository repository;

	@Test
	public void insert(){

		for(int i=0; i<100; i++){

		FileBoard fileBoard = FileBoard.builder()
													.title("AA")
													.content("AAA")
													.writer("AA").build();

		FileBoardImage img1 = FileBoardImage.builder()
													.uuid(UUID.randomUUID().toString())
													.fname("AAA.jpg")
													.build();


		FileBoardImage img2 = FileBoardImage.builder()
													.uuid(UUID.randomUUID().toString())
													.fname("BBB.jpg")
													.build();													

		fileBoard.addImage(img1);
		fileBoard.addImage(img2);

		repository.save(fileBoard);
		}

	}


	@Commit
	@Transactional
	@Test
	public void testRemove(){

		Long bno = 2L;

		repository.deleteById(bno);

	}


	// 조회
	@Test
	@Transactional
	public void testRead(){

		Long bno = 100L;

		Optional<FileBoard> result = repository.findById(bno);

		FileBoard board = result.orElseThrow();

		System.out.println(board);

	}


	// 목록
	@Test
	@Transactional
	public void testList(){

		Pageable pageable = PageRequest.of(0, 10);

		Page<FileBoard> result = repository.findAll(pageable);

		// System.out.println(result);

		result.get().forEach(board -> {
			System.out.println(board);
			System.out.println(board.getImages());
		});
	}


	@Transactional
	@Test
	public void testListQuerydsl(){
		
		PageRequestDTO requestDTO = new  PageRequestDTO();

		log.info(repository.list(requestDTO));

	}


	// read test
	@Test
	public void testSelectOne(){

		Long bno = 100L;

		FileBoard board = repository.selectOne(bno);

		log.info(board);
		log.info(board.getImages());

	}


	@Test
	@Commit
	@Transactional
	public void testDelete(){

		Long bno = 99L;

		repository.deleteById(bno);

	}
	

	@Test
	@Commit
	@Transactional
	public void testUpdate(){

		Optional<FileBoard> result = repository.findById(98L);

		FileBoard board = result.orElseThrow();

		board.clearImages();

		FileBoardImage img1 = FileBoardImage.builder()
													.uuid(UUID.randomUUID().toString())
													.fname("ZZZ.jpg")
													.build();								

		board.addImage(img1);

		repository.save(board);

	}
}
