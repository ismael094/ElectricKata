import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
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



    private String checkDates(List<Receipt> receipts) {
        if (receipts.size() == 1)
            return "Good";
        return "";
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
