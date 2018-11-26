package com.favccxx.mp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.favccxx.mp.constants.SysConstants;
import com.favccxx.mp.entity.SmartImage;
import com.favccxx.mp.entity.SmartProduct;
import com.favccxx.mp.service.ImageService;
import com.favccxx.mp.service.ProductService;
import com.favccxx.mp.utils.ThumbnailUtil;
import com.favccxx.mp.utils.result.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/image")
@Api(tags = "8. 图片管理接口")
public class ImageController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ImageService imageService;
	@Autowired
	ProductService productService;

	@GetMapping("/product/list")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartImage.class) })
	@ApiOperation(httpMethod = "GET", value = "查询商品Id查询所有的缩略图")
	public RestResult<List<SmartImage>> list(@RequestParam(value = "productId", required = false) long productId) {
		List<SmartImage> list = imageService.listThumbnailsByProductId(productId);
		return RestResult.sucess(list);
	}

	@GetMapping("/view/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartImage.class) })
	@ApiOperation(httpMethod = "GET", value = "根据图片Id读取图片信息")
	public void view(
			@PathVariable("id") long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		SmartImage image = imageService.findById(id);
		if (image != null) {			
			response.setContentType(image.getContentType());
			
			FileInputStream fis = null;
			OutputStream os = null;
			try {
				fis = new FileInputStream(image.getLocation() + image.getName());
				os = response.getOutputStream();
				int count = 0;
				byte[] buffer = new byte[1024 * 8];
				while ((count = fis.read(buffer)) != -1) {
					os.write(buffer, 0, count);
					os.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fis.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	
	@GetMapping("/thumbnail/{id}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartImage.class) })
	@ApiOperation(httpMethod = "GET", value = "根据图片Id读取缩略图图片信息")
	public void thumbnail(
			@PathVariable(value = "id") long id, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		SmartImage image = imageService.findById(id);
		if (image != null) {		
			response.setContentType(image.getContentType());
			
			FileInputStream fis = null;
			OutputStream os = null;
			try {
				fis = new FileInputStream(image.getLocation() + image.getThumbnail());
				os = response.getOutputStream();
				int count = 0;
				byte[] buffer = new byte[1024 * 8];
				while ((count = fis.read(buffer)) != -1) {
					os.write(buffer, 0, count);
					os.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				fis.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	

	@PostMapping("/upload")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "操作成功", response = SmartImage.class) })
	@ApiOperation(httpMethod = "POST", value = "上传文件")
	public RestResult<SmartImage> upload(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "productId", required = false) long productId,
			@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
			throws IllegalStateException, IOException {
		
		System.out.println("-----" + type + "#########" + productId);
		
		logger.info("***************imgType: " + type + ", productId: " + productId);
		
		logger.info("###############fileName：" + file.getOriginalFilename() + "&&&&&:" + file.getContentType());

		String fileName = file.getOriginalFilename();
		String fileNamePostfix = fileName.substring(fileName.lastIndexOf("."));

		logger.info("@@@@@@@@@@@@@@@fileType:" + fileNamePostfix);

		String uuid = UUID.randomUUID().toString();
		String imageName = uuid + fileNamePostfix;
		String thumbnailName = uuid + "_thumb" + fileNamePostfix;

		// 保存文件
		File imageFile = new File(SysConstants.IMAGE_PATH + imageName);
		if (!imageFile.exists()) {
			imageFile.createNewFile();
		}
		file.transferTo(imageFile);

		// 保存缩略图
		File destFile = new File(SysConstants.IMAGE_PATH + thumbnailName);
		ThumbnailUtil.saveThumbnail(imageFile, destFile);
		;

		SmartImage image = new SmartImage();
		image.setContentType(file.getContentType());
		image.setName(imageName);
		image.setProductId(productId);
		image.setThumbnail(thumbnailName);
		image.setLength(file.getSize());		
		image.setLocation(SysConstants.IMAGE_PATH);
		
		
		image.setType(SysConstants.IMAGE_CONTENT);
		if(StringUtils.isNoneBlank(type)) {
			image.setType(type);
		}
		
		imageService.save(image);
		
		//更新产品的默认显示图片
		SmartProduct product = productService.findById(productId);
		if(product != null && StringUtils.isBlank(product.getProductImg())) {
			product.setProductImg(String.valueOf(image.getId()));
			product.setUpdateTime(new Date());
			productService.save(product);
		}

		return RestResult.sucess(image);
	}

}
