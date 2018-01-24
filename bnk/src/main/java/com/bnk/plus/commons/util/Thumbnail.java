package com.bnk.plus.commons.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.IOUtils;

public class Thumbnail {
	
	/**
	 * <pre>
	 * 썸네일 생성 (Max Width의 비율에 따른 높이 변경)
	 * 2015.8.18 추가 ks-choi
	 * </pre>
	 * @param orgName 원본 이미지 파일 경로
	 * @param destName 썸네일로 저장될 이미지 파일 경로
	 * @param maxWidth 썸네일 이미지의 최대 Width pixel (-1일 경우 비율 축소 안함)
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String createThumb(String orgName, String destName, int maxWidth) throws IOException {
		File orgFile = new File(orgName);
		File destFile = new File(destName);
		
		return createThumb(orgFile, destFile, maxWidth);
	}
	
	/**
	 * <pre>
	 * 썸네일 생성 (Max Width의 비율에 따른 높이 변경)
	 * 2015.8.18 추가 ks-choi
	 * </pre>
	 * @param orgFileStream 원본 이미지 스트림
	 * @param destName 썸네일로 저장될 이미지 파일 경로
	 * @param maxWidth 썸네일 이미지의 최대 Width pixel (-1일 경우 비율 축소 안함)
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String createThumb(InputStream orgFileStream, String destName, int maxWidth) throws IOException {
		final File orgFile = File.createTempFile("createThumb", ".tmp");
		orgFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(orgFile)) {
            IOUtils.copy(orgFileStream, out);
        }
        
		File destFile = new File(destName);
		
		return createThumb(orgFile, destFile, maxWidth);
	}
	
	/**
	 * <pre>
	 * 썸네일 생성 (Max Width의 비율에 따른 높이 변경)
	 * 2015.8.18 추가 ks-choi
	 * </pre>
	 * @param orgFileStream 원본 이미지 스트림
	 * @param destFile 썸네일로 저장될 이미지 파일
	 * @param maxWidth 썸네일 이미지의 최대 Width pixel (-1일 경우 비율 축소 안함)
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String createThumb(InputStream orgFileStream, File destFile, int maxWidth) throws IOException {
		final File orgFile = File.createTempFile("createThumb", ".tmp");
		orgFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(orgFile)) {
            IOUtils.copy(orgFileStream, out);
        }
        
		return createThumb(orgFile, destFile, maxWidth);
	}
	
	
	/**
	 * <pre>
	 * 썸네일 생성 (Max Width의 비율에 따른 높이 변경)
	 * 2015.8.18 추가 ks-choi
	 * </pre>
	 * @param orgFile 원본 이미지 파일
	 * @param destFile 썸네일로 저장될 이미지 파일
	 * @param maxWidth 썸네일 이미지의 최대 Width pixel (-1일 경우 비율 축소 안함)
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String createThumb(File orgFile, File destFile, int maxWidth) throws IOException {
		
		if (maxWidth < 0){
			return createThumb(orgFile, destFile, -1, -1);
		
		} else {
			Image srcImg = null;
			String suffix = orgFile.getName().substring(orgFile.getName().lastIndexOf('.') + 1).toLowerCase();
	
			if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
				srcImg = ImageIO.read(orgFile);
			} else {
				// JPEG Format
				srcImg = new ImageIcon(orgFile.toString()).getImage();
			}
		
			int srcWidth = srcImg.getWidth(null);
			int srcHeight = srcImg.getHeight(null);
			// 대상 이미지 Width가 Max값보다 작다면 비율로 축소
			if (srcWidth > maxWidth){
				srcHeight = (int) (((float) maxWidth / srcWidth) * srcHeight);
				srcWidth = maxWidth;
			}
			
			return createThumb(orgFile, destFile, srcWidth, srcHeight);
		}
	}
	
	/**
	 * 썸네일 생성
	 * @param orgName 원본 이미지 파일 경로
	 * @param destName 썸네일로 저장될 이미지 파일 경로
	 * @param width 줄일 가로 길이 (-1일 경우 원본 사이즈)
	 * @param height 줄일 세로 길이 (-1일 경우 원본 사이즈)
	 * @return 썸네일 파일 이름
	 * @throws IOException
	 */
	public static String createThumb(String orgName, String destName, int width, int height) throws IOException {
		File orgFile = new File(orgName);
		File destFile = new File(destName);
		
		return createThumb(orgFile, destFile, width, height);
	}
	
	/**
	 * 썸네일 생성
	 * @param orgFile 원본 이미지 파일 객체
	 * @param destFile 썸네일로 저장될 이미지 파일 객체
	 * @param width 줄일 가로 길이 (-1일 경우 원본 사이즈)
	 * @param height 줄일 세로 길이 (-1일 경우 원본 사이즈)
	 * @return 썸네일 파일이름
	 * @throws IOException
	 */
	public static String createThumb(File orgFile, File destFile, int width, int height) throws IOException {
		Image srcImg = null;
		String suffix = orgFile.getName().substring(orgFile.getName().lastIndexOf('.') + 1).toLowerCase();

		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")) {
			srcImg = ImageIO.read(orgFile);
		} else {
			// JPEG Format
			srcImg = new ImageIcon(orgFile.toString()).getImage();
		}
		
		int srcWidth = srcImg.getWidth(null);
		int srcHeight = srcImg.getHeight(null);
		int destWidth = -1, destHeight = -1;

		if (width < 0) {
			destWidth = srcWidth;
		} else if (width > 0) {
			destWidth = width;
		}

		if (height < 0) {
			destHeight = srcHeight;
		} else if (height > 0) {
			destHeight = height;
		}
		
		Image imgTarget = srcImg.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
		int pixels[] = new int[destWidth * destHeight];
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, destWidth, destHeight, pixels, 0, destWidth);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			throw new IOException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (suffix.equals("bmp") || suffix.equals("png") || suffix.equals("gif")){
			int imageType = BufferedImage.TYPE_INT_RGB;
			if (suffix.equals("png")) imageType = BufferedImage.TYPE_INT_ARGB;
			BufferedImage destImg = new BufferedImage(destWidth, destHeight, imageType);
			destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);
			ImageIO.write(destImg, suffix, destFile);
			
		} else {
			BufferedImage destImg = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			destImg.setRGB(0, 0, destWidth, destHeight, pixels, 0, destWidth);
			ImageIO.write(destImg, "png", destFile);
		}

		return destFile.getName();
	}

}
