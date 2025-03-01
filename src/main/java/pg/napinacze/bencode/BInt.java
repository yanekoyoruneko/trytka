package pg.napinacze.bencode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BInt extends BValue<Long> implements Comparable<BValue<Long>> {
    public BInt() {
    }

    public BInt(long value) {
        this.value = value;
    }

    @Override
    public int compareTo(BValue<Long> other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public byte[] toBytes() {
        return ("i" + this.value + "e").getBytes(StandardCharsets.UTF_8);
    }

    public static BInt parseBInt(Decoder decoder) throws IOException {
        if (decoder.read() != 'i') {
            throw new IllegalArgumentException("Int.parse: invalid format: expected 'i'");
        }
        byte sign = decoder.peek();
        if (sign == '-')
            decoder.read();
        BInt bint = new BInt(decoder.parseUintUntil('e'));
        if (sign == '-')
            bint.value = -bint.value;
        if (decoder.read() != 'e') {
            throw new IllegalArgumentException("Int.parse: invalid format: expected 'e'");
        }
        return bint;
    }
}
