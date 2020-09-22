import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DuplicateCheck {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入论文原文文件的绝对路径！");
        String path1 = input.next();
        int[] count1 = getCount(path1);
        System.out.println("请输入待检测版本的绝对路径！");
        String path2 = input.next();
        int[] count2 = getCount(path2);
        // 通过空间向量法，计算两个向量的余弦值，所得即为重复率
        double molecule=0;
        double denominator;
        double temp1 = 0;
        double temp2 = 0;
        for (int i = 1; i < count1.length; i++) {
            molecule += count1[i] * count2[i];
            temp1 += count1[i] * count1[i];
            temp2 += count2[i] * count2[i];
//			if(count1[i]!=count2[i])
//				System.out.println(i);
        }
        denominator = Math.sqrt(temp1 * temp2);
        double result =molecule / denominator;
        String saveResult = String.valueOf(Math.floor(result*100)/100);//保留两位小数
        System.out.println("请输入检测结果储存的绝对路径！");
        String path3 = input.next();
        saveResult(path3, saveResult);
        input.close();
    }

    public static void saveResult(String path, String result) { // 保存结果数据
        try {
            File file = new File(path);
            byte[] b = result.getBytes();
            FileOutputStream out = new FileOutputStream(file);
            out.write(b);
            out.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] getCount(String path) {// 获取汉字出现次数的数组
        int[] count = new int[65536];
        try {
            File file = new File(path);
            InputStreamReader read;
            read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            int num;
            char[] ch = new char[100];
            do {
                if ((num = read.read(ch)) != -1) {
                    int[] key1 = getCode(ch);
                    if (key1 != null)
                        for (int key : key1)
                            count[key]++;
                }
            } while (num != -1);
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("找不到文件");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int[] getCode(char[] ch) {// 汉字转换成区位码
        if (ch == null) {
            return null;
        } else {
            int[] code = new int[100];
            try {
                for (int i = 0; i < ch.length; i++) {
                    StringBuilder sb = new StringBuilder();
                    byte[] by = String.valueOf(ch[i]).getBytes("GBK");
                    for (byte b : by) {
                        sb.append(Integer.toHexString(b & 0xff));
                    }
                    String str = sb.toString();
                    code[i] = Integer.parseInt(str, 16);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return code;
        }
    }
}
