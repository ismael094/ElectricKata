import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Electric_ {
    @Test
    public void with_one_receipt_should_return_good() {
        Receipt receipt = new Receipt(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        assertThat(checkDates(receipts)).isEqualTo("Good");
    }

    @Test
    public void with_two_receipt_should_return_good() {
        Receipt receipt = new Receipt(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        Receipt receipt2 = new Receipt(LocalDate.of(2019, Month.OCTOBER, 17),LocalDate.of(2019, Month.NOVEMBER, 17));
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        receipts.add(receipt2);
        assertThat(checkDates(receipts)).isEqualTo("Good");
    }

    @Test
    public void with_two_receipt_with_gap_should_return_gap() {
        Receipt receipt = new Receipt(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        Receipt receipt2 = new Receipt(LocalDate.of(2019, Month.NOVEMBER, 17),LocalDate.of(2019, Month.DECEMBER, 17));
        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        receipts.add(receipt2);
        assertThat(checkDates(receipts)).isEqualTo("Gap");
    }

    private String checkDates(List<Receipt> receipts) {
        if (receipts.size() <= 1)
            return "Good";
        for (int i = 0; i < receipts.size() -1; i++) {
            if (gap(receipts.get(i).getFinish(),receipts.get(i+1).getStart()))
                return "Gap";
        }
        return "Good";
    }

    private boolean gap(LocalDate finishDate, LocalDate startDate) {
        if ( ChronoUnit.MONTHS.between(finishDate, startDate) >= 1)
            return true;
        return false;
    }

    class Receipt {
        private LocalDate start;
        private LocalDate finish;

        public Receipt(LocalDate start, LocalDate finish) {
            this.start = start;
            this.finish = finish;
        }

        public LocalDate getStart() {
            return start;
        }

        public LocalDate getFinish() {
            return finish;
        }
    }
}
