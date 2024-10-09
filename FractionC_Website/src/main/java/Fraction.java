public class Fraction {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    int Zähler, Nenner;

    public Fraction(int Zähler, int Nenner) {
        this.Zähler = Zähler;
        this.Nenner = Nenner;

        kuerzen();
    }

    private void kuerzen() {
        int GGT = ggtberechnen(Zähler, Nenner);
        Zähler /= GGT;
        Nenner /= GGT;
        if (Nenner < 0) {
            Zähler = -Zähler;
            Nenner = -Nenner;
        }
    }

    private int ggtberechnen(int a, int b) {
        while (b != 0) {
            int GGT = b;
            b = a % b;
            a = GGT;
        }
        return a;
    }

    public Fraction addieren(Fraction b2) {
        int neuerZähler = b2.Nenner * this.Zähler + b2.Zähler * this.Nenner;
        int neuerNenner = b2.Nenner * this.Nenner;
        return new Fraction(neuerZähler, neuerNenner);
    }

    public Fraction subtrahieren(Fraction b2) {
        int neuerZähler = this.Zähler * b2.Nenner - b2.Zähler * this.Nenner;
        int neuerNenner = b2.Nenner * this.Nenner;
        return new Fraction(neuerZähler, neuerNenner);
    }

    public Fraction multiplizieren(Fraction b2) {
        int neuerZähler = b2.Zähler * this.Zähler;
        int neuerNenner = b2.Nenner * this.Nenner;
        return new Fraction(neuerZähler, neuerNenner);
    }

    public Fraction dividieren(Fraction b2) {
        int neuerZähler = this.Zähler * b2.Nenner;
        int neuerNenner = this.Nenner * b2.Zähler;
        return new Fraction(neuerZähler, neuerNenner);
    }
}