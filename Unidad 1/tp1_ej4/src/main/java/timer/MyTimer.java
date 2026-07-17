package timer;

import org.joda.time.Instant;
import org.joda.time.Period;

public class MyTimer {


    private Instant start, end;

    public MyTimer() {
        this.start = new Instant();
    }

    public MyTimer(long start) {
        this.start = new Instant(start);
    }

    public void stop() {
        this.end = new Instant();
    }

    public void stop(long end) {
        this.end = new Instant(end);
        if(this.end.isBefore(this.start)) {
            throw new RuntimeException("El final debe ser despues del inicio");
        }

    }

    @Override
    public String toString() {
        Period period = new Period(start, end);
        return "(%d ms) %d dia %d hs %d min %.3f s".formatted(end.getMillis() - start.getMillis(), period.getDays(), period.getHours(), period.getMinutes(), period.getSeconds() + period.getMillis() / 1000.0);
        //return period.toString();
    }

    public long getElapsedTime(){
        return this.end.getMillis() - this.start.getMillis();
    }

}
