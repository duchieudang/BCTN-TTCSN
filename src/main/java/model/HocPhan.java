package model;

public class HocPhan {
    private String maHocPhan;
    private String tenHocPhan;
    private int tinChi; // Chuyển từ String sang int
    private String hocKy;
    private Nganh nganh;
    private String batbuoc; // Trường batbuoc

    public HocPhan(String maHocPhan, String tenHocPhan, int tinChi, String hocKy, Nganh nganh, String batbuoc) {
        super();
        this.maHocPhan = maHocPhan;
        this.tenHocPhan = tenHocPhan;
        this.tinChi = tinChi; // Khởi tạo tinChi với kiểu int
        this.hocKy = hocKy;
        this.nganh = nganh;
        this.batbuoc = batbuoc; // Khởi tạo batbuoc
    }

    public HocPhan() {
        super();
    }

    public String getMaHocPhan() {
        return maHocPhan;
    }

    public void setMaHocPhan(String maHocPhan) {
        this.maHocPhan = maHocPhan;
    }

    public String getTenHocPhan() {
        return tenHocPhan;
    }

    public void setTenHocPhan(String tenHocPhan) {
        this.tenHocPhan = tenHocPhan;
    }

    public int getTinChi() { // Phương thức getter cho tinChi với kiểu int
        return tinChi;
    }

    public void setTinChi(int tinChi) { // Phương thức setter cho tinChi với kiểu int
        this.tinChi = tinChi;
    }

    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    public Nganh getNganh() {
        return nganh;
    }

    public void setNganh(Nganh nganh) {
        this.nganh = nganh;
    }

    public String getBatbuoc() { // Phương thức getter cho batbuoc
        return batbuoc;
    }

    public void setBatbuoc(String batbuoc) { // Phương thức setter cho batbuoc
        this.batbuoc = batbuoc;
    }
}
