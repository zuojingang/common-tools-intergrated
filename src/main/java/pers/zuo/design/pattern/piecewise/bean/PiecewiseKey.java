package pers.zuo.design.pattern.piecewise.bean;

import java.util.Objects;

public class PiecewiseKey {

    private final long from;
    private final long to;

    public PiecewiseKey(long from, long to) {
        super();
        this.from = from;
        this.to = to;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PiecewiseKey that = (PiecewiseKey) o;
        return from == that.from &&
                to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}