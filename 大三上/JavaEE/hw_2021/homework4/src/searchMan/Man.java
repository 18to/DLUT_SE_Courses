package searchMan;

public class Man {
	
	private String id = null;
	private String name = null;
	private String tel = null;
	private String qq = null;
	private String mail = null;
	
	/*
	 * ���캯��������
	 */
	public Man() {}
	
	public Man(String iid, String nname, String ttel, String qqq, String mmail) {
		id = iid;
		name = nname;
		tel = ttel;
		qq = qqq;
		mail = mmail;
	}
	
	public String getID() { return id;}
	public String getName() { return name; }
	public String getTel() { return tel; }
	public String getQq() { return qq; }
	public String getMail() { return mail; }
	
	/*
	 * �ж��������Ϣ��ǰ���Ƿ�ӵ�У�ģ����ѯ
	 */
	public boolean isMatch(String query) {	
		if(id.equals(query))
			return true;
		else if(name.contains(query))
			return true;
		else if(tel.contains(query))
			return true;
		else if(qq.contains(query))
			return true;
		else if(mail.contains(query))
			return true;
		else
			return false;
	}
	
	/*
	 * �ж�id�Ƿ���ͬ
	 */
	public boolean isEquals(String iid) {	
		if(iid.equals(id))
			return true;
		return false;
	}

}

