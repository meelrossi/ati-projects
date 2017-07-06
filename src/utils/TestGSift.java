package utils;

import java.io.File;

public class TestGSift {
	public static void main(String[] args) {
		File folder1 = new File("resources/yaleBfaces/subset1");
		File[] files1 = folder1.listFiles();
		int count = 0;
		int countPos = 0;

		int count1 = 0;
		int countPos1 = 0;
		for (int i = 0; i < files1.length; i++) {
			File file = files1[i];
			String name = GSift.getInstance().findImage(file);
			count++;
			count1++;
			if (name.split("_")[0].equals(file.getName().split("_")[0])) {
				countPos++;
				countPos1++;
			}
		}
		System.out.println("Subset 1: " + countPos1 + " positivas de " + count1 + "\n porcentage bien:"
				+ ((double) countPos1 / count1) * 100 + "%");

		int count2 = 0;
		int countPos2 = 0;
		File folder2 = new File("resources/yaleBfaces/subset2");
		File[] files2 = folder2.listFiles();
		for (int i = 0; i < files2.length; i++) {
			File file = files2[i];
			String name = GSift.getInstance().findImage(file);
			count++;
			count2++;
			if (name.split("_")[0].equals(file.getName().split("_")[0])) {
				countPos++;
				countPos2++;
			}
		}
		System.out.println("Subset 2: " + countPos2 + " positivas de " + count2 + "\n porcentage bien:"
				+ ((double) countPos2 / count2) * 100 + "%");

		int count3 = 0;
		int countPos3 = 0;
		File folder3 = new File("resources/yaleBfaces/subset3");
		File[] files3 = folder3.listFiles();
		for (int i = 0; i < files3.length; i++) {
			File file = files3[i];
			String name = GSift.getInstance().findImage(file);
			count++;
			count3++;
			if (name.split("_")[0].equals(file.getName().split("_")[0])) {
				countPos++;
				countPos3++;
			} 
		}
		System.out.println("Subset 3: " + countPos3 + " positivas de " + count3 + "\n porcentage bien:"
				+ ((double) countPos3 / count3) * 100 + "%");

		int count4 = 0;
		int countPos4 = 0;
		File folder4 = new File("resources/yaleBfaces/subset4");
		File[] files4 = folder4.listFiles();
		for (int i = 0; i < files4.length; i++) {
			File file = files4[i];
			String name = GSift.getInstance().findImage(file);
			count++;
			count4++;
			if (name.split("_")[0].equals(file.getName().split("_")[0])) {
				countPos++;
				countPos4++;
			} else {
				System.out.println(name);
				System.out.println(file.getName());
			}
		}
		System.out.println("Subset 4: " + countPos4 + " positivas de " + count4 + "\n porcentage bien:"
				+ ((double) countPos4 / count4) * 100 + "%");

		System.out.println(countPos + " positivas de " + count);
	}
}
