package web.info.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import web.info.domain.BoardAttachVO;
import web.info.repository.BoardAttachRepository;

@Log4j
@Component
public class FileCheckTask {
	
	@Autowired
	BoardAttachRepository boardAttachRepository; 
	
	@Scheduled(cron = "0 0 2 * * *")
	public void checkFile() {
		
		/* 데이터베이스에 기록된 파일 정보 */
		List<BoardAttachVO> fileList = boardAttachRepository.pastFiles();
		
		// 업로드 된 파일  
		List<Path> fileListPath = fileList.stream()
			.map(vo-> Paths.get("c:/storage",vo.getUploadPath(),vo.getUuid()+"_"+vo.getFileName()))
			.collect(Collectors.toList());
		log.info(fileListPath);
		
		// 썸네일 파일 경로
		fileList.stream()
				.map(vo-> Paths.get("c:/storage",vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName()))
				.forEach(e-> fileListPath.add(e));
		
		// 어제 날짜 폴더 경로 
		File targetDir = Paths.get("c:/storage", getYesterdayFolder()).toFile();
		
		// 어제 날짜 폴더에 있는 모든 파일 순회
		// 데이터베이스에 기록된 파일 정보가 아니면 삭제 대상 파일
		File[] delTagetList = targetDir.listFiles(file -> !fileListPath.contains(file.toPath()));
		Arrays.stream(delTagetList).forEach(file->{
			file.delete();
		});
	}
	
	private String getYesterdayFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance(); 
		cal.add(Calendar.DATE, -1);
		return sdf.format(cal.getTime()); 
	}
}
