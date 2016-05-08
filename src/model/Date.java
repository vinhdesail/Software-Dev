package model;

public class Date {

	private final int year;
	private final int month;
	private final int day;
	private final String monthString;
	
	public Date(int theYear, int theMonth, int theDay) {
		year = theYear;
		month = theMonth;
		 switch (month) {
            case 1:  monthString = "January";
                     break;
            case 2:  monthString = "February";
                     break;
            case 3:  monthString = "March";
                     break;
            case 4:  monthString = "April";
                     break;
            case 5:  monthString = "May";
                     break;
            case 6:  monthString = "June";
                     break;
            case 7:  monthString = "July";
                     break;
            case 8:  monthString = "August";
                     break;
            case 9:  monthString = "September";
                     break;
            case 10: monthString = "October";
                     break;
            case 11: monthString = "November";
                     break;
            case 12: monthString = "December";
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

