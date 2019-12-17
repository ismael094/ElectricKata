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
        Bill receipt = new Bill(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        List<Bill> receipts = new ArrayList<>();
        receipts.add(receipt);
        assertThat(receiptTimeChecker(receipts)).isEqualTo(STATE.GOOD);
    }

    @Test
    public void with_two_receipt_should_return_good() {
        Bill receipt = new Bill(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        Bill receipt2 = new Bill(LocalDate.of(2019, Month.OCTOBER, 17),LocalDate.of(2019, Month.NOVEMBER, 17));
        List<Bill> receipts = new ArrayList<>();
        receipts.add(receipt);
        receipts.add(receipt2);
        assertThat(receiptTimeChecker(receipts)).isEqualTo(STATE.GOOD);
    }

    @Test
    public void with_two_receipt_with_gap_should_return_gap() {
        Bill receipt = new Bill(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        Bill receipt2 = new Bill(LocalDate.of(2019, Month.NOVEMBER, 17),LocalDate.of(2019, Month.DECEMBER, 17));
        List<Bill> receipts = new ArrayList<>();
        receipts.add(receipt);
        receipts.add(receipt2);
        assertThat(receiptTimeChecker(receipts)).isEqualTo(STATE.GAP);
    }

    @Test
    public void with_two_receipt_with_overlap_should_return_overlap() {
        Bill receipt = new Bill(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.OCTOBER, 17));
        Bill receipt2 = new Bill(LocalDate.of(2019, Month.SEPTEMBER, 17),LocalDate.of(2019, Month.DECEMBER, 17));
        List<Bill> bills = new ArrayList<>();
        bills.add(receipt);
        bills.add(receipt2);
        assertThat(receiptTimeChecker(bills)).isEqualTo(STATE.OVERLAP);
    }

    private STATE receiptTimeChecker(List<Bill> bills) {
        for (int i = 0; i < bills.size() -1; i++) {
            if (gap(bills.get(i).getFinish(),bills.get(i+1).getStart()))
                return STATE.GAP;
            if (overlap(bills.get(i).getFinish(),bills.get(i+1).getStart()))
                return STATE.OVERLAP;
        }
        return STATE.GOOD;
    }

    private boolean overlap(LocalDate finishDate, LocalDate startDate) {
        if ( ChronoUnit.DAYS.between(finishDate, startDate) < 0)
            return true;
        return false;
    }

    private boolean gap(LocalDate finishDate, LocalDate startDate) {
        if ( ChronoUnit.DAYS.between(finishDate, startDate) >= 1)
            return true;
        return false;
    }

    class Bill {
        private LocalDate start;
        private LocalDate finish;

        public Bill(LocalDate start, LocalDate finish) {
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

    class User{
        private List<Bill> receipts;
        private String name;

        public User(List<Bill> receipts) {
            this.receipts = receipts;
        }
    }
}
