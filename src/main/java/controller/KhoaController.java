package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import model.Khoa;
import database.KhoaDAO;
import

org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
@WebServlet("/khoa")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
public class KhoaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        KhoaDAO khoaDAO = new KhoaDAO();
        ArrayList<Khoa> listKhoa = khoaDAO.selectAll();
        request.setAttribute("listKhoa", listKhoa);

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            request.setAttribute("showAddModal", true);
        }

        request.getRequestDispatcher("Khoa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        KhoaDAO khoaDAO = new KhoaDAO();

        switch (action) {
            case "add":
               
                String tenKhoa = request.getParameter("tenKhoa");
                String[] words = tenKhoa.split(" "); // Tách chuỗi thành các từ
                String result = "";

                for (String word : words) {
                    if (!word.isEmpty()) {
                        result += word.charAt(0); // Thêm chữ cái đầu tiên của từ vào kết quả
                    }
                }
                String maKhoa ="K"+ result;
                int dem = khoaDAO.sosanh(maKhoa);

                if (dem > 0) {
                    request.setAttribute("status", "success4");
                    request.setAttribute("message", "Đã trùng mã khoa. Yêu cầu nhập lại!");
                    request.getRequestDispatcher("Khoa.jsp").forward(request, response);
                } else {
                    Khoa khoa = new Khoa(maKhoa, tenKhoa);
                    khoaDAO.insert(khoa);
                    response.sendRedirect("khoa?status=success");
                }
                return;

            case "delete":
                String maKhoaToDelete = request.getParameter("maKhoa");
                Khoa khoaToDelete = new Khoa();
                khoaToDelete.setMaKhoa(maKhoaToDelete);
                khoaDAO.delete(khoaToDelete);
                response.sendRedirect("khoa?status=success2");
                return;

            case "update":
                String maKhoaToUpdate = request.getParameter("maKhoa");
                String newTenKhoa = request.getParameter("tenKhoa");
                Khoa khoaToUpdate = new Khoa(maKhoaToUpdate, newTenKhoa);
                khoaDAO.update(khoaToUpdate);
                response.sendRedirect("khoa?status=success3");
                return;
            case "upload":
            	Part filePart = request.getPart("fileExcel"); // Get the file from the request
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + File.separator + "Uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir(); // Create the directory if it doesn't exist
                }
                File file = new File(uploadDir, fileName);
                filePart.write(file.getAbsolutePath()); // Save the file to the disk

                try (FileInputStream fis = new FileInputStream(file);
                     XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
                    XSSFSheet sheet = workbook.getSheetAt(0); // Get the first sheet
                    ArrayList<Khoa> listKhoa = new ArrayList<>();
                    
                    Iterator<Row> rowIt = sheet.iterator(); // Iterate through rows
                    
                    // Skip header row if it exists (optional, adjust based on your file structure)
                    if (rowIt.hasNext()) rowIt.next();

                    while (rowIt.hasNext()) {
                        Row row = rowIt.next(); // Get the current row
                        
                        // Assuming the Excel file contains MaKhoa and TenKhoa in the first two columns
                        String maKhoa2 = row.getCell(0).getStringCellValue(); // Assuming column 0 is MaKhoa
                        String tenKhoa2 = row.getCell(1).getStringCellValue(); // Assuming column 1 is TenKhoa

                        Khoa khoa = new Khoa();
                        khoa.setMaKhoa(maKhoa2);
                        khoa.setTenKhoa(tenKhoa2);
                        
                        listKhoa.add(khoa); // Add the Khoa object to the list
                    }

                    // Check and insert new departments into the database
                    for (Khoa khoa : listKhoa) {
                        if (khoaDAO.sosanh(khoa.getMaKhoa()) == 0) {
                            khoaDAO.insert(khoa); // Insert new department if it doesn't exist
                        }
                    }

                    response.sendRedirect("khoa?status=success5"); // Redirect on success
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("khoa?status=error"); // Redirect on error
                }
                request.getRequestDispatcher("Khoa.jsp").forward(request, response); // Forward request
            default:
                response.sendRedirect("khoa?status=error&message=Invalid action"); // Xử lý nếu hành động không hợp lệ
                return;
                   }
    }
}
