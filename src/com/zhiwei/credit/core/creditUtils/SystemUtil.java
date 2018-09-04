package com.zhiwei.credit.core.creditUtils;

/*
根据你的原因，可能是系统的Path被更改了，输入ping等命令要输入完整路径才可以，比如C:\windows\system32\ping.exe， 


在我的电脑右键属性，，，"高级"选项卡，最下面的“环境变量”，，进入系统变量，找到"path"一项，按“编辑”，，，变量值前面的不要动，在后面加上分号后输入系统安装的路径C:\windows\system32\ 

一定要加英文符号的分号啊,,,,像这样 ;C:\windows\system32\ 
保存就可以了*/

import java.io.*;
import java.util.*;

/**
 * 系统工具
 * @author Jiang Wanyu
 *
 */
public class SystemUtil {
	static private final int MACLength = 18;

	//调用这个
	static public String getMACAddress() {
		SysCommand syscmd = new SysCommand();
		// 系统命令
		String cmd = "C:\\windows\\system32\\ipconfig.exe /all";
		Vector result;
		result = syscmd.execute(cmd);
		return getCmdStr(result.toString());
	}

	static public String getCmdStr(String outstr) {
		String find = "hysical Address. . . . . . . . . :";
		int findIndex = outstr.indexOf(find);
		if (findIndex == -1) {
			return "未知错误！";
		} else {
			return outstr.substring(findIndex + find.length() + 1, findIndex
					+ find.length() + MACLength);
		}
	}
}

// SysCommand类
class SysCommand {
	Process p;

	public Vector execute(String cmd) {
		try {
			Start(cmd);
			Vector vResult = new Vector();
			DataInputStream in = new DataInputStream(p.getInputStream());
			BufferedReader myReader = new BufferedReader(new InputStreamReader(in));
			String line;
			do {
				line = myReader.readLine();
				if (line == null) {
					break;
				} else {
					vResult.addElement(line);
				}
			} while (true);
			myReader.close();
			return vResult;
		} catch (Exception e) {
			return null;

		}

	}

	public void Start(String cmd) {
		try {
			if (p != null) {
				kill();
			}
			Runtime sys = Runtime.getRuntime();
			p = sys.exec(cmd);

		} catch (Exception e) {

		}
	}

	public void kill() {
		if (p != null) {
			p.destroy();
			p = null;
		}
	}

}