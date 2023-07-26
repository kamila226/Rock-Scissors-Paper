public class Round {
    int player1Element;
    int player2Element;

    int result;
    int roundNo;
    Round (int userElement, int computerElement) {
        this.player1Element = userElement;
        this.player2Element = computerElement;
        this.roundNo = 1;
        getResult(this.player1Element, this.player2Element);
    }

    void getResult(int userEl, int compEl) {
        if (userEl == compEl) result = 0;
        if (userEl == 0) {
            switch (compEl) {
                case (1), (3) -> result =  1;
                case (2), (4) -> result =  -1;
            }
        } else if (userEl == 1) {
            switch (compEl) {
                case (2), (3) -> result =  1;
                case (0), (4) -> result =  -1;
            }
        } else if (userEl == 2) {
            switch (compEl) {
                case (0), (4) -> result =  1;
                case (1), (3) -> result =  -1;
            }
        } else if (userEl == 3) {
            switch (compEl) {
                case (2), (4) -> result =  1;
                case (0), (1) -> result =  -1;
            }
        } else {
            switch (compEl) {
                case (0), (1) -> result =  1;
                case (3), (2) -> result =  -1;
            }
        }
    }
}
