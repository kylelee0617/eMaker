package jcx.jform.tools;
import javax.swing.*;
import jcx.jform.bdisplay;
import jcx.util.datetime;
import java.awt.*;

public class timer implements Runnable{
	Thread me=null;
	JLabel jl=null;

	/**
     * J-form ����椤����r����i�H�]�w �۩w�榡
	 * �����󬰤@�Ӥp����,�u�n�b�۩w�榡�[�J�@��
	 * <pre>
	 *  if(value==START){
	 *    jcx.jform.tools.timer.init(this);
	 *  } else {
	 *
	 *  } 
	 * </pre>
	 */
	public static void init(bdisplay bd){
		JLabel jl=bd.getLabel();
		new timer(jl);
	}

	/**
     * �O�d .
	 */

	public timer(JLabel label){
		jl=label;
		me=new Thread(this);
		me.start();
	}
	/**
     * �O�d .
	 */

	public void run(){
		while(Thread.currentThread()==me){
			try{
				jl.setText(datetime.getTime("h:m:s"));
				Thread.sleep(1000);
			} catch(Exception e){}
		}
	}
}
