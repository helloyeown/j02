package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileBoardImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgno;

	private String uuid;

	private String fname;

	// 파일이 등록된 순서 (대표이미지)
	private int ord;


	public void changeOrd(int ord){
		this.ord = ord;
	}

}
