package library;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A Book is a library item that can be checked out for 21 days and renewed at most twice.
 * If overdue, the fine is .25 per day.
 */
public class Book extends Common
{
  
  /**
   * Number of times the item has been renewed for the current patron.
   */
  private int renewalCount;
  
  /**
   * Constructs a book with the given title.
   * @param givenTitle
   */
  public Book(String givenTitle)
  {
    super(givenTitle, null, null, 0.0, 21);
    renewalCount = 0;
  }
  

  public void checkIn() {
		if(checkedOut()) {
			super.checkOut(null, null);
			renewalCount = 0;
		}	
	}
  
  public void renew(Date now) {
		if (isCheckedOut() && !isOverdue(now) && renewalCount < 2)
	    {
	      checkOut(super.getPatron(), now);
	      renewalCount += 1;
	    }    
	}

  

}
