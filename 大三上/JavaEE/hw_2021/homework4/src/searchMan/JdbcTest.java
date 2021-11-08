package searchMan;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class JdbcTest {
	
	public static void main(String[] args) throws Exception {
		
		Connection conn = JdbcUtils.getConnection();
		String sql = "select id,name,tel from homework2";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String id = rs.getString(1);
			String name = rs.getString(2);
			String tel = rs.getString(3);
			System.out.println("id="+id+", name="+name+", tel="+tel);
			System.out.println("-------------------------");
		}
		JdbcUtils.release(conn, ps, rs);
		
	}
	
	public static Connection getConnection(){
        //ͨ�����������ȡָ�������ļ�����������TestDBCPPro��һ������
       InputStream in = JdbcTest.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
       Properties properties = new Properties();
       try {
               properties.load(in);
               //���������ļ������������Ϣ
               DataSource ds = BasicDataSourceFactory.createDataSource(properties);
               Connection conn = ds.getConnection();
               return conn;
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }

}