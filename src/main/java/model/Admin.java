package model;

import java.sql.Date;

public class Admin {
    private String maAdmin; 
    private String matKhau; 
    private String hoVaTen;
    private String gioiTinh;
    private String diaChi; 
    private Date ngaySinh;
    private String soDienThoai;
    private String email;


    public Admin() {
    }


	public Admin(String maAdmin, String matKhau, String hoVaTen, String gioiTinh, String diaChi, Date ngaySinh,
			String soDienThoai, String email) {
		super();
		this.maAdmin = maAdmin;
		this.matKhau = matKhau;
		this.hoVaTen = hoVaTen;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.email = email;
	}


	public String getMaAdmin() {
		return maAdmin;
	}


	public void setMaAdmin(String maAdmin) {
		this.maAdmin = maAdmin;
	}


	public String getMatKhau() {
		return matKhau;
	}


	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}


	public String getHoVaTen() {
		return hoVaTen;
	}


	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}


	public String getGioiTinh() {
		return gioiTinh;
	}


	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}


	public String getDiaChi() {
		return diaChi;
	}


	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}


	public Date getNgaySinh() {
		return ngaySinh;
	}


	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}


	public String getSoDienThoai() {
		return soDienThoai;
	}


	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

  
}
