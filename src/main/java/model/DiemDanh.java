package model;

public class DiemDanh {
   private String maDiemDanh;
 
    private int tongSoTietVang;
    private String soTietVang;
    private SinhVien sinhVien;
	public DiemDanh() {
		super();
	}
	public DiemDanh(String maDiemDanh, int tongSoTietVang, String soTietVang, SinhVien sinhVien) {
		super();
		this.maDiemDanh = maDiemDanh;
		this.tongSoTietVang = tongSoTietVang;
		this.soTietVang = soTietVang;
		this.sinhVien = sinhVien;
	}
	public String getMaDiemDanh() {
		return maDiemDanh;
	}
	public void setMaDiemDanh(String maDiemDanh) {
		this.maDiemDanh = maDiemDanh;
	}
	public int getTongSoTietVang() {
		return tongSoTietVang;
	}
	public void setTongSoTietVang(int tongSoTietVang) {
		this.tongSoTietVang = tongSoTietVang;
	}
	public String getSoTietVang() {
		return soTietVang;
	}
	public void setSoTietVang(String soTietVang) {
		this.soTietVang = soTietVang;
	}
	public SinhVien getSinhVien() {
		return sinhVien;
	}
	public void setSinhVien(SinhVien sinhVien) {
		this.sinhVien = sinhVien;
	}
	
	
	
   
}
