package com.bnk.plus.commons.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtil {

	    private BufferedImage buffer;

	    public ImageUtil(File file) throws IOException {
	        buffer = ImageIO.read(file);
	    }

	    public ImageUtil(URL url) throws IOException {
	        buffer = ImageIO.read(url);
	    }

	    public ImageUtil(InputStream stream) throws IOException {
	        buffer = ImageIO.read(stream);
	    }

	    public ImageUtil(BufferedImage buffer) {
	        this.buffer = buffer;
	    }

	    public int getWidth() {
	        return buffer.getWidth();
	    }

	    public int getHeight() {
	        return buffer.getHeight();
	    }

	    public ImageUtil resize(int width, int height) {
	        BufferedImage dest = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = dest.createGraphics();
	        g.setComposite(AlphaComposite.Src);
	        g.drawImage(buffer, 0, 0, width, height, null);
	        g.dispose();
	        return new ImageUtil(dest);
	    }

	    public ImageUtil resize(int width) {
	        int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
	        return resize(width, resizedHeight);
	    }

	    public ImageUtil crop(int x, int y, int width, int height) {
	        BufferedImage dest = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = dest.createGraphics();
	        g.setComposite(AlphaComposite.Src);
	        g.drawImage(buffer, 0, 0, width, height, x, y, x + width, y + height,
	            null);
	        g.dispose();
	        return new ImageUtil(dest);
	    }

	    public void writeTo(OutputStream stream, String formatName)
	            throws IOException {
	        ImageIO.write(buffer, formatName, stream);
	    }
	    
	    public void writeTo(File file, String formatName)
	            throws IOException {
	        ImageIO.write(buffer, formatName, file);
	    }

	    public boolean isSuppoprtedImageFormat() {
	        return buffer != null ? true : false;
	    }

	    /**
	     * 
	     * <pre>
	     * 1. 설명 : 이미지 리사이즈시 비율을 유지 하도록 한다.
	     * 2. 동작 : 변경하고자 하는 사이즈, 변경하고자하는 방향(가로, 세로) 을 넣으면 변경하고자 하는 방향 반대에 길이를 리턴한다. 
	     * 3. Input : orgWidth : 변경전 가로 길이
	     *               orgHeight : 변경전 세로 길이
	     *               changeSize : 변경을 원하는 길이
	     *               gnb : 0 = 가로(width)
	     *                       1 = 세로(Height)
	     * 4. Output : gnb가 0이면 비율 유지된 "세로(Height)" 길이 리턴
	     *                 gnb가 1이면 비율 유지된 "가로(Width)" 길이 리턴
	     * 5. 수정내역
	     * ----------------------------------------------------------------
	     * 변경일                 작성자                                            변경내용
	     * ----------------------------------------------------------------
	     * 2015. 9. 23.     hj-kim							         최초작성
	     * ----------------------------------------------------------------
	     * </pre>
	     *
	     * @param orgWidth
	     * @param orgHeight
	     * @param changeSize
	     * @param gnb
	     * @return
	     */
	    public final static int STANDARD_WIDTH = 0;
	    public final static int STANDARD_HEIGHT = 1;
	    public static int getKeepMag(int orgWidth, int orgHeight, int changeSize, int gnb){
			int result=0;
			double mag = 0;
			
			int changeWayLeng=0;
			int changeWayReturnLeng=0;
			if(gnb == STANDARD_WIDTH) { 
				changeWayLeng = orgWidth; 
				changeWayReturnLeng = orgHeight;
			}
			else if(gnb == STANDARD_HEIGHT) {
				changeWayLeng = orgHeight; 
				changeWayReturnLeng = orgWidth;
			}
			
			if(changeWayLeng > changeSize ){
				mag = (double)changeSize / (double)changeWayLeng;
				result = (int)(mag*changeWayReturnLeng);
			}else{
				return changeWayReturnLeng;
			}
			
			return result;
	    }
}
