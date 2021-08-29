package ticlab1;

public class Message {
    private String symbol;
    private double pr;
    private String code;

    Message(String symbol, double pr) {
        this.symbol = symbol;
        this.pr = pr;
        this.code = "";
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPr() {
        return pr;
    }

    public void setPr(double pr) {
        this.pr = pr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
