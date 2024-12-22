package model;

public class Diem {
	  private String maDiem;
	    private double diemtx;
	    private double diemtx2;
	    private double diemThi;
	     
	    private SinhVien sinhvien;
      private MonHoc monHoc;
	
	

		public MonHoc getMonHoc() {
		return monHoc;
	}

	public void setMonHoc(MonHoc monHoc) {
		this.monHoc = monHoc;
	}

		public Diem(String maDiem, double diemtx, double diemtx2, double diemThi, SinhVien sinhvien, MonHoc monHoc) {
		super();
		this.maDiem = maDiem;
		this.diemtx = diemtx;
		this.diemtx2 = diemtx2;
		this.diemThi = diemThi;
		this.sinhvien = sinhvien;
		this.monHoc = monHoc;
	}

		public String getMaDiem() {
			return maDiem;
		}

		public void setMaDiem(String maDiem) {
			this.maDiem = maDiem;
		}

		public double getDiemtx() {
			return diemtx;
		}

		public void setDiemtx(double diemtx) {
			this.diemtx = diemtx;
		}

		public double getDiemtx2() {
			return diemtx2;
		}

		public Diem() {
			super();
		}

		public void setDiemtx2(double diemtx2) {
			this.diemtx2 = diemtx2;
		}

		public double getDiemThi() {
			return diemThi;
		}

		public void setDiemThi(double diemThi) {
			this.diemThi = diemThi;
		}

		public SinhVien getSinhvien() {
			return sinhvien;
		}

		public void setSinhvien(SinhVien sinhvien) {
			this.sinhvien = sinhvien;
		}
	    
		
		
	    
}
