package accounts;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * TimeManager class
 */
public class TimeManager {

    /**
     * Central Bank time
     */
    @Getter
    @Setter(AccessLevel.PUBLIC)
    LocalDate centralBankTime;

    /**
     * Time manager
     */
    public TimeManager() {
        centralBankTime = LocalDate.now();
    }

    /**
     * Rewinds days in Central Bank time
     * @param days
     * @throws IllegalArgumentException if var days is negative
     */
    public void rewindTimeDays(int days) {
        if (days < 0)
            throw new IllegalArgumentException("Days cannot be negative");
        centralBankTime = centralBankTime.plusDays(days);
    }

    /**
     * Check if is it last day of month
     * @return centralBankTime
     */
    public boolean isItLastDayOfMonth() {
        LocalDate firstDayOfMonth = LocalDate.of(centralBankTime.getYear(), centralBankTime.getMonth(), 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);
        return centralBankTime == lastDayOfMonth;
    }
}
