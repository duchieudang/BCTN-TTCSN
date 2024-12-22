package model;

public class Lop {
    private String maLop;
    private String tenLop;
    private String coVanHocTap;
    private String khoa;  
    private Nganh nganh;
	public Lop() {
		super();
	}

	public Lop(String maLop, String tenLop, String coVanHocTap, String khoa, Nganh nganh) {
		super();
		this.maLop = maLop;
		this.tenLop = tenLop;
		this.coVanHocTap = coVanHocTap;
		this.khoa = khoa;
		this.nganh = nganh;
	}

	public String getMaLop() {
		return maLop;
	}
	public void setMaLop(String maLop) {
		this.maLop = maLop;
	}
	public String getTenLop() {
		return tenLop;
	}
	public void setTenLop(String tenLop) {
		this.tenLop = tenLop;
	}
	public String getCoVanHocTap() {
		return coVanHocTap;
	}
	public void setCoVanHocTap(String coVanHocTap) {
		this.coVanHocTap = coVanHocTap;
	}
	public String getKhoa() {
		return khoa;
	}
	public void setKhoa(String khoa) {
		this.khoa = khoa;
	}

	public Nganh getNganh() {
		return nganh;
	}

	public void setNganh(Nganh nganh) {
		this.nganh = nganh;
	}
   
}
