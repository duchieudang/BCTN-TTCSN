package model;

import java.util.Date;

public class MonHoc {
    private String maMonHoc;
    private String tenMonHoc;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int tietBatDau; // Đổi từ Time sang int
    private int tietKetThuc; // Đổi từ Time sang int
    private String lichHoc;
    private HocPhan hocPhan;
    private GiangVien giangVien;
    private Lop lop;
    private LopHoc lopHoc;

    public MonHoc() {
        super();
    }

    public MonHoc(String maMonHoc, String tenMonHoc, Date ngayBatDau, Date ngayKetThuc, 
                  int tietBatDau, int tietKetThuc, String lichHoc, 
                  HocPhan hocPhan, GiangVien giangVien, Lop lop, LopHoc lopHoc) {
        super();
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.tietBatDau = tietBatDau; // Cập nhật constructor
        this.tietKetThuc = tietKetThuc; // Cập nhật constructor
        this.lichHoc = lichHoc;
        this.hocPhan = hocPhan;
        this.giangVien = giangVien;
        this.lop = lop;
        this.lopHoc = lopHoc;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getTietBatDau() { // Cập nhật getter
        return tietBatDau;
    }

    public void setTietBatDau(int tietBatDau) { // Cập nhật setter
        this.tietBatDau = tietBatDau;
    }

    public int getTietKetThuc() { // Cập nhật getter
        return tietKetThuc;
    }

    public void setTietKetThuc(int tietKetThuc) { // Cập nhật setter
        this.tietKetThuc = tietKetThuc;
    }

    public String getLichHoc() {
        return lichHoc;
    }

    public void setLichHoc(String lichHoc) {
        this.lichHoc = lichHoc;
    }

    public HocPhan getHocPhan() {
        return hocPhan;
    }

    public void setHocPhan(HocPhan hocPhan) {
        this.hocPhan = hocPhan;
    }

    public GiangVien getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(GiangVien giangVien) {
        this.giangVien = giangVien;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }

    public void setLopHoc(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }
}
