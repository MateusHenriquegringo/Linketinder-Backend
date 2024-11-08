package dao.likes;


class Like {

    private long from;
    private long to;

    Like(long from, long to) {
        this.from = from;
        this.to = to;
    }

    long getFrom() {
        return from
    }

    void setFrom(long from) {
        this.from = from
    }

    long getTo() {
        return to
    }

    void setTo(long to) {
        this.to = to
    }
}
