package library;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class Common implements Item{
	private String title;
	private Date due;
	private Patron checkedOutTo; 
	private double replacementCost; 
	private int duration;
	
	public Common(String thisTitle, Date now, Patron person, double cost, int time) {
		title = thisTitle;
		due = now; 
		checkedOutTo = person;
		replacementCost = cost; 
		duration = time; 
	}
	
	 public void checkOut(Patron p, Date now)
	  {
	    if (!isCheckedOut())
	    {
	      int checkOutDays = 21;
	      
	      // use a GregorianCalendar to figure out the Date corresponding to
	      // midnight, 21 days from now
	      GregorianCalendar cal = new GregorianCalendar();
	      cal.setTime(now);
	      cal.add(Calendar.DAY_OF_YEAR, checkOutDays);
	      
	      // always set to 11:59:59 pm on the day it's due
	      cal.set(Calendar.HOUR_OF_DAY, 23);
	      cal.set(Calendar.MINUTE, 59);
	      cal.set(Calendar.SECOND, 59);     
	      
	      // convert back to Date object
	      due = cal.getTime();
	      
	      checkedOutTo = p;      
	    }
	  }
	
	public boolean checkedOut() {
		return due != null;
	}
	
	public void checkIn() {
		if(checkedOut()) {
			checkedOutTo = null;
			due = null;
		}	
	}
	
	public boolean overdue(Date now) {
		if(!checkedOut())
			return false;
		return now.after(due);
	}
	
	public int compare(Item item) {
		return title.compareTo(item.getTitle());
	}
	
	public String getTitle() {
		return title;
	}
	
	public Date dueDate() {
		return due;
	}
	
	public Patron getPatron() {
		return checkedOutTo;
	}
	
	public double fines(Date now) {
		if(checkedOut() && overdue(now)) {
			double over = now.getTime() - due.getTime();
			
			int time = 24*60*60*1000;
			int late = (int) Math.ceil(over / time);
			
			if(late < 6)
				return Math.min((double)late, replacementCost);
			else
				return Math.min(5.0 + (late - 5) * .50, replacementCost);
 		}
		else 
			return 0;
	}
	
	public void renew(Date now) {
		if (isCheckedOut() && !isOverdue(now) && renewalCount < 2)
	    {
	      checkOut(checkedOutTo, due);
	      renewalCount += 1;
	    }    
	}
	
}
