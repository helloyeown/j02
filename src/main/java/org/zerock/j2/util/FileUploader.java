package org.zerock.j2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;

@Component
@Log4j2
public class FileUploader {

	// 예외 만들기 (custom exception)
	public static class UploadException extends RuntimeException {
		public UploadException(String msg){
			super(msg);
		}
	}
	
	@Value("${org.zerock.upload.path}")
	private String path;

	// 파일 삭제 (디비에선 삭제하지 않고 업데이트만)
	public void removeFiles(List<String> fileNames){

		if(fileNames == null || fileNames.size() == 0){
			return;
		}

		// 예외 처리 때문에 람다식 사용x
		for (String fname : fileNames) {
			// 원본, 썸네일
			File original = new File(path, fname);
			File thumb = new File(path, "s_" + fname);

			if(thumb.exists()){
				thumb.delete();
			}

			original.delete();
		}
	}

	// 파일 업로드
	public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumnail){

		if(files == null || files.size() == 0){
			throw new UploadException("No File");
		}

		List<String> uploadFileNames = new ArrayList<>();

		log.info("path: " + path);
		log.info(files);

		// loop : 파일 하나씩 save -> 예외 때문에 람다식 못 씀
		for (MultipartFile mFile : files) {
			String originalFileName = mFile.getOriginalFilename();
			String uuid = UUID.randomUUID().toString();

			String saveFileName = uuid+"_"+originalFileName;
			File saveFile = new File(path, saveFileName);

			// auto close
			try (InputStream in = mFile.getInputStream(); OutputStream out = new FileOutputStream(saveFile)) {
				
				FileCopyUtils.copy(in, out);

				if(makeThumnail){
					File thumboutFile = new File(path, "s_" + saveFileName);
					Thumbnailator.createThumbnail(saveFile, thumboutFile, 200, 200);
				}

				uploadFileNames.add(saveFileName);

			} catch (Exception e) {
				throw new UploadException("Upload Fail: " + e.getMessage());
			}
		}

		return uploadFileNames;
	}

}
