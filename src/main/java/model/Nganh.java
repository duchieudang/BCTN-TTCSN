package model;

public class Nganh {
    private String maNganh;
    private String tenNganh;
    private Khoa khoa; // Tham chiếu đến lớp Khoa

    public Nganh() {
        super();
    }

    public Nganh(String maNganh, String tenNganh, Khoa khoa) {
        super();
        this.maNganh = maNganh;
        this.tenNganh = tenNganh;
        this.khoa = khoa;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }

    public Khoa getKhoa() {
        return khoa;
    }

    public void setKhoa(Khoa khoa) {
        this.khoa = khoa;
    }
}
