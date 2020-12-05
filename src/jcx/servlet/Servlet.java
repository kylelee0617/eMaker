/*
 * @(#)Servlet.java	1.00 2000/08/01
 *
 * Copyright 2000-20010 by Internet Information Corp.,
 * All rights reserved.
 * 
 */
package jcx.servlet;

import netscape.server.applet.*;

import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.io.*;
import java.util.*;
import jcx.util.upload;
import javax.servlet.http.*;

/**
 * �����O <code>Servlet</code> ���@Servlet ����¦���O,
 * �Z�O�Ҧ�Servlet �{�������n���~�Ӧ����O�t���o�i�C
 */

public abstract class Servlet extends HttpApplet {
	Hashtable formdata=new Hashtable();
	boolean file_upload=false;
	String filename="";
	Hashtable files=new Hashtable();
	Hashtable file_ins=new Hashtable();

    /**
     * �Ǧ^ Client �ݩI�s�� Servlet �������Ѽ�.
     *
     * @return  Hashtable represented the Parameter.
     */
	public Hashtable getFormData(){
		return formdata;
	}

	
    /**
     * �Ǧ^ Client �ݩI�s�� Servlet ����@�Ѽ�.
     *
     * @param   key   �ѼƦW��,�p�G�ѼƤ��s�b�h�Ǧ^ null.
     * @return  �Ѽƪ� value
     */
	public String getParameter(String key){
		return (String)formdata.get(key);
	}

	
    /**
     * �Ǧ^ Client ���ɮפW�Ǹ�ƪ� FileInputStream.
     *	
	 * <PRE>�ҿ��ɮפW�ǬO�H multipart/form-data �榡�W�Ǫ��覡,
	 * Exam.
	 *	<form ENCTYPE='multipart/form-data' action='/server-java/[servlet]' method=post>
	 *	<center>
	 *	<h2 align=center>�ɮפW��</h2>
	 *	<h4 align=center>�ϥΪ̥N��: Jony</h4>
	 *	<INPUT TYPE='hidden' NAME='id' VALUE='Jony'>
	 *	<TABLE border align=center>
	 *	<TD> �ɮצW��: </TD>
	 *	<TD COLSPAN=3><INPUT TYPE="file" NAME="uploadfile" size=50 ></TD></TR>
	 *	</TABLE>
	 *	<INPUT TYPE='submit' VALUE='�W��'></center>
	 *
	 *  �{���g�k
	 *  String user_id=getParameter("id");<BR>
	 *  FileInputStream fin=getFileInputStream("uploadfile");
	 *	</PRE>
     * @param   key   �ѼƦW��,�p�G�ѼƤ��s�b�h�Ǧ^ null.
     * @exception  IOException  if an I/O error occurs.
     * @return  �W�Ǹ�ƪ� FileInputStream.
	 *
	 */
	public final FileInputStream getFileInputStream(String key) throws IOException {

		ByteArrayOutputStream n1=(ByteArrayOutputStream)files.get(key+".[]");
		if(n1==null) return null;
		n1.flush();

		String filename=(String)files.get(key);
		FileOutputStream fout=new FileOutputStream(filename);
		fout.write(n1.toByteArray());
		fout.close();

		if(filename==null) return null;
		FileInputStream fin=new FileInputStream(filename);
		file_ins.put(key,fin);
		return fin;
	}

	public final InputStream getMemoryInputStream(String key) throws IOException {
		ByteArrayOutputStream n1=(ByteArrayOutputStream)files.get(key+".[]");
		if(n1==null) return null;
		n1.flush();
		ByteArrayInputStream ba=new ByteArrayInputStream(n1.toByteArray());
		return ba;
	}

    private final boolean upload_process() throws Exception {
		 Hashtable h1=new Hashtable();
		 boolean ret=upload.SaveBinaryFile(this,h1,files);
		 if(!ret) {   
			 return false;
		 } else {
			 formdata=h1;
			 return true;
		 }
	}
    private final void clean_up() throws Exception {
		for (Enumeration e = file_ins.keys() ; e.hasMoreElements() ;) {
			String key1=e.nextElement().toString();
			try{
				((FileInputStream)file_ins.get(key1)).close();
			} catch(Exception e1){}
		}
		
		for (Enumeration e = files.keys() ; e.hasMoreElements() ;) {
			String key1=e.nextElement().toString();
			try{
				File f1=new File((String)files.get(key1));
				f1.delete();
			} catch(Exception e1){}
		}
	}

