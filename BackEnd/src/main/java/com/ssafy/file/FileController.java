package com.ssafy.file;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.board.controller.BoardController;
import com.ssafy.file.model.FileDto;
import com.ssafy.file.service.FileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController extends HttpServlet {
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;
	
	FileUtil fileUtil;
	FileService fileService;
	
	private FileController (FileUtil fileUtil, FileService fileService) {
		super();
		this.fileUtil = fileUtil;
		this.fileService = fileService;
	}
	
	
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@GetMapping("/download/{sfolder}/{ofile}/{sfile}")
	public ResponseEntity<Object> download(@PathVariable("sfolder") String sfolder, @PathVariable("ofile") String ofile,
			@PathVariable("sfile") String sfile) {
		log.debug("download file info sfolder : {}, ofile : {}, sfile : {}", sfolder, ofile, sfile);
		String file = uploadPath + File.separator + sfolder + File.separator + sfile;

		try {
			Path filePath = Paths.get(file);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(URLEncoder.encode(ofile, "UTF-8").replaceAll("\\+", "%20")).build());
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registProfile(MultipartHttpServletRequest formData) {
		logger.info("이미지 등록 들어옴 {}", formData);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			FileDto fileDto = objectMapper.readValue(formData.getParameter("fileDto"), FileDto.class);
			MultipartFile multipartFiles = formData.getFile("imgInfo");
			
			logger.info("regist dto : {}", fileDto);
			logger.info("regist multipartFile : {}", multipartFiles);
			
			fileDto = fileUtil.storeImg(multipartFiles, fileDto);
			 
			 fileService.registerProfile(fileDto);
			 
			 return new ResponseEntity<Void>(HttpStatus.OK);
			
		} catch (Exception e) {
            System.out.println("board uploadImg Controller Error");
            e.printStackTrace();
            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getFileInfo/{user_id}")
	public ResponseEntity<?> viewImg(@PathVariable("user_id") String user_id) throws Exception {
		
		FileDto fileDto = fileService.getProfileFile(user_id);
		
		return new ResponseEntity<FileDto>(fileDto, HttpStatus.OK);
	}
	
    @GetMapping("/getImg/{saveFolder}/{originalName}/{saveName}")
    public ResponseEntity<?> getImg(@PathVariable("saveFolder") String saveFolder, 
                                            @PathVariable("originalName") String originalName, 
                                            @PathVariable("saveName") String saveName)  {

        String file = uploadImagePath + File.separator +saveFolder + File.separator + saveName;
        
        try {
            Path filePath = Paths.get(file);
            
            Resource resource = new FileSystemResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", Files.probeContentType(filePath));
            
            System.out.println("resource : " + resource);
            
            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    } 
	
	
}















