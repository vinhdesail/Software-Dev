package model;

public class Date {

	private final int year;
	private final int month;
	private final int day;
	private final String monthString;
	private static final int JANUARY = 1;
	private static final int FEBRUARY = 2;
	private static final int MARCH = 3;
	private static final int APRIL = 4;
	private static final int MAY = 5;
	private static final int JUNE = 6;
	private static final int JULY = 7;
	private static final int AUGEST = 8;
	private static final int SEPTEMBER = 9;
	private static final int OCTOBER = 10;
	private static final int NOVEMBER = 11;
	private static final int DECEMBER = 12;
	
	public Date(int theYear, int theMonth, int theDay) {
		year = theYear;
		month = theMonth;
		 switch (month) {
            case JANUARY:  monthString = "January";
                     break;
            case FEBRUARY:  monthString = "February";
                     break;
            case MARCH:  monthString = "March";
                     break;
            case APRIL:  monthString = "April";
                     break;
            case MAY:  monthString = "May";
                     break;
            case JUNE:  monthString = "June";
                     break;
            case JULY:  monthString = "July";
                     break;
            case AUGEST:  monthString = "August";
                     break;
            case SEPTEMBER:  monthString = "September";
                     break;
            case OCTOBER: monthString = "October";
                     break;
            case NOVEMBER: monthString = "November";
                     break;
            case DECEMBER: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
		 }
		day = theDay;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(monthString);
		sb.append(" ");
		sb.append(day);
		sb.append(", ");
		sb.append(year);
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return year + month + day;
	}
	
	@Override
	public boolean equals(Object theOther) {
		if (!this.getClass().equals(theOther.getClass())) {
			return false;
		}
		Date otherDate = (Date) theOther;
		if (this.year != otherDate.year) {
			return false;
		} else if (this.month != otherDate.month) {
			return false;
		} else if (this.day != otherDate.day) {
			return false;
		}
		return true;
	}
	
	/**
	 * Determines whether this Date is before some other Date.
	 * @param otherDate
	 * @return true if this Date is before otherDate, false otherwise
	 */
	public boolean isBefore(Date otherDate) {
		if (this.year < otherDate.year) {
			return true;
		} else if (this.year == otherDate.year) {
			if (this.month < otherDate.month) {
				return true;
			} else if (this.month == otherDate.month) {
				if (this.day < otherDate.day) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Determines whether this Date is after some other Date.
	 * @param otherDate
	 * @return true if this Date is after otherDate, false otherwise
	 */
	public boolean isAfter(Date otherDate) {
		if (this.year > otherDate.year) {
			return true;
		} else if (this.year == otherDate.year) {
			if (this.month > otherDate.month) {
				return true;
			} else if (this.month == otherDate.month) {
				if (this.day > otherDate.day) {
					return true;
				}
			}
		}
		return false;
	}
}