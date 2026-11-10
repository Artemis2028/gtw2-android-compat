package com.invertbit.games.gtw.sim;

/** One arsenal row: totals of 5 / 10 / 15 MT (original {@code a.Cj}). */
public final class YieldRow {
    public int m5;
    public int m10;
    public int m15;
    public final WeaponKind kind;

    public YieldRow(WeaponKind kind) {
        this.kind = kind;
    }

    public YieldRow copy() {
        YieldRow row = new YieldRow(kind);
        row.m5 = m5;
        row.m10 = m10;
        row.m15 = m15;
        return row;
    }

    public int totalMt() {
        return m5 + m10 + m15;
    }

    public int count() {
        return (m5 / 5) + (m10 / 10) + (m15 / 15);
    }

    public void add5(int mt) {
        m5 += mt;
    }

    public void add10(int mt) {
        m10 += mt;
    }

    public void add15(int mt) {
        m15 += mt;
    }

    @Override
    public String toString() {
        return "[m5]=" + m5 + " [m10]=" + m10 + " [m15]=" + m15;
    }
}
