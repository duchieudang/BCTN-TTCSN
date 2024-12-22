package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Nganh;
import model.HocPhan;
import model.Khoa;
import model.Lop;
public class NganhDAO implements DAOInterface<Nganh> {

	@Override
	public ArrayList<Nganh> selectAll() {
		ArrayList<Nganh> ketQua = new ArrayList<>();
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM nganh";
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String maNganh = rs.getString("manganh");
				String tenNganh = rs.getString("tennganh");
				String maKhoa = rs.getString("makhoa");

				// Tạo đối tượng Khoa từ mã khoa
				Khoa khoa = new Khoa(maKhoa, ""); // Tên khoa có thể được truy vấn thêm nếu cần
				Nganh nganh = new Nganh(maNganh, tenNganh, khoa);
				ketQua.add(nganh);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}

	public ArrayList<Nganh> selectByMaKhoaWithPagination(String maKhoa, int offset, int limit) {
		ArrayList<Nganh> listNganh = new ArrayList<>();
		String query = "SELECT * FROM Nganh WHERE maKhoa = ? LIMIT ?, ?";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, maKhoa);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Nganh nganh = new Nganh();
				nganh.setMaNganh(rs.getString("maNganh"));
				nganh.setTenNganh(rs.getString("tenNganh"));
				listNganh.add(nganh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listNganh;
	}

	public int countNganhByMaKhoa(String maKhoa) {
		int count = 0;
		String query = "SELECT COUNT(*) FROM Nganh WHERE maKhoa = ?";
		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, maKhoa);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Nganh selectById(Nganh t) {
		Nganh ketQua = null;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM nganh WHERE manganh=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaNganh());
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				String maNganh = rs.getString("manganh");
				String tenNganh = rs.getString("tennganh");
				String maKhoa = rs.getString("makhoa");

				// Tạo đối tượng Khoa từ mã khoa
				Khoa khoa = new Khoa(maKhoa, ""); // Tên khoa có thể được truy vấn thêm nếu cần
				ketQua = new Nganh(maNganh, tenNganh, khoa);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}

	@Override
	public int insert(Nganh t) {
		int ketQua = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "INSERT INTO nganh (manganh, tennganh, makhoa) VALUES (?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaNganh());
			st.setString(2, t.getTenNganh());
			st.setString(3, t.getKhoa().getMaKhoa());
			ketQua = st.executeUpdate();
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}

	@Override
	public int update(Nganh t) {
		int ketQua = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "UPDATE nganh SET tennganh=?, makhoa=? WHERE manganh=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getTenNganh());
			st.setString(2, t.getKhoa().getMaKhoa());
			st.setString(3, t.getMaNganh());
			ketQua = st.executeUpdate();
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}

	@Override
	public int delete(Nganh t) {
		int ketQua = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "DELETE FROM nganh WHERE manganh=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaNganh());
			ketQua = st.executeUpdate();
			HocPhanDAO hocPhanDAO= new HocPhanDAO();
			LopDAO lopDAO=new LopDAO();
			ArrayList<Lop> listLop= lopDAO.selectByMaNganh(t.getMaNganh());
			ArrayList<HocPhan> listHP= hocPhanDAO.selectByMaNganh(t.getMaNganh());
			for (HocPhan hocPhan : listHP) {
				ketQua+=hocPhanDAO.delete(hocPhan);
			}
			for (Lop lop : listLop)
			{
				ketQua+=lopDAO.delete(lop);
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ketQua;
	}

	public ArrayList<Nganh> selectByMaKhoa(String makhoa) {
		ArrayList<Nganh> listNganh = new ArrayList<>();
		// Thực hiện truy vấn SQL để lấy ngành theo maKhoa
		String sql = "SELECT * FROM Nganh WHERE maKhoa = ?";

		try (Connection conn = JDBCUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, makhoa);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Nganh nganh = new Nganh();
				nganh.setMaNganh(rs.getString("maNganh"));
				nganh.setTenNganh(rs.getString("tenNganh"));
				// Thêm đối tượng Nganh vào danh sách
				listNganh.add(nganh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listNganh;
	}

	public Nganh getNganhByMaNganh(String maNganh) {
	    Nganh nganh = null; // Khai báo đối tượng Nganh
	    Connection conn = null; // Khai báo Connection
	    String sql = "SELECT nganh.manganh, nganh.tennganh, khoa.makhoa, khoa.tenkhoa " + 
	                 "FROM nganh JOIN khoa ON nganh.makhoa = khoa.makhoa " + 
	                 "WHERE manganh = ?"; // Truy vấn để lấy thông tin ngành và khoa
	    try {
	        conn = JDBCUtil.getConnection(); // Kết nối cơ sở dữ liệu
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, maNganh); // Gán giá trị cho tham số
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            nganh = new Nganh(); // Khởi tạo đối tượng Nganh
	            nganh.setMaNganh(rs.getString("manganh")); // Lấy mã ngành
	            nganh.setTenNganh(rs.getString("tennganh")); // Lấy tên ngành
	            
	            // Khởi tạo đối tượng Khoa và thiết lập cho đối tượng Nganh
	            Khoa khoa = new Khoa();
	            khoa.setMaKhoa(rs.getString("makhoa")); // Lấy mã khoa
	            khoa.setTenKhoa(rs.getString("tenkhoa")); // Lấy tên khoa
	            nganh.setKhoa(khoa); // Thiết lập khoa cho ngành
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCUtil.closeConnection(conn); // Đóng kết nối trong finally
	    }
	    return nganh; // Trả về đối tượng Nganh
	}


	// Hàm kiểm tra mã ngành đã tồn tại trong cơ sở dữ liệu chưa
	public int sosanh(String maNganhMoi) {
		int dem = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT COUNT(*) FROM nganh WHERE manganh = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, maNganhMoi);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				dem = rs.getInt(1); // Lấy số lượng bản ghi trùng mã ngành
			}
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dem;
	}

	public static void main(String[] args) {
	    NganhDAO nganhDAO = new NganhDAO(); // Tạo đối tượng NganhDAO

	    // Khởi tạo đối tượng Khoa để thiết lập cho ngành mới
	    Khoa khoa = new Khoa("KO01", ""); // Sửa mã và tên khoa nếu cần

	    // Tạo đối tượng Nganh mới
	    Nganh nganhMoi = new Nganh("NGANH01", "Khoa học Máy tính", khoa); // Sửa mã và tên ngành nếu cần

	    // Thực hiện chèn (insert) đối tượng Nganh vào cơ sở dữ liệu
	    int ketQua = nganhDAO.insert(nganhMoi);

	    // Kiểm tra kết quả
	    if (ketQua > 0) {
	        System.out.println("Đã thêm ngành mới thành công.");
	        System.out.println("Mã ngành: " + nganhMoi.getMaNganh());
	        System.out.println("Tên ngành: " + nganhMoi.getTenNganh());
	        System.out.println("Mã khoa: " + nganhMoi.getKhoa().getMaKhoa());
	        System.out.println("Tên khoa: " + nganhMoi.getKhoa().getTenKhoa());
	    } else {
	        System.out.println("Thêm ngành mới thất bại.");
	    }
	}


	@Override
	public int insertAll(ArrayList<Nganh> arr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAll(ArrayList<Nganh> arr) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
