package searchMan;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;



@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UploadServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		//String id1 = request.getParameter("id");
		
		// ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("Error: ��������� enctype=multipart/form-data");
            return;
        }
        
        // ��ȡ·�����洢�ļ�
        //String path = "D:";
        String path = this.getServletContext().getRealPath("/") + "image";
        System.out.println("ͼƬ�洢·����" + path);
        
        // ����·��������һ�� Fileʵ��
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();       // ����������򴴽���·����Ŀ¼
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // ���Ĵ���
        upload.setHeaderEncoding("utf-8");
		
        boolean flag = false;
        try {
        	
        	// ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
        	
            if (formItems != null && formItems.size() > 0) {
            	// ����������
            	for (FileItem item : formItems) {
            		// �����ڱ��е��ֶ�
            		if (!item.isFormField()) {
                		System.out.println("�ύ�����ļ� ��");
                        String fileName = item.getName();
                        System.out.println("�ϴ����ļ�����" + fileName);
                        
                        // ��ȡ�ļ�����׺, ���� "."���ļ��������ֵ�����, �����ļ���׺��
                        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
                        
                        // �洢���ļ������ݻ�ȡ��id��Ψһȷ��, �������ʹ�� "test"
                        String id = (String)request.getSession().getAttribute("id");
                        request.setAttribute("id", id);
                        String fileSaveName = id + "." + prefix; // id.��׺
                        request.setAttribute("saveName", fileSaveName);
                        
                        // ��ȡ�ļ�������
                        InputStream inputStream = item.getInputStream();
                        // �����ļ��������������ָ���ļ������ļ�д������
                        FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + fileSaveName);
                        int index = 0;
                        
                        // ����������ȡ���ݵ���һ���ֽڣ���ĩβʱ���� -1
                        while ((index = inputStream.read()) != -1) {
                            fileOutputStream.write(index);  // ��ָ���ֽ�д����ļ������
                        }
                        
                        // �ر���
                        inputStream.close();
                        fileOutputStream.close();
                        item.delete();

                        // �������ݿ�
                        Database db = new Database();
                        // ����ͼƬ�洢������·��
                        String virtualPath = "image/" + fileSaveName;
                        
                        System.out.println("virtualPath = " + virtualPath + " id = " + id);
                        
                        flag = db.upLoadFile(id, virtualPath);
                        System.out.println(flag);
                        db.release();
                        
                	}
            	}
            	
            }
            
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        PrintWriter out=response.getWriter();
        if(flag == true) {
        	out.print("<script charset=UTF-8>alert('Success!')</script>");
        	request.getRequestDispatcher("ShowImg.jsp").forward(request, response);
        } else {
        	out.print("<script charset=UTF-8>alert('Fail!')</script>");
        	request.getRequestDispatcher("UploadImg.jsp").forward(request, response);
        }
        
	}

}
