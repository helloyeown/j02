package org.zerock.j2.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

	@Entity
	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString(exclude = "images")	// 주의
	public class FileBoard {

		@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;

	private String title;

	private String content;

	private String writer;

	@BatchSize(size = 20)
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
	// 파일 보드가 여러 개의 이미지를 가질 것
	@JoinColumn(name = "board")		// board라는 컬럼 만듬
	@Builder.Default
	private List<FileBoardImage> images = new ArrayList<>();
	// 처음부터 new로 만들어서 해당 객체를 계속 가리키도록 초기화해서 관리함
	// -> 새로 컬렉션 추가하면 절대 안 됨
	// 파일이미지와 연관관계 명시 안 하면 에러
	// 이미지의 crud도 보드가 관리 -> 하위 엔티티까지 한 번에 save 가능

	// 도메인 주도 개발은 비즈니스 로직을 도메인에서 관리

	// 이미지 추가
	public void addImage(FileBoardImage boardImage){
		boardImage.changeOrd(images.size());
		images.add(boardImage);
	}

	// 수정
	public void clearImages(){
		images.clear();		// 내용 지우기
	}

}
