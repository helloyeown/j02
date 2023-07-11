package org.zerock.j2.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileBoardListDTO {
	
	private Long bno;
	private String title;
	private String uuid;
	private String fname;

}
