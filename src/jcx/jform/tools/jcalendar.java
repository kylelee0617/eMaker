package jcx.jform.tools;

import jcx.jform.bdisplay;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.text.DateFormat;
import javax.swing.border.EtchedBorder;
import cLabel;

/**
 * bDateChooser is a simple Date choosing component with similar functionality
 * to JFileChooser and JColorChooser. It can be used as a component, to 
 * be inserted into a client layout, or can display it's own Dialog
 * through use of the {@link #showDialog(Component, String) showDialog} method.
 * <p>
 * bDateChooser can be initialized to the current date using the no argument
 * constructor, or initialized to a predefined date by passing an instance
 * of Calendar to the constructor.<p>
 * Using the bDateChooser dialog works in a similar manner to JFileChooser
 * or JColorChooser. The {@link #showDialog(Component, String) showDialog} method
 * returns an int that equates to the public variables ACCEPT_OPTION, CANCEL_OPTION
 * or ERROR_OPTION.<p>
 * <tt>
 * bDateChooser chooser = new bDateChooser();<br>
 * if (chooser.showDialog(this, "Select a date...") == bDateChooser.ACCEPT_OPTION) {<br>
 * &nbsp;&nbsp;Calendar selectedDate = chooser.getSelectedDate();<br>
 * &nbsp;&nbsp;// process date here...<br>
 * }<p>
 * To use bDateChooser as a component within a GUI, users should subclass
 * bDateChooser and override the {@link #acceptSelection() acceptSelection} and
 * {@link #cancelSelection() cancelSelection} methods to process the 
 * corresponding user selection.<p>
 * The current date can be retrieved by calling {@link #getSelectedDate() getSelectedDate}
 * method.
 */

public class jcalendar extends JComponent implements ActionListener { //, bDaySelectionListener {
	
	/**
	 * Value returned by {@link #showDialog(Component, String) showDialog} upon an error.
	 */
	public static final int ERROR_OPTION = 0;
	/**
	 * Value returned by {@link #showDialog(Component, String) showDialog} upon pressing the "okay" button.
	 */
	public static final int ACCEPT_OPTION = 2;
	/**
	 * Value returned by {@link #showDialog(Component, String) showDialog} upon pressing the "cancel" button.
	 */
	public static final int CANCEL_OPTION = 4;
	private int currentDay;
	private int currentMonth;
	private int currentYear;
	private JLabel dateText;
	private Calendar calendar;
	private Date date;
	private JButton previousYear;
	private JButton previousMonth;
	private JButton nextMonth;
	private JButton nextYear;
	private JButton okay;
	private JButton cancel;
	private int returnValue;
	private JDialog dialog;
	private JPanel days;
	cLabel c1=null;
	String format="yymmdd";
    static int base_x, base_y;    // 以日期輸入欄作 Base


	/**
     * J-form 的表單中的文字物件可以設定 自定格式
	 * 本物件為一個日曆,只要在自定格式加入一行.
	 * <pre>
	 *  if(value==START){
	 *    jcx.jform.tools.jcalendar.init(this,"field1");
	 *  } else {
	 *
	 *  } 
	 * </pre>
	 * 如果物件的標題第一個字是 & ,以小 button 呼叫月曆視窗
     * @param   bd 傳入 this 即可.
     * @param   name 即時更動之物件名稱.
     * @return  none.
	 */
	public static void init(bdisplay bd,String name){
		init(bd,name,"yymmdd");
	}
	/**
     * J-form 的表單中的文字物件可以設定 自定格式
	 * 本物件為一個日曆,只要在自定格式加入一行.
	 * <pre>
	 *  if(value==START){
	 *    jcx.jform.tools.jcalendar.init(this,"field1");
	 *  } else {
	 *
	 *  } 
	 * </pre>
	 * 如果物件的標題第一個字是 & ,以小 button 呼叫月曆視窗
     * @param   bd 傳入 this 即可.
     * @param   name 即時更動之物件名稱.
     * @param   format 傳回的日期格式 yymmdd YYYYmmdd yy/mm/dd 皆可.
     * @return  none.
	 */

