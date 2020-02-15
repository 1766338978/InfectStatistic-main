import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * InfectStatistic
 * TODO
 *
 * @author Spike
 * @version 1.0
 * @since 2020/2/12
 */
class InfectStatistic{
    public static void main(String[] args) {
        System.out.println("It's testing!");
        //test        
        CmdAnalysis test = new CmdAnalysis(args);
        if(test.isCmdString()) {
            System.out.println("��ȷ");
            test.showAll();
        }
        else
            System.out.println("����");
    }
    
}
class CmdAnalysis{
	/*
	      ���������в���
	 */
	private String[] cmdString;
	private String logLocation;
	private String outLocation;
	private String logDate;
	private int[] typeOrder = {0,1,2,3};	//Ĭ��ȫ���˳��,-1�������
	private String[] typeString = {"ip","sp","cure","dead"};
	private int[] provinceShow = new int[32];	//Ĭ��ȫ���˳��,-1�������
	private String[] province = {"ȫ��", "����","����", "����","����","����",
			"�㶫", "����", "����", "����", "�ӱ�", "����", "������", "����", "����", "����",
			"����", "����", "����", "���ɹ�", "����", "�ຣ", "ɽ��", "ɽ��", "����", "�Ϻ�",
			"�Ĵ�", "���", "����", "�½�", "����", "�㽭"};
	
	public CmdAnalysis(String []args) {
	    cmdString = args;
	    //����Ĭ��ָ������Ϊһ���ӽ����������ڣ���������Ƚϣ�����Ĭ��Ϊȫ��ͳ��
	    logDate = "9999-12-31";
	    for(int i = 0;i < provinceShow.length;i++)
	    	provinceShow[i] = 0;
	}
	public boolean isCmdString() {
		/*
		      �ж������в����Ƿ���ȷ������ȷ��ֵ����
		 */
		boolean mustLog = false;
		boolean mustOut = false;
		if(!cmdString[0].equals("list")) {
			System.out.println("�����в���ȱ��list");
			return false;
		}
		for(int i = 0;i < cmdString.length;i++) {
			if(cmdString[i].equals("-log")) {
				mustLog = true;
				//�����־�ļ�·���Ϸ���
				if(!isLogLocation(++i)) {
					System.out.println("��־�ļ�·�����Ϸ�");
					return false;
				}
			}
			if(cmdString[i].equals("-out")) {
				mustOut = true;
				//������·���Ϸ���
				if(!isOutLocation(++i)) {
					System.out.println("���·�����Ϸ�");
					return false;
				}
			}
			if(cmdString[i].equals("-date")) {
				//������ںϷ���
				if(!isCorrectDate(++i)) {
					System.out.println("ָ�����ڲ��Ϸ�");
					return false;
				}
			}
			if(cmdString[i].equals("-type")) {
				if(!isType(++i)) {
					System.out.println("ָ�����Ͳ��Ϸ�");
					return false;
				}
			}
			if(cmdString[i].equals("-province")) {
				if(!isProvince(++i)) {
					System.out.println("ָ��ʡ�ݲ��Ϸ�");
					return false;
				}
			}
		}
		if(mustLog && mustOut)
			return true;
		else {
			System.out.println("ȱ������-log �� -out");
			return false;
		}
			
	}
	private boolean isLogLocation(int i) {
		/*
		      �ж�Ŀ¼·���Ƿ���ȷ
		 */
		if(i<cmdString.length) {
			String regex = "^[A-z]:(/|\\\\)(.+?(/|\\\\))*$";
			if(cmdString[i].matches(regex)) {
				logLocation = cmdString[i];
				return true;
			}else
				return false;
		}else
			return false;
	}
	
	private boolean isOutLocation(int i) {
		/*
		     �ж����·���Ƿ���ȷ
		 */
		if(i<cmdString.length) {
			String regex = "^[A-z]:(/|\\\\)(.+?(/|\\\\))*(.+\\.txt)$";
			if(cmdString[i].matches(regex)) {
				outLocation = cmdString[i];
				return true;
			}else
				return false;
		}else
			return false;
	}
	
	private boolean isCorrectDate(int i) {
		/*
	             �ж�ָ�������Ƿ���ȷ
	   */
		if(i<cmdString.length) {
			if(isValidDate(cmdString[i])) {
				logDate = cmdString[i];
				return true;
			}else 
				return false;
		}else
			return false;
	}
	
	private boolean isValidDate(String strDate) {
		/*
	     	�ж�ָ�����ڸ�ʽ�Ƿ�����yyyy-MM-dd �ַ����Ƿ�Ϊ����
	   */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
            format.setLenient(false);
            Date date = format.parse(strDate);
            String[] sArray = strDate.split("-");
            for (String s : sArray) {
                boolean isNum = s.matches("[0-9]+");
                if (!isNum) {
                    return false;
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
        return true;
	}
	private boolean isType(int i) {
		/*
	             �ж�ָ�������Ƿ���ȷ
	   */
		for(int a = 0;a < typeOrder.length;a++)
			typeOrder[a] = -1;	
		int currentIndex = i;
		if(i<cmdString.length) {
			int t = 0;
			for(;currentIndex < cmdString.length; currentIndex ++) {
				if(cmdString[currentIndex].equals(typeString[0])) {
					if(t < typeOrder.length)
						typeOrder[t] = 0;
					t++;
				}	
				else if(cmdString[currentIndex].equals(typeString[1])) {
					if(t < typeOrder.length)
						typeOrder[t] = 1;
					t++;
				}
				else if(cmdString[currentIndex].equals(typeString[2])) {
					if(t < typeOrder.length)
						typeOrder[t] = 2;
					t++;
				}
				else if(cmdString[currentIndex].equals(typeString[3])) {
					if(t < typeOrder.length)
						typeOrder[t] = 3;
					t++;
				}
				else {
					break;
				}
			}
			if(t > 0 )
				return true;
			else
				return false;
		}else
			return false;
	}
	private boolean isProvince(int i) {
		/*
	             �ж�ָ��ʡ���Ƿ���ȷ
	   */
		int currentIndex = i;
		if(i<cmdString.length) {
			for(;currentIndex < cmdString.length; currentIndex ++) {
				for(int j = 0;j < province.length;j++) {
					if(cmdString[currentIndex].equals(province[j]))
						provinceShow[j] = 1;
				}
			}
			return true;
		}else
			return false;
	}
	
	
	
	
	public void showAll() {
		for(int i = 0;i < cmdString.length;i++) {
			System.out.println(cmdString[i]);
		}
		System.out.println(logLocation);
		System.out.println(outLocation);
		System.out.println(logDate);
		for(int i = 0;i < typeOrder.length;i++) {
			if(typeOrder[i] != -1) {
				System.out.println(typeString[typeOrder[i]]);
			}
			
		}
		for(int i = 0;i < provinceShow.length;i++) {
			if(provinceShow[i] != 0) {
				System.out.println(province[i]);
			}
			
		}
	}
}