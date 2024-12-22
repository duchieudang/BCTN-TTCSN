package model;

public class LopHoc {
    private String maLopHoc;
    private String tenLopHoc;
    private String toaNha;
    private String phong;

    public LopHoc() {
        super();
    }

    public LopHoc(String maLopHoc, String tenLopHoc, String toaNha, String phong) {
        super();
        this.maLopHoc = maLopHoc;
        this.tenLopHoc = tenLopHoc;
        this.toaNha = toaNha;
        this.phong = phong;
    }

    public String getMaLopHoc() {
        return maLopHoc;
    }

    public void setMaLopHoc(String maLopHoc) {
        this.maLopHoc = maLopHoc;
    }

    public String getTenLopHoc() {
        return tenLopHoc;
    }

    public void setTenLopHoc(String tenLopHoc) {
        this.tenLopHoc = tenLopHoc;
    }

    public String getToaNha() {
        return toaNha;
    }

    public void setToaNha(String toaNha) {
        this.toaNha = toaNha;
    }

    public String getPhong() {
        return phong;
    }

    public void setPhong(String phong) {
        this.phong = phong;
    }
}
