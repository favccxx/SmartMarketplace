package com.favccxx.mp.utils;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailUtil {
	
	private static int DEFAULT_WIDTH = 200;
	private static int DEFAULT_HEIGHT = 200;

	/**
	 * 创建一个200*200的缩略图 
	 * @param sourceFile 源文件
	 * @param destFile 目标文件
	 * @throws IOException
	 */
	public static void saveThumbnail(File sourceFile, File destFile) throws IOException {
		Thumbnails.of(sourceFile).forceSize(DEFAULT_WIDTH, DEFAULT_HEIGHT).toFile(destFile);
	}
	
	/**
	 * 创建200*200的缩略图
	 * @param sourcePath
	 * @param destPath
	 * @throws IOException
	 */
	public static void saveThumbnail(String sourcePath, String destPath) throws IOException {
		Thumbnails.of(new File(sourcePath)).forceSize(DEFAULT_WIDTH, DEFAULT_HEIGHT).toFile(new File(destPath));
	}
	
	/**
	 * 创建缩略图
	 * @param sourcePath 原始文件路径
	 * @param width 缩略图宽度
	 * @param height 缩略图高度
	 * @param destPath 缩略图位置
	 * @throws IOException
	 */
	public static void saveThumbnail(String sourcePath, int width, int height, String destPath) throws IOException {
		Thumbnails.of(new File(sourcePath)).forceSize(width, height).toFile(new File(destPath));
	}
	
	
}