	/**
     * Servlet �O�d�γ~�A�{���� run1 �}�l����A�����\ override
     *	
	 *
	 */
    public final void run() throws Exception {

     try {
        formdata=super.getFormData();
     }catch (Exception e) {
        if(e.toString().trim().equals("java.io.IOException: illegal content type")){
		  file_upload=true;
	      if(!upload_process()){
			getOutputStream().println("<html><h3>Upload fail ,please call service</h3>");
			return;
		  }
		}
     }

	  run1();
	  if(file_upload){
		clean_up();
	  }
	}
    /**
     * Servlet �{�����i�J�I�A�{���� run1 �}�l����
     *	
     * @exception  Exception  if any Exception.
     * @return  none
	 *
	 */
    abstract public void run1() throws Exception;
	
	/**
	*
	* �o�� Browser �ݸ�ƪ� InputStream.
	*
    * @return  Browser �ݸ�ƪ� InputStream
	*/
    public InputStream getInputStream() {
	  return super.getInputStream();
    }

	/**
	*
	* �o�� �� Browser �ݿ�X��ƪ� PrintStream.
	*
    * @return  Browser �ݸ�ƪ� PrintStream
	*/
    public PrintStream getOutputStream() {
	  return super.getOutputStream();
    }

    public void setHeader(String name, String value){
      return ;
    }

    public String getHeader(String name){
	  return "";
    }

	/**
	* �o�� Browser �ݪ� Cookie.
	*
    * @return  Cookie �}�C,�p�G Client �ݨS���e�X Cookie ,�h�^�ǹs���ת��}�C
	*/
    public Cookie[] getCookies(){
	  return super.getCookies();
	}

    /**
     * �� Client �ݰe�X Cookie,�����b returnNormalResponse �e����A�_�h�L��.
     *
     * @param   cookie   ���e�X�� Client �ݪ� Cookie.
     * @return  none
	 */
	public void addCookie(Cookie cookie){
	  super.addCookie(cookie);
      return ;
    }

    /**
     * �� Client �ݰe�X HTTP 200 O.K ���T���A�ó]�w content-type �� s1.
     *
     * @param   String   ���e�X�� Client �ݪ� content-type ,�p text/html , image/gif ����.
     * @return  �û��� true.
	 */

	public boolean returnNormalResponse(String s1){
		return super.returnNormalResponse(s1);
	}

    /**
     * �� Client �ݰe�X HTTP/1.0 401 Unauthorized ���T���A�ó]�w realm.
     *
     * @param   String   ���e�X�� Client �ݪ� realm.
     * @return  �û��� true
	 */
    public boolean returnAuthenticateResponse(String realm){
		return super.returnAuthenticateResponse(realm);
	}

    /**
     * �� Client �ݰe�X HTTP/1.0 401 Unauthorized ���T���A�ó]�w realm �� Java Composer.
     *
     * @param   none.
     * @return  �û��� true
	 */
    public boolean returnAuthenticateResponse(){
		return super.returnAuthenticateResponse();
	}
    /**
     * �� Client �ݰe�X HTTP/1.1 400 unknown reason ���T���A�ó]�w content-type �� s1.
     *
     * @param   String   ���e�X�� Client �ݪ� content-type ,�p text/html , image/gif ����.
     * @param   int      �O�d�A���ϥΡA�жǤJ�s�Y�i.
     * @return  �û��� true.
	 */
    public boolean returnErrorResponse(String s1,int ii){
		return super.returnErrorResponse(s1,ii);
	}


    /**
     * �Ǧ^���{�������|(�t�{���W��) �p /server-java/Hello , /servlet/HelloWorld.
     *
     * @return  �Ǧ^���{�������|(�t�{���W��),���t�Ѽ�.
	 */
    public String getPath(){
	 return super.getPath();
	}

    /**
     * �Ǧ^�I�s���{���� Method �p GET,POST.
     *
     * @return  �I�s���{���� Method �p GET,POST.
	 */
    public String getMethod(){
	 return super.getMethod();
	}

    /**
     * ���o Client �ݪ��ݩ� .
     *
     * @param   prop   �ǤJ ip.
     * @return  Client �ݪ� IP Address .
	 */
	public String getClientProperty(String prop){
		return super.getClientProperty(prop);
	}


}