	public static void init(bdisplay bd,String name,String format){
        JLabel base = bd.getcLabel(name);          //  日期輸入欄
        base_x = base.getX() + base.getWidth();    //  取得 日期輸入欄 的 位置, 用以作 JDialog 顯示位置的參考.
        base_y = base.getY();
		init(bd,name,format,Calendar.getInstance());
	}
	/**
     * J-form 的表單中的文字物件可以設定 自定格式
	 * 本物件為一個日曆,只要在自定格式加入一行.
	 * <pre>
	 *  if(value==START){
	 *    jcx.jform.tools.jcalendar.init(this,"field1");
	 *  } else {
	 *
	 *  } 
	 * </pre>
	 * 如果物件的標題第一個字是 & ,以小 button 呼叫月曆視窗
     * @param   bd 傳入 this 即可.
     * @param   name 即時更動之物件名稱.
     * @param   format 傳回的日期格式 yymmdd YYYYmmdd yy/mm/dd 皆可.
     * @param   c 預設的日期.
     * @return  none.
	 */

	public static void init(bdisplay bd,String name,String format,Calendar c){
		final JLabel jl=bd.getLabel();
		String caption=jl.getText();
		if(caption.startsWith("&")){
			final String format1=format;
			final String name1=name;
			final Calendar c1=c;
			final bdisplay bd1=bd;
			JButton jbt=new JButton(caption.substring(1));
			jbt.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					jcalendar j1=new jcalendar(c1);
					j1.format=format1;
					j1.c1=bd1.getcLabel(name1);
					j1.showDialog(jl,"Calendar");
				}
			});
			jl.removeAll();
			jl.setLayout(new BorderLayout());
			jl.add(jbt,BorderLayout.CENTER);
			return;
		}
		jl.setText("");
		jcalendar j1=new jcalendar(c);
		j1.format=format;
		j1.c1=bd.getcLabel(name);
		jl.removeAll();
		jl.setLayout(new BorderLayout());
		jl.add(j1,BorderLayout.CENTER);

	}
	/**
	 * This constructor creates a new instance of bDateChooser initialized to
	 * the current date.
	 */
	public jcalendar() {
		this(Calendar.getInstance());
	}
	
	/**
	 * Creates a new instance of bDateChooser initialized to the given Calendar.
	 */
	public jcalendar(Calendar c) {
		super();
		this.calendar = c;
		this.calendar.setLenient(true);
		setup();
	}
	
	private void setup() {
		GridBagLayout g = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JPanel header = new JPanel(g);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(2, 0, 2, 0);
		previousYear = (JButton) header.add(new JButton("<<"));
		previousYear.addActionListener(this);
		previousYear.setToolTipText("Previous Year");
		g.setConstraints(previousYear, c);
		previousMonth = (JButton) header.add(new JButton("<"));
		previousMonth.addActionListener(this);
		previousMonth.setToolTipText("Previous Month");
		c.gridx++;
		g.setConstraints(previousMonth, c);
		dateText = (JLabel) header.add(new JLabel("", JLabel.CENTER));
		dateText.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        dateText.setFont(new Font("Dialog", Font.BOLD, 16));
		c.gridx++;
		c.weightx = 1.0;
		c.fill = c.BOTH;
		g.setConstraints(dateText, c);
		nextMonth = (JButton) header.add(new JButton(">"));
		nextMonth.addActionListener(this);
		nextMonth.setToolTipText("Next Month");
		c.gridx++;
		c.weightx = 0.0;
		c.fill = c.NONE;
		g.setConstraints(nextMonth, c);
		nextYear = (JButton) header.add(new JButton(">>"));
		nextYear.addActionListener(this);
		nextYear.setToolTipText("Next Year");
		c.gridx++;
		g.setConstraints(nextYear, c);
		
		updateCalendar(calendar);
		
//		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		okay = (JButton) buttons.add(new JButton("Okay"));
//		okay.addActionListener(this);
//		cancel = (JButton) buttons.add(new JButton("Cancel"));
//		cancel.addActionListener(this);
		
		setLayout(new BorderLayout());
		add("North", header);
		add("Center", days);
//		add("South", buttons);
	}
	
	private void updateCalendar(Calendar c) {
		if (days != null)
			remove(days);
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		currentMonth = calendar.get(Calendar.MONTH);
		currentYear = calendar.get(Calendar.YEAR);
		days = new JPanel(new GridLayout(7, 7));
		Calendar setup = (Calendar) calendar.clone();
		setup.set(Calendar.DAY_OF_WEEK, setup.getFirstDayOfWeek());
		int lastLayoutPosition = 0;
		for (int i = 0; i < 7; i++) {
			int dayInt = setup.get(Calendar.DAY_OF_WEEK);
			if (dayInt == Calendar.MONDAY)
				days.add(new JLabel("一",SwingConstants.CENTER));
			if (dayInt == Calendar.TUESDAY)
				days.add(new JLabel("二",SwingConstants.CENTER));
			if (dayInt == Calendar.WEDNESDAY)
				days.add(new JLabel("三",SwingConstants.CENTER));
			if (dayInt == Calendar.THURSDAY)
				days.add(new JLabel("四",SwingConstants.CENTER));
			if (dayInt == Calendar.FRIDAY)
				days.add(new JLabel("五",SwingConstants.CENTER));
			if (dayInt == Calendar.SATURDAY)
				days.add(new JLabel("六",SwingConstants.CENTER));
			if (dayInt == Calendar.SUNDAY)
				days.add(new JLabel("日",SwingConstants.CENTER));
			setup.roll(Calendar.DAY_OF_WEEK, true);
			lastLayoutPosition++;
		}
		setup = (Calendar) calendar.clone();
		setup.set(Calendar.DAY_OF_MONTH, 1);
		int first = setup.get(Calendar.DAY_OF_WEEK);
		for (int i = 0; i < (first - 1); i++) {
			days.add(new JLabel(""));
			lastLayoutPosition++;
		}
		setup.set(Calendar.DAY_OF_MONTH, 1);
		for (int i = 0; i < setup.getActualMaximum(setup.DAY_OF_MONTH); i++) {
			JButton button = new JButton(""+setup.get(setup.DAY_OF_MONTH));
			button.addActionListener(this);
            button.addMouseListener(new MouseAdapter()    {public void mouseClicked(MouseEvent e)    // Mouse點2下, 關閉視窗.
                { 
                if (e.getClickCount() == 2)    dialog.dispose();
                }});
			days.add(button);
			setup.roll(setup.DAY_OF_MONTH, true);
			lastLayoutPosition++;
		}
		for (int i = lastLayoutPosition; i < 49; i++)
			days.add(new JLabel(""));
		add("Center", days);
		validate();
		if (dialog != null)
			dialog.pack();
		setup = null;
		updateLabel();
	}
	
	private void updateLabel() {
//		Date date = calendar.getTime();
//		dateText.setText(DateFormat.getInstance().format(date));
		int dd=(calendar.get(calendar.YEAR)*10000+(calendar.get(calendar.MONTH)+1)*100+calendar.get(calendar.DATE))-19110000; 
		dateText.setText(getToday(dd,format));
	}
	
	/**
	 * Returns the currently selected Date in the form of a java.util.Calendar
	 * object. Typically called adter receipt of an {@link #ACCEPT_OPTION ACCEPT_OPTION}
	 * (using the {@link #showDialog(Component, String) showDialog} method) or
	 * within the {@link #acceptSelection() acceptSelection} method (using the
	 * bDateChooser as a component.)<p>
	 * @return java.util.Calendar The selected date in the form of a Calendar object.
	 */
	private Calendar getSelectedDate() {
		return calendar;
	}
	
	/**
	 * Pops up a Date chooser dialog with the supplied <i>title</i>, centered
	 * about the component <i>parent</i>.
	 * @return int An integer that equates to the static variables <i>ERROR_OPTION</i>, <i>ACCEPT_OPTION</i> or <i>CANCEL_OPTION</i>.
	 */
	private int showDialog(Component parent, String title) {
		returnValue = ERROR_OPTION;
		Frame frame = parent instanceof Frame ? (Frame) parent : (Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
		dialog = new JDialog(frame, title, true);
		dialog.getContentPane().add("Center", this);
		dialog.pack();

        int x, y;
        int scW = Toolkit.getDefaultToolkit().getScreenSize().width;     //  螢幕的"寬"
        int scH = Toolkit.getDefaultToolkit().getScreenSize().height;    //  螢幕的"高"
        if (base_x > scW / 2)
            x = (scW / 2 - dialog.getWidth()) / 2;              // 將 JDialog 放在 ~~~ 左邊
            else
            x = scW / 2 + (scW / 2 - dialog.getWidth()) / 2;    // 將 JDialog 放在 ~~~ 右邊
        y = (scH - dialog.getHeight()) / 2;

		dialog.setLocation(x, y);
//		dialog.setLocationRelativeTo(parent);
		dialog.show();
		return returnValue;
	}
	
	/**
	 * This method is called when the user presses the "okay" button. Users
	 * must subclass bDateChooser and override this method to use bDateChooser
	 * as a Component and receive accept selections by the user.
	 */
	private void acceptSelection() {
	}
	
	/**
	 * This method is called when the user presses the "cancel" button. Users
	 * must subclass bDateChooser and override this method to use bDateChooser
	 * as a Component and receive cancel selections by the user.
	 */
	private void cancelSelection() {
	}
	
	/**
	 * Used to process events from the previous month, previous year, next month, next year,
	 * okay and cancel buttons. Users should call super.actionPerformed(ActionEvent) if overriding
	 * this method.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okay) {
			returnValue = ACCEPT_OPTION;
			if (dialog != null)
				dialog.dispose();
			acceptSelection();
		}else if (e.getSource() == cancel) {
			returnValue = CANCEL_OPTION;
			if (dialog != null)
				dialog.dispose();
			cancelSelection();
		}else if (e.getSource() == previousYear) {
			calendar.roll(Calendar.YEAR, -1);
			updateCalendar(calendar);
		}else  if (e.getSource() == previousMonth) {
			calendar.roll(Calendar.MONTH, -1);
			updateCalendar(calendar);
		}else if (e.getSource() == nextMonth) {
			calendar.roll(Calendar.MONTH, 1);
			updateCalendar(calendar);
		} else if (e.getSource() == nextYear) {
			calendar.roll(Calendar.YEAR, 1);
			updateCalendar(calendar);
		} else{
			JButton j1=(JButton)e.getSource();
			try{
			daySelected(Integer.parseInt(j1.getText()));
			} catch(Exception e1){}
		}
	}
	
	/**
	 * Used to process day selection events from the user. This method resets
	 * resets the Calendar object to the selected day. Subclasses should make a call
	 * to super.daySelected() if overriding this method.
	 */
	private void daySelected(int d) {
		calendar.set(Calendar.DAY_OF_MONTH, d);
		updateLabel();
		currentDay = d;
		int dd=(calendar.get(calendar.YEAR)*10000+(calendar.get(calendar.MONTH)+1)*100+calendar.get(calendar.DATE))-19110000; 
		c1.setValue(getToday(dd,format));
	}

	private static String getToday(int today,String str)	{
		String err=""+today;
		
		int year=getYear(today);
		int month=getMonth(today);
		int date=getDate(today);
		
		String out_str="";
		
		String yy=String.valueOf(year);
		String YY=String.valueOf(year+1911).substring(String.valueOf(year+1911).length()-2);
		String YYYY=String.valueOf(year+1911);
		String mm="";
		String dd="";
		if(month<10)  mm="0"+String.valueOf(month);
		else  mm=""+month;
		if(date<10) dd="0"+String.valueOf(date);
		else  dd=""+date;

		if (str.equals("yy/mm/dd"))        out_str=yy+"/"+mm+"/"+dd;
		else if (str.equals("mm/dd/yy"))   out_str=mm+"/"+dd+"/"+yy;
		else if (str.equals("yymmdd"))	   out_str=yy+mm+dd;
		else if (str.equals("mmddyy"))	   out_str=mm+dd+yy;
		else if (str.equals("yymm"))	   out_str=yy+mm;
	    else if (str.equals("yy/mm"))	   out_str=yy+"/"+mm;

		else if (str.equals("mmdd"))	   out_str=mm+dd;

		else if (str.equals("yyyy/mm/dd"))	out_str=YYYY+"/"+mm+"/"+dd;
		else if (str.equals("YYYY/mm/dd"))	out_str=YYYY+"/"+mm+"/"+dd;
		else if (str.equals("mm/dd/YYYY"))	out_str=mm+"/"+dd+"/"+YYYY;
		else if (str.equals("yyyymmdd"))	out_str=YYYY+mm+dd;
		else if (str.equals("YYYYmmdd"))	out_str=YYYY+mm+dd;
		else if (str.equals("mmddYYYY"))	out_str=mm+dd+YYYY;
		else if (str.equals("YY/mm/dd"))	out_str=YY+"/"+mm+"/"+dd;
		else if (str.equals("mm/dd/YY"))	out_str=mm+"/"+dd+"/"+YY;
		else if (str.equals("YYmmdd"))	    out_str=YY+mm+dd;
	    else if (str.equals("mmddYY"))	    out_str=mm+dd+YY;
	    else if (str.equals("YY,mm,dd"))	out_str=YY+","+mm+","+dd;
	    else if (str.equals("mm,dd,YY"))	out_str=mm+","+dd+","+YY;
		else if (str.equals("YYmm"))	    out_str=YY+mm;
		else if (str.equals("YY/mm"))	    out_str=YY+"/"+mm;
		else if (str.equals("YYYYmm"))	    out_str=YYYY+mm;
		else if (str.equals("YYYY/mm"))	    out_str=YYYY+"/"+mm;
	    else
		    out_str=err;
	    return out_str;
	}
	private static int getYear(int day)	{
		return Integer.parseInt(getYear(day+""));
	}

	private static String getYear(String day)	{
		String err="0";
		if(day.length()==5) 
			return day.substring(0,1);
		else if(day.length()==6)
			return day.substring(0,2);
		else if(day.length()==7)
			return day.substring(0,3);
		else if(day.length()==8)
			return day.substring(0,4);
		else 
			return err;
	}
	private static int getMonth(int day)	{
		return Integer.parseInt(getMonth(day+""));
	}

	private static String getMonth(String day)	{
		String err="0";
		if(day.length()==5)
			return day.substring(1,3);
		else if(day.length()==6)
			return day.substring(2,4);
		else if(day.length()==7)
			return day.substring(3,5);
		else if(day.length()==8)
			return day.substring(4,6);
		else 
			return err;
	}
	private static int getDate(int day)	{
		return Integer.parseInt(getDate(day+""));
	}
	private static String getDate(String day)	{
		String err="0";
		
		if(day.length()==5)
			return day.substring(3,5);
		else if(day.length()==6)
			return day.substring(4,6);
		else if(day.length()==7)
			return day.substring(5,7);
		else if(day.length()==8)
			return day.substring(6,8);
		else 
			return err;
	}
}
/*
class DayButton extends JButton implements ActionListener {
	
	public int day;
	private Vector listeners;
	
	public DayButton(int d) {
		super((new Integer(d)).toString());
		this.day = d;
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		
//		c1.setValue("");
		if (listeners != null) {
			for (int i = 0; i < listeners.size(); i++) {
				((bDaySelectionListener) listeners.elementAt(i)).daySelected(day);
			}
		}
	}
	
	public void addbDaySelectionListener(bDaySelectionListener l) {
		if (listeners == null)
			listeners = new Vector(1, 1);
		listeners.addElement(l);
	}
	
	public void removebDaySelectionListener(bDaySelectionListener l) {
		if (listeners != null)
			listeners.removeElement(l);
	}
	
	public void removeAllListeners() {
		listeners = new Vector(1,1);
	}
}
*/