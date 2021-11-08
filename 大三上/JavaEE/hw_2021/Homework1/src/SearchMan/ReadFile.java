package SearchMan;

import java.io.*;
import java.util.*;

import jxl.*;
import jxl.Workbook;  
import jxl.read.biff.BiffException;  


public class ReadFile {
	
	private static int CACHE_SIZE = 3;
	private static int LIST_SIZE = 8;
	
	private Workbook wb;
	private FIFOCache<String, Man> cache = null;;
	private ArrayList<Man> manList = null;
	
	public ReadFile(String filename, FIFOCache<String, Man> ccache) {
		
		cache = ccache;
		manList = new ArrayList<Man>();
		
		try {
			// ���ļ���Ԥ����
			wb = Workbook.getWorkbook(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		} catch (BiffException e) {
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();  
		}
		
	}
	
	private boolean isInCache(String query) {	// �ж��Ƿ���cache�У�����ڣ�����ӵ����ظ������manList��
		Iterator<Map.Entry<String, Man>> it = cache.getIterator();
		boolean flag = false;
		
		while(it.hasNext()) {
			Map.Entry<String, Man> now = it.next();
			Man value = now.getValue();
			if(value.isMatch(query) == true) {
				if(this.isManListFull() == true)
					continue;
				manList.add(value);
				System.out.println("got one in cache!");
				System.out.println("cacah now has " + cache.getNum() + " elements!");
				flag = true;
			}
		}
		
		return flag;
	}
	
	private boolean isInManList(String id) {	// �ж��Ƿ���manList�У������ظ�
		Iterator<Man> it = manList.iterator();
		
		while(it.hasNext()) {
			Man man = it.next();
			if(man.isEquals(id) == true)
				return true;
		}
		return false;
	}
	
	private boolean isManListFull() {
		if(manList.size() == LIST_SIZE || manList.size() > LIST_SIZE)
			return true;
		return false;
	}
	
	public ArrayList<Man> search(String query) {		
		
		//����cache�в�
		isInCache(query);
		
		//�ٴӱ���ֱ�Ӳ�
		int sheet_size = wb.getNumberOfSheets(); 
		int i = 0, j = 0;
		
		for (int index=0; index<sheet_size; index++) {
			
            Sheet sheet = wb.getSheet(index); 		//ÿ��ҳǩ����һ��Sheet����  
            
            for (i = 0; i < sheet.getRows(); i++) {	//ѭ��һ��
            	
            	for (j = 0; j < sheet.getColumns(); j++) {	//ѭ��һ��
            		String cellinfo = sheet.getCell(j, i).getContents();
            		
            		if(cellinfo.contains(query)) {	//�鵽���������
            			String id = sheet.getCell(0, i).getContents();
            			//����Ѿ���manList����
            			if(isInManList(id) == true)
            				continue;
            			Man manTemp = new Man(id, sheet.getCell(1, i).getContents(), 
            					sheet.getCell(2, i).getContents(), sheet.getCell(3, i).getContents(), 
            					sheet.getCell(4, i).getContents());
            			if(this.isManListFull() == true)
            				continue;
            			manList.add(manTemp);
            			cache.put(id, manTemp);
            			System.out.println("after add one to cache, cache now has " + cache.getNum() + " elements");
            		}
            	}
            }
		}
		return manList;
	}
	
	public int getCacheNum() {
		return cache.getNum();
	}

}
