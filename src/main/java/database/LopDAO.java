package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Lop;
import model.Khoa; // Import lớp Khoa
import model.Nganh; // Import lớp Nganh
import model.SinhVien;

public class LopDAO implements DAOInterface<Lop> {

	@Override
	public ArrayList<Lop> selectAll() {
		ArrayList<Lop> ketQua = new ArrayList<>();
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM lop";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String maLop = rs.getString("malop");
				String tenLop = rs.getString("tenlop");
				String coVanHocTap = rs.getString("covanhoctap");
				String tenKhoa = rs.getString("khoa");
				String maNganh = rs.getString("manganh"); // Giả sử có trường mã ngành trong bảng lop

				Khoa khoa = new Khoa("", ""); // Tên khoa có thể được truy vấn thêm nếu cần
				Nganh nganh = new Nganh(maNganh, "", khoa);
				Lop lop = new Lop(maLop, tenLop, coVanHocTap, tenKhoa, nganh);
				ketQua.add(lop);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return ketQua;
	}
	public Lop getLopByMaSinhVien(String maSinhVien) {
	    Lop lop = null;
	    Connection conn = null;
	    String sql = "SELECT lop.malop, lop.tenlop, lop.covanhoctap, lop.khoa, "
	               + "nganh.manganh, nganh.tennganh, khoa.makhoa, khoa.tenkhoa "
	               + "FROM sinhvien "
	               + "JOIN lop ON sinhvien.malop = lop.malop "
	               + "JOIN nganh ON lop.manganh = nganh.manganh "
	               + "JOIN khoa ON nganh.makhoa = khoa.makhoa "
	               + "WHERE sinhvien.masinhvien = ?";

	    try {
	        conn = JDBCUtil.getConnection();
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, maSinhVien);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            lop = new Lop();
	            lop.setMaLop(rs.getString("malop"));
	            lop.setTenLop(rs.getString("tenlop"));
	            lop.setCoVanHocTap(rs.getString("covanhoctap"));
	            lop.setKhoa(rs.getString("khoa"));

	            // Tạo đối tượng Nganh và thiết lập cho đối tượng Lop
	            Nganh nganh = new Nganh();
	            nganh.setMaNganh(rs.getString("manganh"));
	            nganh.setTenNganh(rs.getString("tennganh"));

	            // Tạo đối tượng Khoa và thiết lập cho đối tượng Nganh
	            Khoa khoa = new Khoa();
	            khoa.setMaKhoa(rs.getString("makhoa"));
	            khoa.setTenKhoa(rs.getString("tenkhoa"));
	            nganh.setKhoa(khoa);

	            lop.setNganh(nganh);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(conn);
	    }
	    return lop;
	}

	public ArrayList<Lop> selectByMaNganh(String maNganh) {
		ArrayList<Lop> ketQua = new ArrayList<>();
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM lop WHERE manganh = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, maNganh);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String maLop = rs.getString("malop");
				String tenLop = rs.getString("tenlop");
				String coVanHocTap = rs.getString("covanhoctap");
				String tenKhoa = rs.getString("khoa");

				Khoa khoa = new Khoa("", tenKhoa); // Tạo đối tượng Khoa
				Nganh nganh = new Nganh(maNganh, "", khoa); // Tạo đối tượng Nganh
				Lop lop = new Lop(maLop, tenLop, coVanHocTap, tenKhoa, nganh);
				ketQua.add(lop);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return ketQua;
	}

	@Override
	public Lop selectById(Lop t) {
		Lop ketQua = null;
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM lop WHERE malop=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaLop());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				String maLop = rs.getString("malop");
				String tenLop = rs.getString("tenlop");
				String coVanHocTap = rs.getString("covanhoctap");
				String tenKhoa = rs.getString("khoa");
				String maNganh = rs.getString("manganh"); // Giả sử có trường mã ngành trong bảng lop

				Khoa khoa = new Khoa("", tenKhoa); // Tên khoa có thể được truy vấn thêm nếu cần
				Nganh nganh = new Nganh(maNganh, "", khoa);
				ketQua = new Lop(maLop, tenLop, coVanHocTap, tenKhoa, nganh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return ketQua;
	}

	public Lop getLopByMaLop(String maLop) {
		Lop lop = null; // Khai báo đối tượng Lop
		Connection conn = null; // Khai báo Connection
		String sql = "SELECT lop.malop, lop.tenlop,lop.covanhoctap,lop.khoa, nganh.manganh, nganh.tennganh, khoa.makhoa, khoa.tenkhoa "
				+ "FROM lop " + "JOIN nganh ON lop.manganh = nganh.manganh "
				+ "JOIN khoa ON nganh.makhoa = khoa.makhoa " + "WHERE malop = ?"; // Truy vấn để lấy thông tin lớp,
																					// ngành, và khoa

		try {
			conn = JDBCUtil.getConnection(); // Kết nối cơ sở dữ liệu
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, maLop); // Gán giá trị cho tham số
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				lop = new Lop(); // Khởi tạo đối tượng Lop
				lop.setMaLop(rs.getString("malop")); // Lấy mã lớp
				lop.setTenLop(rs.getString("tenlop")); // Lấy tên lớp
				lop.setCoVanHocTap(rs.getString("covanhoctap"));
				lop.setKhoa(rs.getString("khoa"));
				// Khởi tạo đối tượng Nganh và thiết lập cho đối tượng Lop
				Nganh nganh = new Nganh();
				nganh.setMaNganh(rs.getString("manganh")); // Lấy mã ngành
				nganh.setTenNganh(rs.getString("tennganh")); // Lấy tên ngành

				// Khởi tạo đối tượng Khoa và thiết lập cho đối tượng Nganh
				Khoa khoa = new Khoa();
				khoa.setMaKhoa(rs.getString("makhoa")); // Lấy mã khoa
				khoa.setTenKhoa(rs.getString("tenkhoa")); // Lấy tên khoa
				nganh.setKhoa(khoa); // Thiết lập khoa cho ngành

				lop.setNganh(nganh); // Thiết lập ngành cho lớp
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(conn); // Đóng kết nối trong finally
		}
		return lop; // Trả về đối tượng Lop
	}

	@Override
	public int insert(Lop t) {
		int ketQua = 0;
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "INSERT INTO lop (malop, tenlop, covanhoctap, khoa, manganh) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaLop());
			st.setString(2, t.getTenLop());
			st.setString(3, t.getCoVanHocTap());
			st.setString(4, t.getKhoa());
			st.setString(5, t.getNganh().getMaNganh()); // Giả sử bạn đã có phương thức getMaNganh trong lớp Nganh
			ketQua = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return ketQua;
	}

	@Override
	public int update(Lop t) {
		int ketQua = 0;
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "UPDATE lop SET tenlop=?, covanhoctap=?, khoa=?, manganh=? WHERE malop=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getTenLop());
			st.setString(2, t.getCoVanHocTap());
			st.setString(3, t.getKhoa());
			st.setString(4, t.getNganh().getMaNganh()); // Giả sử bạn đã có phương thức getMaNganh trong lớp Nganh
			st.setString(5, t.getMaLop());
			ketQua = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return ketQua;
	}

	@Override
	public int delete(Lop t) {
	    int ketQua = 0;
	    try {
	        Connection con = JDBCUtil.getConnection();

	        // Delete from the lop table
	        String sql = "DELETE FROM lop WHERE malop = ?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setString(1, t.getMaLop());
	        ketQua = st.executeUpdate();

	        // Delete associated sinhvien records
	        SinhVienDAO sinhVienDAO = new SinhVienDAO();
	        ArrayList<SinhVien> listSinhVien = sinhVienDAO.selectByMaLop(t.getMaLop());
	        for (SinhVien sinhVien : listSinhVien) {
	            ketQua += sinhVienDAO.delete(sinhVien);
	        }

	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ketQua;
	}

	@Override
	public int insertAll(ArrayList<Lop> arr) {
		int dem = 0;
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "INSERT INTO lop (malop, tenlop, covanhoctap, khoa, manganh) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);

			for (Lop lop : arr) {
				st.setString(1, lop.getMaLop());
				st.setString(2, lop.getTenLop());
				st.setString(3, lop.getCoVanHocTap());
				st.setString(4, lop.getKhoa());
				st.setString(5, lop.getNganh().getMaNganh()); // Giả sử bạn đã có phương thức getMaNganh trong lớp Nganh
				dem += st.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return dem;
	}

	@Override
	public int deleteAll(ArrayList<Lop> arr) {
		int dem = 0;
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "DELETE FROM lop WHERE malop=?";
			PreparedStatement st = con.prepareStatement(sql);

			for (Lop lop : arr) {
				st.setString(1, lop.getMaLop());
				dem += st.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return dem;
	}

	public int sosanh(String maLopMoi) {
		int dem = 0;
		Connection con = null;
		try {
			con = JDBCUtil.getConnection();
			String sql = "SELECT COUNT(*) FROM lop WHERE malop = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, maLopMoi);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				dem = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(con);
		}
		return dem;
	}

	public static void main(String[] args) {
		LopDAO lopDAO = new LopDAO();

//		// Kiểm tra selectAll()
//		System.out.println("Danh sách lớp:");
//		ArrayList<Lop> danhSachLop = lopDAO.selectAll();
//		for (Lop lop : danhSachLop) {
//			System.out.println("Mã lớp: " + lop.getMaLop() + ", Tên lớp: " + lop.getTenLop());
//		}
//
//		// Kiểm tra selectById()
//		System.out.println("\nChi tiết lớp với mã lớp 'L001':");
//		Lop lopChiTiet = lopDAO.selectById(new Lop("L001", "", "", "", null));
//		if (lopChiTiet != null) {
//			System.out.println("Mã lớp: " + lopChiTiet.getMaLop() + ", Tên lớp: " + lopChiTiet.getTenLop());
//		} else {
//			System.out.println("Không tìm thấy lớp với mã lớp 'L001'.");
//		}
//
//		// Kiểm tra insert()
//		Lop lopMoi = new Lop("L002", "Lớp mới", "Cố vấn 1", "Khoa Công nghệ Thông tin",
//				new Nganh("adasda", "", new Khoa("", "")));
//		int ketQuaInsert = lopDAO.insert(lopMoi);
//		System.out.println("\nSố lớp đã thêm: " + ketQuaInsert);
//
//		// Kiểm tra update()
//		lopMoi.setTenLop("Lớp mới đã cập nhật");
//		int ketQuaUpdate = lopDAO.update(lopMoi);
//		System.out.println("Số lớp đã cập nhật: " + ketQuaUpdate);
//
//		// Kiểm tra delete()
//		int ketQuaDelete = lopDAO.delete(lopMoi);
//		System.out.println("Số lớp đã xóa: " + ketQuaDelete);
//
//		// Kiểm tra sosanh()
//		int soLuong = lopDAO.sosanh("L002");
//		System.out.println("Số lớp có mã 'L002': " + soLuong);
//
//		// Kiểm tra selectByMaNganh()
//		System.out.println("\nDanh sách lớp theo mã ngành 'adasda':");
//		ArrayList<Lop> danhSachTheoNganh = lopDAO.selectByMaNganh("adasda");
//		for (Lop lop : danhSachTheoNganh) {
//			System.out.println("Mã lớp: " + lop.getMaLop() + ", Tên lớp: " + lop.getTenLop());
//		}

		// Kiểm tra getLopByMaLop()
		System.out.println("\nThông tin chi tiết lớp với mã 'L002':");
		Lop lopTheoMa = lopDAO.getLopByMaLop("L002");
		if (lopTheoMa != null) {
			
			System.out.println("Mã lớp: " + lopTheoMa.getMaLop() + ", Tên lớp: " + lopTheoMa.getTenLop());
			System.out.println("Co van hoc tap : "+ lopTheoMa.getCoVanHocTap()+ "Khoa : "+ lopTheoMa.getKhoa());
			System.out.println("Ngành: " + lopTheoMa.getNganh().getTenNganh() + ", Khoa: "
					+ lopTheoMa.getNganh().getKhoa().getTenKhoa());
		} else {
			System.out.println("Không tìm thấy lớp với mã 'L002'.");
		}
	}
}
